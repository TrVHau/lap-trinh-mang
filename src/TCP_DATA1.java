import java.io.*;
import java.net.Socket;

public class TCP_DATA1 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("36.50.135.242", 2207);

            DataInputStream in =
                    new DataInputStream(socket.getInputStream());

            DataOutputStream out =
                    new DataOutputStream(socket.getOutputStream());

            out.writeUTF("B23DCCN287;0QQfDFRy");
            out.flush();

            int a = in.readInt();
            int b = in.readInt();

            System.out.println("a = " + a);
            System.out.println("b = " + b);

            out.writeInt(a+b);
            out.writeInt(a*b);
            out.flush();

            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}