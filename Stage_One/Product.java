import java.util.*;
import java.lang.*;
import java.io.*;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L; // what does this do
    private double price;
    private String name;
    private String id;
    private int quantity;
    private List Manufacturer;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.Manufacturer = new LinkedList();
        // not sure about this one (to assign an ID to a product )
        id = PRODUCT_STRING + (ProductIdServer.instance()).getId();
    }

    // setters
    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    pbulic String

    getId(){
        return id;
    }

    // search Manufacuturer
    public ProdcuctList searchManufacturer(String id) {
        Iterator productMan = Manufacturer.iterator();
        while (product.hasNext()) {
            ProductList product = (ProductList) product.next;
            if (product.getManufacturer().equals(id)) {
                return productMan;
            }
        }
        return null;
    }

    // assign Manufacturer
    public boolean assignManufacturer(ProdcuctList PL) {
        return mManufacturer.add(PL);
    }

    // unassign Manufacturer
    public boolean unassignManufacturer(ProductList PL) {
        return mManufacturer.remove(PL);
    }

    // count manufacturers
    public List getManufacturerCount() {
        return Manufacturer.size();
    }

    // get Manufacturers
    public Iterator getManufacturer() {
        return Manufacturer.iterator();
    }

}