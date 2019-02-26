import java.util.*;
import java.text.*;
import java.io.*;

public class Client implements Serializable {
  private static final long serialVersionUID = 1L;
  private String Name;
  private String Address;
  private String Phone;
  private String clientID;
  private static final String CLIENT_STRING = "C";

  public Client(String Name, String Address, String Phone) {
    this.Name = Name;
    this.Address = Address;
    this.Phone = Phone;
    clientID = CLIENT_STRING + (ClientIdServer.instance()).getId();
  }

  public String getClientName() {
    return Name;
  }

  public String getClientPhone() {
    return Phone;
  }

  public String getClientAddress() {
    return Address;
  }

  public String getClientId() {
    return clientID;
  }

  public void setClientName(String newName) {
    Name = newName;
  }

  public void setClientAddress(String newAddress) {
    Address = newAddress;
  }

  public void setClientPhone(String newPhone) {
    Phone = newPhone;
  }

  public boolean equals(String id) {
    return this.clientID.equals(clientID);
  }

  public String toString() {
    String string = "\nClient name: " + Name + "\nAddress: " + Address + "\nID: " + clientID + "\nPhone: " + Phone
        + "\n";
    return string;
  }
}
