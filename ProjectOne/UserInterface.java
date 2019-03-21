
//samuel ondieki
//new version modified version

import java.util.*;
import java.text.*;
import java.io.*;

public class UserInterface {
    private static UserInterface userInterface;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    private static final int EXIT = 0;
    private static final int ADD_CLIENT = 1;
    private static final int ADD_PRODUCT = 2;
    private static final int ADD_MANUFACTURER = 3;
    private static final int DISPLAY_CLIENTS = 4;
    private static final int DISPLAY_PRODUCTS = 5;
    private static final int DISPLAY_MANUFACTURERS = 6;
    private static final int ASGN_PROD_TO_MANU = 7;
    private static final int DISPLAY_PRODUCTS_BY_MAN = 8;
    private static final int DISPLAY_MANUFACTURER_FOR_PRODUCT = 9;
    private static final int UNASSIGN_PRODUCT = 10;
    private static final int ACCEPT_CLIENT_PAYMNENT = 11;
    private static final int SHOW_WAITLIST = 12;
    private static final int ADD_ORDER = 13;
    private static final int DISPLAY_OUTSTANDING_BALANCE = 14;
    private static final int PROCESS_ORDER = 15;
    private static final int SAVE = 16;
    private static final int RETRIEVE = 17;
    private static final int HELP = 18;

    private UserInterface() {
        if (yesOrNo("Look for saved data and use it?")) {
            retrieve();
        } else {
            warehouse = Warehouse.instance();
        }
    }

    public static UserInterface instance() {
        if (userInterface == null) {
            return userInterface = new UserInterface();
        } else {
            return userInterface;
        }
    }

    public String getToken(String prompt) {
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

    private boolean yesOrNo(String prompt) {
        String more = getToken(prompt + " (Y|y)[es] or anything else for no");
        if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
            return false;
        }
        return true;
    }

