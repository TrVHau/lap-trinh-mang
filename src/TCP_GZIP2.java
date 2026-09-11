
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TCP_GZIP2 {
    public static void main(String[] args) throws Exception {

        Socket s = new Socket("36.50.135.242", 2210);

        GZIPInputStream in = new GZIPInputStream(s.getInputStream());
        GZIPOutputStream out = new GZIPOutputStream(s.getOutputStream(), true);

        out.write("B23DCCN287;Ggjuxk1F\n".getBytes(StandardCharsets.UTF_8));
        out.flush();

        StringBuilder sb = new StringBuilder();
        int c;

        while ((c = in.read()) != '\n' && c != -1)
            sb.append((char)c);

        String response = sb.toString();
        System.out.println("Server: " + response);

        char[] a = response.toCharArray();
        Arrays.sort(a);

        String result = new String(a);
        System.out.println("Send: " + result);

        out.write((result + "\n").getBytes(StandardCharsets.UTF_8));
        out.flush();

        s.close();
    }
}
