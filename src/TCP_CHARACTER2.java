import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TCP_CHARACTER2 {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("36.50.135.242", 2208);

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            out.write("B23DCCN287;T6Df8iQL");
            out.newLine();
            out.flush();

            String response = in.readLine();
            Map<Character,Integer> map = new HashMap<>();

            for(char c:response.toCharArray()){
                map.put(c,map.getOrDefault(c,0)+1);
            }
            map.put(' ',0);
            StringBuilder builder = new StringBuilder();
            for(char c:response.toCharArray()){
                if(map.get(c)>1){
                    builder.append(c+":"+map.get(c)+",");
                    map.put(c,0);
                }
            }

            System.out.println(builder.toString());

            out.write(builder.toString());
            out.newLine();
            out.flush();

            in.close();
            out.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}