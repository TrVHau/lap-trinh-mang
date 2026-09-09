import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import TCP.Laptop;

public class TCP_OBJECT1 {
    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("36.50.135.242", 2209);

        ObjectOutputStream out =
                new ObjectOutputStream(socket.getOutputStream());

        ObjectInputStream in =
                new ObjectInputStream(socket.getInputStream());

        // 1. Gửi studentCode;qCode
        String request = "B23DCCN287;SKZERn0K";

        out.writeObject(request);
        out.flush();

        // 2. Nhận Laptop
        Laptop laptop = (Laptop) in.readObject();

        System.out.println("Laptop nhận được:");
        System.out.println(laptop);

        // 3. Sửa tên
        String[] words = laptop.getName().split(" ");

        // Đảo từ đầu tiên và cuối cùng
        String temp = words[0];
        words[0] = words[words.length - 1];
        words[words.length - 1] = temp;

        laptop.setName(String.join(" ", words));

        // Sửa quantity
        String quantity = String.valueOf(laptop.getQuantity());

        String reversedQuantity =
                new StringBuilder(quantity).reverse().toString();

        laptop.setQuantity(Integer.parseInt(reversedQuantity));

        System.out.println("Laptop sau khi sửa:");
        System.out.println(laptop);

        // Gửi Laptop đã sửa
        out.writeObject(laptop);
        out.flush();

        // 4. Đóng socket
        socket.close();
    }
}