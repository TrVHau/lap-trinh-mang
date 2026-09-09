import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class TCP_BYTE_RAW1 {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("36.50.135.242", 2206);

            OutputStream out = client.getOutputStream();
            InputStream in = client.getInputStream();

            out.write("B23DCCN287;FsQYNYLn".getBytes());
            out.flush();

            byte[] buffer = new byte[1024];
            int length = in.read(buffer);

            String response = new String(buffer, 0, length);

            String[] parts = response.split(",");

            int[] numbers = new int[parts.length];

            for (int i = 0; i < parts.length; i++) {
                numbers[i] = Integer.parseInt(parts[i].trim());
            }

            Arrays.sort(numbers);

            int diff = Integer.MAX_VALUE;
            int num1 = 0;
            int num2 = 0;

            for (int i = 0; i < numbers.length - 1; i++) {
                int currentDiff = numbers[i + 1] - numbers[i];

                if (currentDiff < diff) {
                    diff = currentDiff;
                    num1 = numbers[i];
                    num2 = numbers[i + 1];
                }
            }

            String result = diff + "," + num1 + "," + num2;

            System.out.println("Response: " + response);
            System.out.println("Result: " + result);

            out.write(result.getBytes());
            out.flush();

            in.close();
            out.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}