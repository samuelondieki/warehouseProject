import java.util.*;
import java.text.*;
import java.io.*;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private String Name;
    private String Description;
    private String productId;
    private int quantity;
    private double cost;
    private static final String PRODUCT_STRING = "p";
    private List<Manufacturer> productManufacturer = new LinkedList<Manufacturer>();
    private List<Double> productPrices = new LinkedList<Double>();

    public Product(String Name, double cost, int quantity) {
        this.Name = Name;
        this.cost = cost;
        this.quantity = quantity;
        this.setCost(cost);
        productId = PRODUCT_STRING + (ProductIdServer.instance()).getId();
    }

    public int searchSupplier(Manufacturer supplier) {
        int i = 0;
        for (; i <= productManufacturer.size() - 1; i++) {
            if ((productManufacturer.get(i)) == supplier) {
                return i;
            }
        }
        i = -1;
        return i;
    }

    public boolean assign(Manufacturer supplier) {
        return productManufacturer.add(supplier) ? true : false;
    }

    public boolean Unassign(Manufacturer supplier) {
        if (productManufacturer.remove(supplier)) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean addPrice(double price) {
        return productPrices.add(price) ? true : false;
    }

    public Boolean removePrice(int position) {
        if (productPrices.remove(position) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getID() {
        return productId;
    }

    public Iterator<Manufacturer> getManufacturers() {
        return productManufacturer.iterator();
    }

    public Iterator<Double> getManufacturerPrices() {
        return productPrices.iterator();
    }

    public String getProductDescription() {
        return Description;
    }

    public String getProductName() {
        return Name;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String toString() {
        return "\nNAME: " + Name + "\nCOST: " + cost + "\nID: " + productId + "\nQuantity: " + quantity;
    }
}
