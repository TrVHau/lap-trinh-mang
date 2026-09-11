import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class UDP_DATA2 {
    public static void main(String[] args) throws Exception {
        String url = "36.50.135.242";
        int PORT = 2207;

        DatagramSocket socket = new DatagramSocket();

        byte[] data = ";B23DCCN287;Bo8sSAMb".getBytes();

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
        String[] stringArr = parts[1].split(",");

        int[] a = new int[50];

        for(int i=0;i<50;i++){
            a[i]=Integer.parseInt(stringArr[i]);
        }

        StringBuilder result = new StringBuilder();

        result.append(requestId).append(";");

        // max
        result.append(Arrays.stream(a).max().getAsInt()).append(',');
        result.append(Arrays.stream(a).min().getAsInt());

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