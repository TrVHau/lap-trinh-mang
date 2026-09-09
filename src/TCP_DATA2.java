import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCP_DATA2 {
    public static void main(String[] args) throws Exception {
        String url = "36.50.135.242";
        int PORT = 2207;

        Socket socket = new Socket(url, PORT);

        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        out.writeUTF("B23DCCN287;O0pNyTVd");

        String response = in.readUTF();
        int c = in.readInt();

        System.out.println("String: " + response);
        System.out.println("Integer: " + c);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < response.length(); i++) {
            char ch = response.charAt(i);
            if(ch >= 'A' && ch <= 'Z') {
                ch = (char) ('A' + (ch - 'A' - c+26) % 26);
            }
            result.append(ch);
        }

        System.out.println("Decrypted: " + result.toString());
        out.writeUTF(result.toString());


        in.close();
        out.close();
        socket.close();
    }
}