import java.util.*;
import java.text.*;
import java.io.*;

public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    private ClientList clientList;
    private ProductList productList;
    private ManufacturerList manufacturerList;
    private static Warehouse warehouse;
    private OrderList orderList;
    private Inventory inventory;

    private Warehouse() {
        clientList = ClientList.instance();
        manufacturerList = ManufacturerList.instance();
        productList = ProductList.instance();
        orderList = OrderList.instance();
    }

    public static Warehouse instance() {
        if (warehouse == null) {
            ManufacturerIdServer.instance();
            ProductIdServer.instance();
            ClientIdServer.instance();
            OrderIdServer.instance();
            return (warehouse = new Warehouse());
        } else {
            return warehouse;
        }
    }

    public Iterator<Product> getProductByMan(Manufacturer m) {
        return m.getProducts();
    }

    public Iterator<Manufacturer> getManufacturerByProd(Product p) {
        return p.getManufacturers();
        // return p.getManufacturerIds();
    }

    public Iterator<Double> getPrice(Product p) {
        return p.getManufacturerPrices();
    }

    public Product addProduct(String Name, double cost, int quantity) {
        Product prod = new Product(Name, cost, quantity);
        if (productList.insertProduct(prod)) {
            return (prod);
        }
        return null;
    }

    public Client addClient(String Name, String cAddress, String cPhone) {
        Client client = new Client(Name, cAddress, cPhone);
        if (clientList.insertMember(client)) {
            return (client);
        }
        return null;
    }

    public Manufacturer addManufacturer(String mName, String mAddress, String mPhone) {
        Manufacturer newManufacturer = new Manufacturer(mName, mAddress, mPhone);
        if (manufacturerList.insertManufacturer(newManufacturer)) {
            return (newManufacturer);
        }
        return null;
    }

    public Iterator getProducts() {
        return productList.getAllProducts();
    }

    public Iterator getClients() {
        return clientList.getAllClients();
    }

    public Iterator getManufacturer() {
        return manufacturerList.getAllManufacturers();
    }

    // // find product
    // public Product searchProduct(String pId) {
    //     return productList.find(pId);
    // }

    // find product
    public Product findProduct(String productId) {
        return productList.search(productId);
    }

    public Manufacturer searchManufacturer(String mId) {
        return manufacturerList.searchId(mId);
    }

    public Product assignProdToManufacturer(String pId, String mId, Double price) {
        Product product = productList.search(pId);
        if (product == null) {
            return null;
        }

        Manufacturer manufacturer = manufacturerList.searchId(mId);
        if (manufacturer == null) {
            return null;
        }

        int found = 0;
        found = product.searchSupplier(manufacturer);
        if (found != -1) {
            return null;
        }

        boolean check = product.assign(manufacturer);
        check = product.addPrice(price);
        check = manufacturer.assignProduct(product);
        if (check) {
            return product;
        } else {
            return null;
        }
    }

    // unassign
    public void unassignProdFromManufacturer(String pId, String mId) {
        Product product = productList.search(pId);
        if (product == null) {
            return;
        }

        Manufacturer manufacturer = manufacturerList.searchId(mId);
        if (manufacturer == null) {
            return;
        }

        int found = 0;
        found = product.searchSupplier(manufacturer);
        boolean check = product.Unassign(manufacturer);
        check = product.removePrice(found);
        check = manufacturer.unassignProduct(product);
    }

    public String toString() {
        return productList + "\n" + clientList;
    }

    // accept payment
    public void acceptPayment(String clientName, double payment) {
        Client client;
        client = clientList.findClient(clientName);

        if (client != null) {
            client.creditInvoice(payment);
            System.out.println("Payment successful");

        } else {
            System.out.println("Payment unsuccessful");
        }
    }

    // saving the warehouse state
    public static boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("WarehouseData");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(warehouse);
            output.writeObject(ClientIdServer.instance());
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    // product processing stuff
    public void recieveProduct(String productName, int i, BufferedReader reader) {
        // Product productToMatch = inventory.find(productName);
        Product productToMatch = productList.search(productName);
        Iterator allOrders = orderList.getOrder();

        while (allOrders.hasNext()) {
            Order eachOrder = (Order) allOrders.next();

            Iterator waitListIterator = eachOrder.getWaitListIterator();

            while (waitListIterator.hasNext()) {
                OrderLineItem waitListForOrder = (OrderLineItem) waitListIterator.next();

                if (waitListForOrder.product.equals(productToMatch) && i >= waitListForOrder.quantity
                        && waitListForOrder.quantity > 0) {

                    System.out.println("Order: " + eachOrder.getId() + " has " + waitListForOrder.quantity
                            + " items waiting to be filled");
                    String prompt = getToken(", continue?y/n", reader);

                    prompt = prompt.toUpperCase();
                    if (prompt.startsWith("Y")) {

                        int amountToFill;

                        amountToFill = waitListForOrder.quantity;

                        waitListForOrder.quantity = 0;

                        eachOrder.insertlistedItem(productToMatch, amountToFill);

                        i -= amountToFill;

                        clientList.debitClient(eachOrder.getClientReferenceId(),
                                amountToFill * productToMatch.getCost());
                    }

                }

            }

        }
        System.out.println("Depositing " + i + " " + productToMatch.getProductName());
        productToMatch.setQuantity(productToMatch.getQuantity() + i);
    } // end of receiveProduct

    // processing an order stuff
    public Order processOrder(Order order, String tempClient) {
        double Total;
        Order newOrder = order;

        newOrder.setClientReferenceId(tempClient);

        order.createWaitList();

        clientList.debitClient(tempClient, newOrder.getTotals());

        
        if (orderList.insertOrderNode(newOrder)) {

            return newOrder;
        } else
            return null;

    }

    // get token
    public String getToken(String prompt, BufferedReader reader) {
        do {
            try {
                System.out.println(prompt);

                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            } catch (IOException ioe) {

                System.exit(0);
            }
        } while (true);
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
    // end

    // retrieve saved database
    public static Warehouse retrieve() {
        try {
            FileInputStream file = new FileInputStream("WarehouseData");
            ObjectInputStream input = new ObjectInputStream(file);
            input.readObject();
            ClientIdServer.retrieve(input);
            return warehouse;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        }
    }

    // wailtlist stuff
    public void showWaitlistForProduct(Product product) {
        orderList.outPutWaitListForProduct(product);
    }

    // unpaid balance stuff
    public void outStandingBalance() {
        clientList.listoutstandingBalances();

    }

}
