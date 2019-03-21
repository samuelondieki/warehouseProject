import java.util.*;
import java.text.*;
import java.io.*;

public class Client implements Serializable {
  private static final long serialVersionUID = 1L;
  private String Name;
  private String Address;
  private String Phone;
  private String clientID;
  private double invoicedAmount = 0.00;

  private static final String CLIENT_STRING = "C";

  public Client(String Name, String Address, String Phone) {
    this.Name = Name;
    this.Address = Address;
    this.Phone = Phone;
    clientID = CLIENT_STRING + (ClientIdServer.instance()).getId();
  }

  public boolean equal(String id) {
    return this.clientID.equals(id);
  }

  // getters
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

  public double getInvoicedAmount() {
    return invoicedAmount;
  }

  // setters

  public void setClientAddress(String newAddress) {
    Address = newAddress;
  }

  public void setClientPhone(String newPhone) {
    Phone = newPhone;
  }

  public boolean equals(String id) {
    return this.clientID.equals(clientID);
  }

  // debit
  public void debitInvoice(double money) {
    this.invoicedAmount += money;
  }

  // // credit
  // public void creditInvoice(double money) {
  // if (invoicedAmount >= 0.000) {
  // invoicedAmount -= money;
  // }
  // // System.out.println("No invoiced amount to credit");
  // }

  // credit
  public void creditInvoice(double money) {
    this.invoicedAmount -= money;
  }
  // System.out.println("No invoiced amount to credit");

  @Override
  public String toString() {
    String string = "\nClient name: " + Name + "\nAddress: " + Address + "\nID: " + clientID + "\nPhone: " + Phone
        + "\ninvoicedAmount:" + invoicedAmount + "";
    return string;
  }

}
