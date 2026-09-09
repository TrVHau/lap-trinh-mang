import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.StringJoiner;

public class TCP_CHARACTER1 {
    public static void main(String[] args) throws Exception {
        String url = "36.50.135.242";
        int PORT = 2208;

        Socket socket = new Socket(url,PORT);
        BufferedReader in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        out.write("B23DCCN287;JpwrcgU7");
        out.newLine();
        out.flush();

        String response  =  in.readLine();
        String[] parts = response.split(",");
        StringJoiner sb = new StringJoiner(",");
        for(String s:parts){
            if(s.endsWith(".edu"))
                sb.add(s);
        }
        System.out.print(sb.toString());
        out.write(sb.toString());
        out.flush();

        in.close();
        out.close();
        socket.close();

    }
}