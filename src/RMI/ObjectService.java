package RMI;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObjectService extends Remote {
    Serializable requestObject(String studentCode, String qCode) throws RemoteException;
    void submitObject(String studentCode, String qCode, Serializable object) throws RemoteException;
}
