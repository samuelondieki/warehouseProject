import java.util.*;
import java.text.*;
import java.io.*;

public class ManufacturerList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List<Manufacturer> manufacturers = new LinkedList<Manufacturer>();
  private static ManufacturerList manufacturerList;

  private ManufacturerList() {
  }

  public static ManufacturerList instance() {
    if (manufacturerList == null) {
      return (manufacturerList = new ManufacturerList());
    } else {
      return manufacturerList;
    }
  }

  public boolean insertManufacturer(Manufacturer newManufacturer) {
    manufacturers.add(newManufacturer);
    return true;
  }

  public Iterator getAllManufacturers() {
    return manufacturers.iterator();
  }

  public boolean searchMan(String searchProductId) {
    boolean discover = false;
    Iterator<Manufacturer> manufacturerIterator = manufacturers.iterator();
    while (manufacturerIterator.hasNext()) {
      Manufacturer manufacturer = (Manufacturer) manufacturerIterator.next();
      if (manufacturer.getManufacturerId().equals(searchProductId)) {
        discover = true;
        return discover;
      }
    }
    System.out.println("product not discovered");
    return discover;
  }

  public Manufacturer searchId(String searchManufacturerId) {
    for (Iterator iterator = manufacturers.iterator(); iterator.hasNext();) {
      Manufacturer manufacturer = (Manufacturer) iterator.next();
      if (manufacturer.getManufacturerId().equals(searchManufacturerId)) {
        return manufacturer;
      }
    }
    return null;
  }

  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(manufacturerList);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (manufacturerList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (manufacturerList == null) {
          manufacturerList = (ManufacturerList) input.readObject();
        } else {
          input.readObject();
        }
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } catch (ClassNotFoundException cnfe) {
      cnfe.printStackTrace();
    }
  }

  public String toString() {
    return manufacturers.toString();
  }
}
