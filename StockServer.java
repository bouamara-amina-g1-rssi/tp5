package tp5res;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class StockServer extends UnicastRemoteObject implements StockInterface {

    private Map<String, Integer> stock;
    private List<ClientInterface> clients;

    public StockServer() throws RemoteException {
        super();
        stock = new HashMap<String, Integer>();
        clients = new ArrayList<ClientInterface>();
    }

    public int getQuantity(String productName) throws RemoteException {
        if (stock.containsKey(productName)) {
            return stock.get(productName);
        } else {
            return 0;
        }
    }

    public List<String> getProducts() throws RemoteException {
        return new ArrayList<String>(stock.keySet());
    }

    public void addProduct(String productName, int quantity) throws RemoteException {
        if (stock.containsKey(productName)) {
            stock.put(productName, stock.get(productName) + quantity);
        } else {
            stock.put(productName, quantity);
        }
        notifyClients(productName + " a été ajouté au stock.");
    }

    public void removeProduct(String productName, int quantity) throws RemoteException {
        if (stock.containsKey(productName)) {
            int newQuantity = stock.get(productName) - quantity;
            if (newQuantity > 0) {
                stock.put(productName, newQuantity);
            } else {
                stock.remove(productName);
            }
            notifyClients(productName + " a été retiré du stock.");
        }
    }

    public void registerClient(ClientInterface client) throws RemoteException {
        clients.add(client);
    }

    private void notifyClients(String message) throws RemoteException {
        for (ClientInterface client : clients) {
            client.notify(message);
        }
    }

    public static void main(String args[]) throws Exception {
        StockServer server = new StockServer();
        Naming.rebind("StockServer", server);
        System.out.println("Stock server ready.");
    }
}

