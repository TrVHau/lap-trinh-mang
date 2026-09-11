import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_STRING1 {
    public static void main(String[] args) throws Exception {
        String url = "36.50.135.242";
        int PORT = 2208;

        DatagramSocket socket = new DatagramSocket();

        byte[] data = ";B23DCCN287;TWiEfmqA".getBytes();
        InetAddress address = InetAddress.getByName(url);
        DatagramPacket packet = new DatagramPacket(data, data.length, address, PORT);
        socket.send(packet);

        byte[] buffer = new byte[1024];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        socket.receive(response);
        String responseData = new String(response.getData(), 0, response.getLength());
        System.out.println("Response: " + responseData);

        String[] parts=responseData.split(";");
        StringBuilder sb = new StringBuilder();
        sb.append(parts[0]).append(";");

        int[] cnt = new int[256];
        for(char c : parts[1].toCharArray()) {
            cnt[c]++;
        }
        int max = 0;
        char ans='0';

        for(char c : parts[1].toCharArray()) {
            if(cnt[c] > max) {
                max = cnt[c];
            }
        }

        for(char c : parts[1].toCharArray()) {
            if(cnt[c] == max) {
                sb.append(c);
                ans = c;
                break;
            }
        }
        sb.append(":");
        for(int i=0; i<parts[1].length(); i++) {
            if(parts[1].charAt(i) == ans) {
                sb.append(i+1).append(",");
            }
        }
        System.out.println("Sending: " + sb.toString());

        data = sb.toString().getBytes();
        packet = new DatagramPacket(data, data.length, address, PORT);
        socket.send(packet);

        socket.close();
    }
}