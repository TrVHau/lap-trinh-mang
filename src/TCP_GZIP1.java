import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.Arrays;

public class TCP_GZIP1 {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("36.50.135.242", 2210);
        socket.setSoTimeout(5000);

        GZIPOutputStream gos =
                new GZIPOutputStream(socket.getOutputStream(), true);

        GZIPInputStream gis =
                new GZIPInputStream(socket.getInputStream());

        gos.write("B23DCCN287;0J7T6Igp\n".getBytes(StandardCharsets.UTF_8));
        gos.flush();

        int[] data = new int[10000];
        int n = 0, c;

        while ((c = gis.read()) != -1 && c != '\n')
            data[n++] = c;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++)
            sb.append((char) data[i]);
        String response = sb.toString();

        System.out.println("Server: " + response);

        String reversed = new StringBuilder(response).reverse().toString();

        String base64 = Base64.getEncoder().encodeToString(
                reversed.getBytes(StandardCharsets.UTF_8)
        );

        String result = reversed + "|" + base64;

        System.out.println("Result: " + result);

        gos.write((result + "\n").getBytes(StandardCharsets.UTF_8));
        gos.flush();

        socket.close();
    }
}
