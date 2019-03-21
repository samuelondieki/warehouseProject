import java.util.*;
import java.text.*;
import java.io.*;

public class ProductList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Product> products = new LinkedList<Product>();
    private static ProductList productList;

    private ProductList() {
    }

    public static ProductList instance() {
        if (productList == null) {
            return (productList = new ProductList());
        } else {
            return productList;
        }
    }

    public boolean searchProduct(String searchProductID) {
        boolean discover = false;
        Iterator<Product> productsIterator = products.iterator();
        while (productsIterator.hasNext()) {
            Product product = (Product) productsIterator.next();
            if (product.getProductId().equals(searchProductID)) {
                discover = true;
                return discover;
            }
        }
        System.out.println("product not discovered");
        return discover;
    }

    public Product search(String productId) {
        for (Iterator iterator = products.iterator(); iterator.hasNext();) {
            Product product = (Product) iterator.next();
            if (product.getID().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public boolean insertProduct(Product prod) {
        products.add(prod);
        return true;
    }

    public Iterator getAllProducts() {
        return products.iterator();
    }

    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(productList);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (productList != null) {
                return;
            } else {
                input.defaultReadObject();
                if (productList == null) {
                    productList = (ProductList) input.readObject();
                } else {
                    input.readObject();
                }
            }
        } catch (IOException ioe) {
            System.out.println("in ProductList readObject \n" + ioe);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public String toString() {
        return products.toString();
    }
}
