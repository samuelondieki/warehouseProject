import java.util.*;
import java.text.*;
import java.io.*;

public class Manufacturer implements Serializable {
	private static final long serialVersionUID = 1L;
	private String Name;
	private String Address;
	private String Phone;
	private String manufacturerId;
	private static final String MANUFACTURER_STRING = "m";
	private List<Product> Products = new LinkedList<Product>();

	private List booksBorrowed = new LinkedList();
	private List booksOnHold = new LinkedList();
	private List transactions = new LinkedList();

	public boolean assignProduct(Product P) {
		if (Products.add(P)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean unassignProduct(Product P) {
		if (Products.remove(P)) {
			return true;
		} else {
			return false;
		}
	}

	public Manufacturer(String Name, String Address, String Phone) {
		this.Name = Name;
		this.Address = Address;
		this.Phone = Phone;
		manufacturerId = MANUFACTURER_STRING + (ManufacturerIdServer.instance()).getId();
	}

	public String getManufacturerName() {
		return Name;
	}

	public Iterator<Product> getProducts() {
		return Products.iterator();
	}

	public String getManufacturerPhone() {
		return Phone;
	}

	public String getManufacturerAddress() {
		return Address;
	}

	public String getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerName(String newName) {
		Name = newName;
	}

	public void setManufacturerAddress(String newAddress) {
		Address = newAddress;
	}

	public void setManufacturerPhone(String newPhone) {
		Phone = newPhone;
	}

	public boolean equals(String id) {
		return this.manufacturerId.equals(manufacturerId);
	}

	public String toString() {
		String string = "\nManufacturer name: " + Name + "\nAddress: " + Address + "\nID: " + manufacturerId
				+ "\nPhone: " + Phone + "\n";
		return string;
	}
}
