import java.util.*;
import java.lang.*;
import java.io.*;

public class ManufacturerList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Manufacturer> manuList = new LinkedList<Manufacturer>();
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

    public boolean insertManufacturer(Manufacturer manufacturer) {
        return manuList.add(manufacturer);
    }

    public Iterator getManufacturerList() {
        return manuList.iterator();
    }

    public Manufacturer search(String id) {
        Iterator mans = manuList.iterator();
        while (mans.hasNext()) {
            Manufacturer manufacturer = (Manufacturer) mans.next();
            if (manufacturer.getId().equals(id)) {
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
            System.out.println(ioe);
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
            System.out.println("in ManufacturerList readObject \n" + ioe);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public String toString() {
        return manuList.toString();
    }
}
