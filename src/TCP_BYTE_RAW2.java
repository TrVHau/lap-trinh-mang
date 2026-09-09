import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class TCP_BYTE_RAW2 {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("36.50.135.242", 2206);

            OutputStream out = client.getOutputStream();
            InputStream in = client.getInputStream();

            out.write("B23DCCN287;nS46oEic".getBytes());
            out.flush();

            byte[] buffer = new byte[1024];
            int length = in.read(buffer);

            String response = new String(buffer, 0, length);

            String[] parts = response.split(",");

            int[] numbers = new int[parts.length];

            for (int i = 0; i < parts.length; i++) {
                numbers[i] = Integer.parseInt(parts[i].trim());
            }

            int ans=numbers[0];
            int max=Arrays.stream(numbers).max().getAsInt();
            int index=0;
            for(int i=0;i<numbers.length;i++){
                if(numbers[i]!=max&&numbers[i]>ans){
                    index=i;
                    ans=numbers[i];
                }
            }

            System.out.println(Arrays.toString(numbers));
            System.out.println(ans);
            System.out.println(index);


            out.write((ans+","+index).getBytes());
            out.flush();

            in.close();
            out.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}