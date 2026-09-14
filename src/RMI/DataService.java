package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DataService extends Remote {

    Integer[] requestData(
            String studentCode,
            String qCode
    ) throws RemoteException;
    void submitData(
            String studentCode,
            String qCode,
            List<Integer> data
    ) throws RemoteException;
}