    public int getCommand() {
        do {
            try {
                int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
                if (value >= EXIT && value <= HELP) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
    }

    public void help() {
        System.out.println("Enter a number between 0 and 12 as explained below:");
        System.out.println(EXIT + " to Exit");
        System.out.println(ADD_CLIENT + " Add client");
        System.out.println(ADD_PRODUCT + " Add products");
        System.out.println(ADD_MANUFACTURER + " Add manufacturer");
        System.out.println(DISPLAY_CLIENTS + " Display clients");
        System.out.println(DISPLAY_PRODUCTS + " Display products");
        System.out.println(DISPLAY_MANUFACTURERS + " Display manufacturers");
        System.out.println(ASGN_PROD_TO_MANU + " Assign product to manufacturer");
        System.out.println(DISPLAY_PRODUCTS_BY_MAN + " Display list of products by a manufacturer");
        System.out.println(DISPLAY_MANUFACTURER_FOR_PRODUCT + " Display list of manufacturers for a product");
        System.out.println(UNASSIGN_PRODUCT + " Unassign a product");
        System.out.println(ACCEPT_CLIENT_PAYMNENT + " Accept client payment");
        System.out.println(SHOW_WAITLIST + " Show waitlisted orders for a product ");
        System.out.println(ADD_ORDER + " To add an order");
        System.out.println(DISPLAY_OUTSTANDING_BALANCE + " To display outstanding balances");
        System.out.println(PROCESS_ORDER + " To receive a shipment");
        System.out.println(" ");
        System.out.println(SAVE + " To save");
        System.out.println(RETRIEVE + " To retrieve");
        System.out.println(HELP + " For help");

    }

    // add client
    public void addNewClient() {
        String cName = getToken("Enter client name");
        String cAddress = getToken("Enter address");
        String cPhone = getToken("Enter phone");
        Client result;
        result = warehouse.addClient(cName, cAddress, cPhone);
        if (result == null) {
            System.out.println("Could not add client");
        }
        System.out.println(result);
    }

    // new product
    public void addNewProduct() {
        Product result;
        do {
            String pName = getToken("Enter name");
            int pQuantity = Integer.parseInt(getToken("Enter Product quantity"));
            double pCost = Double.parseDouble(getToken("Enter price for item"));
            ;
            result = warehouse.addProduct(pName, pCost, pQuantity);
            if (result != null) {
                System.out.println(result);
            } else {
                System.out.println("Product could not be added");
            }
            if (!yesOrNo("Add more products?")) {
                break;
            }
        } while (true);
    }

    // new manufacturers
    public void addnewManufacturer() {
        Manufacturer result;
        do {
            String mName = getToken("Enter name");
            String mAddress = getToken("Enter address");
            String mPhonenumber = getToken("Enter phone number");
            result = warehouse.addManufacturer(mName, mAddress, mPhonenumber);
            if (result != null) {
                System.out.println(result);
            } else {
                System.out.println("Manufacturer could not be added");
            }
            if (!yesOrNo("Add more Manufacturers?")) {
                break;
            }
        } while (true);
    }

    // display products
    public void showProducts() {
        Iterator allProducts = warehouse.getProducts();
        while (allProducts.hasNext()) {
            Product product = (Product) (allProducts.next());
            System.out.println(product.toString());
        }
    }

    // display clients
    public void showClients() {
        Iterator allManu = warehouse.getClients();
        while (allManu.hasNext()) {
            Client client = (Client) (allManu.next());
            System.out.println(client.toString());
        }
    }

    public void showManufacturers() {
        Iterator allManu = warehouse.getManufacturer();
        while (allManu.hasNext()) {
            Manufacturer manufacturer = (Manufacturer) (allManu.next());
            System.out.println(manufacturer.toString());
        }
    }

    public void assignProduct() {
        String pId = getToken("Enter product ID: ");
        Product product; // not used
        if ((product = warehouse.findProduct(pId)) == null) {
            System.out.println("Product does not exist.");
            return;
        }

        String mId = getToken("Enter manufacturer ID: ");
        Manufacturer m;
        if ((m = warehouse.searchManufacturer(mId)) == null) {
            System.out.println("No such manufacturer.");
            return;
        }

        double p;
        while (true) {
            String price = getToken("Enter product price: ");
            try {
                p = Double.parseDouble(price);
                break;
            } catch (NumberFormatException ignore) {
                System.out.println("Invalid price");
            }
        }
        product = warehouse.assignProdToManufacturer(pId, mId, p);
    }

    public void unassignProduct() {
        String pId = getToken("Enter product ID: ");
        Product product;
        if ((product = warehouse.findProduct(pId)) == null) {
            System.out.println("Product does not exist.");
            return;
        }
        System.out.println("It works for this one");
        String mId = getToken("Enter manufacturer ID: ");
        Manufacturer m;
        if ((m = warehouse.searchManufacturer(mId)) == null) {
            System.out.println("No such manufacturer.");
            return;
        }
        warehouse.unassignProdFromManufacturer(pId, mId);
    }

    // create an order
    public void createOrder() {
        Order createdOrder = new Order();
        char cont;

        String productStringId;
        String tempString;
        int tempQuantity;
        Product tempProduct;
        String tempClient;

        boolean match = false;

        boolean addItemsToOrder;
        Order result;

        tempClient = getToken("Enter client id to create order for ");
        Iterator i = warehouse.getClients();

        while (i.hasNext()) {
            Client client = (Client) i.next();

            if (client.equal(tempClient)) {
                match = true;

            }

        }

        if (match) {

            do {

                productStringId = getToken("Enter First id of product to be added to the list");
                tempProduct = warehouse.findProduct(productStringId);
                if (tempProduct != null) {

                    tempQuantity = Integer.parseInt(getToken("Enter the quantity of that item: "));

                    addItemsToOrder = createdOrder.insertlistedItem(tempProduct, tempQuantity);
                    if (!addItemsToOrder) {
                        System.out.println("Failed to add item to order");

                    } else
                        System.out.println("Added Item");

                } else {
                    System.out.println("Could not find item");
                }

                tempString = getToken("Continue adding items? Y to continue");
                cont = tempString.charAt(0);
            } while (cont == 'y' || cont == 'Y');

            System.out.println("Processing the order");

            result = warehouse.processOrder(createdOrder, tempClient);

            if (result == null) {
                System.out.println("Could not add order");
            } else {

                System.out.println("Added product " + result);

            }
        } else
            System.out.println("Couldn't find client to creat order for");

    }
    // end of create an order

    // unpaid balances
    private void outstandingBalance() {
        warehouse.outStandingBalance();
    }

    public void process() {
        int command;
        help();
        while ((command = getCommand()) != EXIT) {
            switch (command) {
            case ADD_CLIENT:
                addNewClient();
                break;
            case ADD_PRODUCT:
                addNewProduct();
                break;
            case ADD_MANUFACTURER:
                addnewManufacturer();
                break;
            case DISPLAY_CLIENTS:
                showClients();
                break;
            case DISPLAY_PRODUCTS:
                showProducts();
                break;
            case DISPLAY_MANUFACTURERS:
                showManufacturers();
                break;
            case DISPLAY_PRODUCTS_BY_MAN:
                listProductsByManufacturer();
                break;
            case DISPLAY_MANUFACTURER_FOR_PRODUCT:
                listSuppliersForProduct();
                break;
            case ASGN_PROD_TO_MANU:
                assignProduct();
                break;
            case UNASSIGN_PRODUCT:
                unassignProduct();
                break;
            case ACCEPT_CLIENT_PAYMNENT:
                acceptPayment();
                break;
            case SHOW_WAITLIST:
                showWaitlistedOrdersforProduct();
                break;
            case ADD_ORDER:
                createOrder();
            case DISPLAY_OUTSTANDING_BALANCE:
                outstandingBalance();
                break;
            case PROCESS_ORDER:
                recieveOrder();
                break;
            case SAVE:
                save();
                break;
            case HELP:
                help();
                break;
            case RETRIEVE:
                retrieve();
                break;

            default:
                break;
            }
        }
        save();
    }

    public void listProductsByManufacturer() {
        String m = getToken("Please enter manufacturer ID: ");
        Manufacturer manufacturer = warehouse.searchManufacturer(m);
        if (manufacturer != null) {
            Product p_temp;
            Iterator<Product> product_traverse = warehouse.getProductByMan(manufacturer);
            while (product_traverse.hasNext() != false) {
                p_temp = product_traverse.next();
                System.out.println(p_temp.getProductName());
            }
        } else {
            System.out.println("Manufacturer doesn't exist");
        }
    }

    public void listSuppliersForProduct() {
        String p = getToken("Please enter product ID: ");
        Product product = warehouse.findProduct(p);
        if (product != null) {
            Manufacturer m_temp;
            double price_temp;
            Iterator<Manufacturer> m_traversal = warehouse.getManufacturerByProd(product);
            Iterator<Double> price_traverse = warehouse.getPrice(product);

            while (m_traversal.hasNext() != false) {
                m_temp = m_traversal.next();
                price_temp = price_traverse.next();
                System.out
                        .println(m_temp.getManufacturerName() + "                               Price: " + price_temp);
            }
        } else {
            System.out.println("Product Doesnt't exist");
        }
    }

    // acceptpayment
    private void acceptPayment() {
        double payment;
        String clientName;
        clientName = getToken("Enter client id to to make payment for:");
        payment = Double.parseDouble(getToken("Enter total amount to be paid:"));
        warehouse.acceptPayment(clientName, payment);
    }

    // process an order
    private void recieveOrder() {
        String productName;
        productName = getToken("Enter Product id to recieve");

        if (warehouse.findProduct(productName) != null) {
            String productQuantity = getToken("Enter amount to receive");
            warehouse.recieveProduct(productName, Integer.parseInt(productQuantity), reader);

        } else {
            System.out.println("Couldn't find product");

        }

    }

    // save warehouse
    // Save Function
    private void save() {
        if (warehouse.save()) {
            System.out.println(" The warehouse has been successfully saved in the file WarehouseData \n");
        } else {
            System.out.println(" There has been an error in saving \n");
        }
    }

    // Retrieve saved data
    private void retrieve() {
        try {
            Warehouse tempWarehouse = Warehouse.retrieve();
            if (tempWarehouse != null) {
                System.out.println(" The warehouse has been successfully retrieved from the file WarehouseData \n");
                warehouse = tempWarehouse;
            } else {
                System.out.println("File doesn't exist; creating new warehouse");
                warehouse = Warehouse.instance();
            }
        } catch (Exception cnfe) {
            cnfe.printStackTrace();
        }
    }

    public static void main(String[] s) {
        UserInterface.instance().process();
    }

    // waitlisted orders
    public void showWaitlistedOrdersforProduct() {
        String productId;

        productId = getToken("Enter product id to show waitlisted items for");
        Product product = warehouse.findProduct(productId);

        if (product != null)
            warehouse.showWaitlistForProduct(product);

        else
            System.out.println("Coudldn't find product ");

    }

}
