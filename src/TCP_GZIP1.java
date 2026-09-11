import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TCP_GZIP1 {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("36.50.135.242", 2210);
        socket.setSoTimeout(5000);

        GZIPOutputStream gos =
                new GZIPOutputStream(
                        socket.getOutputStream(),
                        true
                );

        BufferedWriter bw =
                new BufferedWriter(
                        new OutputStreamWriter(
                                gos,
                                StandardCharsets.UTF_8
                        )
                );

        GZIPInputStream gis =
                new GZIPInputStream(
                        socket.getInputStream()
                );

        bw.write("B23DCCN287;0J7T6Igp");
        bw.write("\n");
        bw.flush();

        ByteArrayOutputStream baos =
                new ByteArrayOutputStream();

        int c;

        while ((c = gis.read()) != -1) {
            if (c == '\n') {
                break;
            }
            baos.write(c);
        }

        String response =
                baos.toString(StandardCharsets.UTF_8);

        System.out.println("Server: " + response);

        String reversed =
                new StringBuilder(response)
                        .reverse()
                        .toString();

        String base64 =
                Base64.getEncoder()
                        .encodeToString(
                                reversed.getBytes(StandardCharsets.UTF_8)
                        );

        String result =
                reversed + "|" + base64;

        System.out.println("Result: " + result);

        bw.write(result);
        bw.write("\n");
        bw.flush();

        socket.close();
    }
}