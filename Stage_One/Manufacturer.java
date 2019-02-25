import java.util.*;
import java.lang.*;
import java.io.*;

public class Manufacturer implements Serializable {
    private static final long serialVersionUID = 1L; // magic spell
    private String name;
    private String address;
    private String phone;
    private String id;

    private static final String MANUFACTURER_STRING = "M";
    private List<ProdMan> productsCarried;

    public Manufacturer(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.productsCarried = new LinkedList<ProdMan>();
        id = MANUFACTURER_STRING + (ManufacturerIdServer.instance()).getId();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean equals(String id) {
        return this.id.equals(id);
    }

    public ProdMan searchProd(String id) {
        Iterator prods = productsCarried.iterator();
        while (prods.hasNext()) {
            ProdMan prodman = (ProdMan) prods.next();
            if (prodman.getProdId().equals(id)) {
                return prodman;
            }
        }
        return null;
    }

    public boolean assignProduct(ProdMan pm) {
        return productsCarried.add(pm);
    }

    public boolean unassignProduct(ProdMan pm) {
        return productsCarried.remove(pm);
    }

    public boolean hasProduct(ProdMan pm) {
        return productsCarried.contains(pm);
    }

    public int getProductCount() {
        return productsCarried.size();
    }

    public Iterator getProducts() {
        return productsCarried.iterator();
    }

    public String toString() {
        String string = "Manufacturer ID: " + id + ", name: " + name + ", address: " + address + ", phone: " + phone;
        return string;
    }

}