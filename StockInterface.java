package tp5res;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface StockInterface extends Remote {
    int getQuantity(String productName) throws RemoteException;
    List<String> getProducts() throws RemoteException;
    void addProduct(String productName, int quantity) throws RemoteException;
    void removeProduct(String productName, int quantity) throws RemoteException;
    void registerClient(ClientInterface client) throws RemoteException;
}