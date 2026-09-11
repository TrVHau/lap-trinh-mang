import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_DATA1 {
    public static void main(String[] args) throws Exception {
        String url = "36.50.135.242";
        int PORT = 2207;

        DatagramSocket socket = new DatagramSocket();

        byte[] data = ";B23DCCN287;MU3h4jq9".getBytes();

        InetAddress address = InetAddress.getByName(url);

        DatagramPacket packet1 =
                new DatagramPacket(data, data.length, address, PORT);

        socket.send(packet1);

        byte[] buffer = new byte[1024];

        DatagramPacket packet2 =
                new DatagramPacket(buffer, buffer.length);

        socket.receive(packet2);

        String response =
                new String(
                        packet2.getData(),
                        0,
                        packet2.getLength()
                );

        System.out.println("Server: " + response);

        String[] parts = response.split(";");

        String requestId = parts[0];
        int n = Integer.parseInt(parts[1]);

        int[] a = new int[n + 1];

        String[] values = parts[2].split(",");

        for (String value : values) {
            int x = Integer.parseInt(value);
            a[x] = 1;
        }

        StringBuilder result = new StringBuilder();

        result.append(requestId).append(";");

        boolean first = true;

        for (int i = 1; i <= n; i++) {
            if (a[i] == 0) {

                if (!first) {
                    result.append(",");
                }

                result.append(i);
                first = false;
            }
        }

        System.out.println("Client gửi: " + result);

        byte[] resultData = result.toString().getBytes();

        DatagramPacket packet3 =
                new DatagramPacket(
                        resultData,
                        resultData.length,
                        address,
                        PORT
                );

        socket.send(packet3);

        socket.close();
    }
}