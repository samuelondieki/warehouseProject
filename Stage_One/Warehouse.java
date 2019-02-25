import java.util.*;
import java.lang.*;
import java.io.*;

import java.util.*;
import java.io.*;

public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Inventory inventory;
    private ManufacturerList manufacturerList;
    private ClientList clientList;
    private static Warehouse warehouse;

    // instances
    private Warehouse() {
        inventory = Inventory.instance();
        manufacturerList = ManufacturerList.instance();
        clientList = ClientList.instance();
        orderList = OrderList.instance();
    }

    public static Warehouse instance() {
        if (warehouse == null) {
            ManufacturerIdServer.instance(); // instantiate all singletons
            ClientIdServer.instance();
            ProductIdServer.instance();
            OrderIdServer.instance();
            return (warehouse = new Warehouse());
        } else {
            return warehouse;
        }
    }

    public Product addProduct(String name, int quantity, double price) {
        Product product = new Product(name, quantity, price);
        if (inventory.insertProduct(product)) {
            return (product);
        }
        return null;
    }

    public Manufacturer addManufacturer(String name, String address, String phone) {
        Manufacturer manufacturer = new Manufacturer(name, address, phone);
        if (manufacturerList.insertManufacturer(manufacturer)) {
            return (manufacturer);
        }
        return null;
    }

    public Client addClient(String name, String address, String phone) {
        Client client = new Client(name, address, phone);
        if (clientList.insertClient(client)) {
            return (client);
        }
        return null;
    }

    public Iterator getProductIterator() {
        return inventory.getProductList();
    }

    public Iterator getManufacturerIterator() {
        return manufacturerList.getManufacturerList();
    }

    public Iterator getClientIterator() {
        return clientList.getClientList();
    }

    public Iterator getOrderIterator() {
        return orderList.getOrderList();
    }

    public Client findClient(String id) {
        return clientList.search(id);
    }

    public Manufacturer findManufacturer(String id) {
        return manufacturerList.search(id);
    }

    public Product findProduct(String id) {
        return inventory.search(id);
    }

    public String getClientInfo(String id) {
        return (clientList.search(id)).toString();
    }

    public Product assignProductManufacturer(String pid, String mid, int price) {
        Product product = inventory.search(pid);
        if (product != null) {
            Manufacturer manufacturer = manufacturerList.search(mid);
            ProdMan pm = new ProdMan(product, manufacturer, price);
            product.assignManufacturer(pm);
            manufacturer.assignProduct(pm);
        }
        return product;
    }

    public Product unassignProductManufacturer(String pid, String mid) {
        Product product = inventory.search(pid);
        if (product != null) {
            Manufacturer manufacturer = manufacturerList.search(mid);
            ProdMan pm = manufacturer.searchProd(pid);
            product.unassignManufacturer(pm);
            manufacturer.unassignProduct(pm);
        }
        return product;
    }

    public Iterator getProductManufacturers(String id) {
        Product product = inventory.search(id);
        if (product != null) {
            return product.getManufacturers();
        } else {
            return null;
        }
    }

    public Iterator getManufacturerProducts(String id) {
        Manufacturer manufacturer = manufacturerList.search(id);
        if (manufacturer != null) {
            return manufacturer.getProducts();
        } else {
            return null;
        }
    }

    public int verifyQuantity(String pid, int q) {
        Product product = inventory.search(pid);
        boolean enough = (product.getQuantity() >= q);

        if (product == null) {
            return 0; // product does not exist
        }

        if (enough) {
            return 1; // product has enough quantity
        } else {
            return 2; // product does not have enough quantity
        }

    }

    public boolean verifyClient(String cid) {
        Client c = clientList.search(cid);
        if (c != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyProduct(String pid) {
        Product p = inventory.search(pid);
        if (p != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyManufacturer(String mid) {
        Manufacturer m = manufacturerList.search(mid);
        if (m != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyOrder(String oid) {
        Order o = orderList.search(oid);
        if (o != null) {
            return true;
        } else {
            return false;
        }
    }

    public String getOrderDetails(String oid) {
        Order order = orderList.search(oid);
        return order.toString();
    }

    public void processClientOrder(String oid) {
        ClientOrder clientorder = (ClientOrder) orderList.search(oid);
        Iterator invoice = clientorder.getInvoice();
        clientorder.calculateTotalPrice();
        Product stock;
        Item requested;

        String id;
        int orderQuan;

        while (invoice.hasNext()) {

            requested = (Item) (invoice.next());
            id = requested.getProd().getId();
            stock = inventory.search(id);
            orderQuan = requested.getQuan();

            // int
            stock.setQuantity((stock.getQuantity() - orderQuan));
        }

        Client client = clientorder.getClient();
        double orderPrice = clientorder.getPrice();

        client.chargeAmount(orderPrice);

    }

    public boolean cancelOrder(String oid) {
        Order cancelOrder = orderList.search(oid);
        Iterator olist = getOrderIterator();

        boolean found = false;

        while (!found && olist.hasNext()) {
            Order order = (Order) (olist.next());
            if (cancelOrder.equals(order.getId())) {
                olist.remove();
                cancelOrder = null;
                found = true;
            }
        }

        return found;
    }

    public void addProductToOrder(String oid, String pid, int quantity) {
        ClientOrder order = (ClientOrder) orderList.search(oid);
        Product product = inventory.search(pid);
        Item item = new Item(product, quantity);

        order.addItem(item);
    }

    public Iterator getOrdersToFill(String oid) {
        ManuOrder order = (ManuOrder) orderList.search(oid);
        String pid = order.getProdId();
        Product product = inventory.search(pid);
        if (product != null) {
            return product.getWaitlist();
        } else {
            return null;
        }
    }

    public int getManuOrderQuantity(String oid) {
        ManuOrder order = (ManuOrder) orderList.search(oid);
        return order.quantity();
    }

    public void increaseInventory(String pid, int quantity) {
        Product product = inventory.search(pid);
        int curInventory = product.getQuantity();
        product.setQuantity(curInventory + quantity);
    }

    public static Warehouse retrieve() {
        try {
            FileInputStream file = new FileInputStream("WarehouseData");
            ObjectInputStream input = new ObjectInputStream(file);
            input.readObject();
            ManufacturerIdServer.retrieve(input);
            ClientIdServer.retrieve(input);
            ProductIdServer.retrieve(input);
            OrderIdServer.retrieve(input);
            return warehouse;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        }
    }

    public static boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("WarehouseData");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(warehouse);
            output.writeObject(ManufacturerIdServer.instance());
            output.writeObject(ClientIdServer.instance());
            output.writeObject(ProductIdServer.instance());
            output.writeObject(OrderIdServer.instance());
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(warehouse);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            input.defaultReadObject();
            if (warehouse == null) {
                warehouse = (Warehouse) input.readObject();
            } else {
                input.readObject();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}