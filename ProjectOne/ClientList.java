import java.util.*;
import java.text.*;
import java.io.*;

public class ClientList implements Serializable {
  private static final long serialVersionUID = 1L;
  private List clients = new LinkedList();
  private static ClientList clientList;

  private ClientList() {
  }

  public static ClientList instance() {
    if (clientList == null) {
      return (clientList = new ClientList());
    } else {
      return clientList;
    }
  }

  public boolean insertMember(Client client) {
    clients.add(client);
    return true;
  }

  public Iterator getAllClients() {
    return clients.iterator();
  }

  // credit payment
  public void creditClient(String id, double amount) {
    Client client = findClient(id);
    if (client != null) {
      client.debitInvoice(amount);
      System.out.println("Credited: " + id + " for " + amount);
    } else
      System.out.println("Couldn't Credit  " + id);
  }

  // debit payment
  public void debitClient(String id, double amount) {
    Client client = findClient(id);
    if (client != null) {
      client.creditInvoice(amount);
      System.out.println("Debited: " + id + " for " + amount);
    } else
      System.out.println("Couldn't Debit  " + id);
  }

  // find a particular client
  public Client findClient(String potentialId) {
    Iterator find = clients.iterator();

    while (find.hasNext()) {
      Client client = (Client) find.next();
      if (client.equal(potentialId))
        return client;
    }
    return null;
  }

  // unpaid balances
  public void listoutstandingBalances() {
    Iterator it = clients.iterator();

    while (it.hasNext()) {
      Client client = (Client) it.next();

      if (client.getInvoicedAmount() > 0) {
        System.out
            .println("Client " + client.getClientId() + " has a outstanding balance of: " + client.getInvoicedAmount());

      }
    }

  }

  private void writeObject(java.io.ObjectOutputStream output) {
    try {
      output.defaultWriteObject();
      output.writeObject(clientList);
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  private void readObject(java.io.ObjectInputStream input) {
    try {
      if (clientList != null) {
        return;
      } else {
        input.defaultReadObject();
        if (clientList == null) {
          clientList = (ClientList) input.readObject();
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
    return clients.toString();
  }
}
