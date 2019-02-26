import java.util.*;
import java.text.*;
import java.io.*;

public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    private ClientList clientList;
    private ProductList productList;
    private ManufacturerList manufacturerList;
    private static Warehouse warehouse;

    private Warehouse() {
        clientList = ClientList.instance();
        manufacturerList = ManufacturerList.instance();
        productList = ProductList.instance();
    }

    public static Warehouse instance() {
        if (warehouse == null) {
            ManufacturerIdServer.instance();
            ProductIdServer.instance();
            ClientIdServer.instance();
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

    public Product addProduct(String Name, String Description) {
        Product prod = new Product(Name, Description);
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

    public Product searchProduct(String pId) {
        return productList.search(pId);
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
}
