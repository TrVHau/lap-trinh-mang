package RMI;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CharacterService extends Remote {
    public String requestCharacter(String studentCode, String qCode) throws RemoteException;
    public void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
}