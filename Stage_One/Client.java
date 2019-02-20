
import java.util.*;
import java.io.*;

public class Client implements Serializable {
	private static final long serialVersionUID = 1L; // magic spell
	private String name;
	private String address;
	private String phone;
	private String id;
	private static final String CLIENT_STRING = "C";

	public Client(String name, String address, String phone) {

		this.name = name;
		this.address = address;
		this.phone = phone;
		id = CLIENT_STRING + (ClientIdServer.instance()).getId();
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

	public String toString() {
		String string = "Client ID: " + id + ", name: " + name + ", address: " + address + ", phone: " + phone;
		return string;
	}

}