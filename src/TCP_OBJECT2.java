import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import TCP.Customer;

public class TCP_OBJECT2 {
    private static String nomalizeName(String name){
        String[] parts = name.split(" ");
        StringBuilder sb = new StringBuilder();
        int n =parts.length;
        sb.append(parts[n-1].toUpperCase()).append(", ");
        for(int i=0;i<n-1;i++){
            String word = parts[i].toUpperCase().charAt(0)+parts[i].toLowerCase().substring(1);
            sb.append(word);
            if(i!=n-2) sb.append(" ");
        }
        return sb.toString();
    }

    private static String genUserName(String name){
        String[] parts = name.split(" ");
        StringBuilder sb = new StringBuilder();
        int n = parts.length;
        for(int i=0;i<n-1;i++){
            sb.append(parts[i].toLowerCase().charAt(0));
        }
        sb.append(parts[n-1].toLowerCase());
        return sb.toString();
    }

    private static String nomalizeDate(String date){
        StringBuilder sb = new StringBuilder();
        sb.append(date.substring(3,5)).append('/').append(date.substring(0,2)).append('/').append(date.substring(6));
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("36.50.135.242", 2209);

        ObjectOutputStream out =
                new ObjectOutputStream(socket.getOutputStream());

        ObjectInputStream in =
                new ObjectInputStream(socket.getInputStream());

        String request = "B23DCCN287;R4FoAWWZ";

        out.writeObject(request);
        out.flush();

        Customer customer= (Customer) in.readObject();

        System.out.println("Customer:");
        System.out.println(customer);

        customer.setUserName(genUserName(customer.getName()));

        customer.setName(nomalizeName(customer.getName()));

        customer.setDayOfBirth(nomalizeDate(customer.getDayOfBirth()));

        System.out.println("Customer sau khi sửa:");
        System.out.println(customer);


        out.writeObject(customer);
        out.flush();

        socket.close();
    }
}