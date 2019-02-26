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
    private static final int HELP = 11;

    private UserInterface() {
        warehouse = Warehouse.instance();
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
        System.out.println(DISPLAY_MANUFACTURER_FOR_PRODUCT + " Dsiplay list of manufacturers for a product");
        System.out.println(UNASSIGN_PRODUCT + " Unassign a product");
        System.out.println(HELP + " for help");
    }

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

    public void addNewProduct() {
        Product result;
        do {
            String pName = getToken("Enter name");
            String pDesc = getToken("Enter description");
            result = warehouse.addProduct(pName, pDesc);
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

    public void showProducts() {
        Iterator allProducts = warehouse.getProducts();
        while (allProducts.hasNext()) {
            Product product = (Product) (allProducts.next());
            System.out.println(product.toString());
        }
    }

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
        Product product;
        if ((product = warehouse.searchProduct(pId)) == null) {
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
        if ((product = warehouse.searchProduct(pId)) == null) {
            System.out.println("Product does not exist.");
            return;
        }

        String mId = getToken("Enter manufacturer ID: ");
        Manufacturer m;
        if ((m = warehouse.searchManufacturer(mId)) == null) {
            System.out.println("No such manufacturer.");
            return;
        }
        warehouse.unassignProdFromManufacturer(pId, mId);
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
            case HELP:
                help();
                break;
            case UNASSIGN_PRODUCT:
                unassignProduct();
                break;

            }
        }
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
        Product product = warehouse.searchProduct(p);
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

    public static void main(String[] s) {
        UserInterface.instance().process();
    }
}