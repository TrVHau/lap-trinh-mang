import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TCP_GZIP2 {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("36.50.135.242",2210);

        GZIPInputStream gis = new GZIPInputStream(socket.getInputStream());
        GZIPOutputStream gos = new GZIPOutputStream(socket.getOutputStream(),true);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(gos, StandardCharsets.UTF_8));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bw.write("B23DCCN287;Ggjuxk1F");
        bw.write("\n");
        bw.flush();

        int c;
        while((c=gis.read())!=-1){
            if(c=='\n'){
                break;
            }
            baos.write(c);
        }

        String reponse = baos.toString(StandardCharsets.UTF_8);
        System.out.println("Server: "+reponse);
        char[] chars = reponse.toCharArray();

        Arrays.sort(chars);
        String result = new String(chars);

        System.out.println("send: "+result );
        bw.write(result);
        bw.write("\n");
        bw.flush();

        socket.close();
    }
}
