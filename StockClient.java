package tp5res;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class StockClient extends UnicastRemoteObject implements ClientInterface {

    private StockInterface server;

    public StockClient(String serverUrl) throws RemoteException, NotBoundException, MalformedURLException {
        super();
        server = (StockInterface) Naming.lookup(serverUrl);
        server.registerClient(this);
    }

    public void notify(String message) throws RemoteException {
        System.out.println(message);
    }

    public void displayProducts() throws RemoteException {
        List<String> products = server.getProducts();
        System.out.println("Liste des produits :");
        for (String product : products) {
            int quantity = server.getQuantity(product);
            System.out.println("- " + product + " : " + quantity);
        }
    }

    public void addProduct(String productName, int quantity) throws RemoteException {
        server.addProduct(productName, quantity);
    }

    public void removeProduct(String productName, int quantity) throws RemoteException {
        server.removeProduct(productName, quantity);
    }

    public static void main(String args[]) {
        try {
            StockClient client = new StockClient("rmi://localhost/StockServer");

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("Que voulez-vous faire ?\n");
                System.out.println("1 - Afficher le stock");
                System.out.println("2 - Ajouter un produit");
                System.out.println("3 - Retirer un produit");
                System.out.println("4 - Quitter");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        client.displayProducts();
                        break;
                    case 2:
                        System.out.println("Nom du produit :");
                        String productName = scanner.next();
                        System.out.println("Quantité à ajouter :");
                        int quantityToAdd = scanner.nextInt();
                        client.addProduct(productName, quantityToAdd);
                        break;
                    case 3:
                        System.out.println("Nom du produit :");
                        String productToRemove = scanner.next();
                        System.out.println("Quantité à retirer :");
                        int quantityToRemove = scanner.nextInt();
                        client.removeProduct(productToRemove, quantityToRemove);
                        break;
                    case 4:
                        running = false;
                        break;
                    default:
                        System.out.println("Choix invalide.");
                        break;
                }
            }

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}

