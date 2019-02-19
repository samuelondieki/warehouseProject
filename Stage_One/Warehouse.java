import java.util.*;
import java.lang.*;
import java.io.*;

public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Inventory inventory;
    private ManufacturerList manufacturerList;
    private static Warehouse warehouse;

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
}