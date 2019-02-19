import java.util.*;
import java.lang.*;
import java.io.*;

public class UserInterface {
    private static UserInterface userInterface;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    private static final int EXIT = 0;
    private static final int ADD_PRODUCT = 1;
    private static final int ADD_MANUFACTURER = 2;
    private static final int ADD_CLIENT = 3;
    private static final int SHOW_PRODUCTS = 4;
    private static final int SHOW_MANUFACTURERS = 5;
    private static final int SHOW_CLIENTS = 6;
    private static final int SAVE = 7;
    private static final int RETRIEVE = 8;
    private static final int ASSIGN_PM = 9;
    private static final int UNASSIGN_PM = 10;
    private static final int DISPLAY_PRODMANUFACTURER = 11;
    private static final int DISPLAY_MANUFACTURERPRODUCT = 12;
    private static final int HELP = 22;
    

    private UserInterface() {
        if (yesorNo("Look for saved data and use it")) {
            retrieve();
        } else {
            warehouse = Warehouse.instance;
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

    public int getNumber(String prompt) {
        do {
            try {
                String item = getToken(prompt);
                Integer num = Integer.valueOf(item);
                return num.intValue();
            } catch (NumberFormatException nfe) {
                System.out.println("Please input a number ");
            }
        } while (true);
    }

    public Calendar getDate(String prompt) {
        do {
            try {
                Calendar date = new GregorianCalendar();
                String item = getToken(prompt);
                DateFormat df = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
                date.setTime(df.parse(item));
                return date;
            } catch (Exception fe) {
                System.out.println("Please input a date as mm/dd/yy");
            }
        } while (true);
    }

    public int getCommand() {
        do {
            try {
                int value = Integer.parseInt(getToken("Enter command: (enter " + HELP + " for help)"));
                if (value >= EXIT && value <= HELP) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
    }

    public void help() {
        System.out.println("Enter a number between " + EXIT + " and " + HELP + " as explained below:\n");
        System.out.println(EXIT + " to Exit\n");
        System.out.println(ADD_PRODUCT + " to add products");
        System.out.println(ADD_MANUFACTURER + " to add a manufacturer");
        System.out.println(ADD_CLIENT + " to add a client");
        System.out.println(SHOW_PRODUCTS + " to show all products");
        System.out.println(SHOW_MANUFACTURERS + " to show all manufacturers");
        System.out.println(SHOW_CLIENTS + " to show all clients");
        System.out.println(SAVE + " to save data");
        System.out.println(RETRIEVE + " to retrieve");
        System.out.println(ASSIGN_PM + " to assign a product to manufacturer");
        System.out.println(UNASSIGN_PM + " to unassign a product from manufacturer");
        System.out.println(DISPLAY_PRODMANUFACTURER + " to display the manufacturers for a product");
        System.out.println(DISPLAY_MANUFACTURERPRODUCT + " to display the products from a manufacturer");
        System.out.println(HELP + " for help\n");
    }


}