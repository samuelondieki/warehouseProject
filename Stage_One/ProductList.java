
//Group 26
import java.util.*;
import java.lang.*;
import java.io.*;

public class ProdcuctList implements Serializabale {
    private static final long serialversionUID = 1L;
    private Manufacturer manufacturer;
    private Product product;
    private double price;

    public ProductList(){};

    public ProdcuctList(Product prod, Manufacturer manu, double price) {
        this.product = prod;
        this.manufacturer = manu;
        this.price = price;
    }

    // getters
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public Product getProduct() {
        return product;
    }

    public double getPrice() {
        return price;
    }

    // setters
    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // get product and manufacturer ID
    public String getProductId() {
        return product.getId();
    }

    public String getManufacturerId(){
        return manufacturer.getId
    }

    // overide to display the info..
    public String productString() {
        String string = product.toString() + ", price: $" + price;
        return string;
    }

    public String manufacturerString() {
        String string = manufacturer.toString() + ", supply price: $" + price;
        return string;
    }

}// end of file

// ~Samuel Ondieki