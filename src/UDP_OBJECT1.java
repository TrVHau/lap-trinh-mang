import UDP.Student;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDP_OBJECT1 {

    private static String normalizeName(String name) {
        String[] words = name.trim().toLowerCase().split("\\s+");

        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }

        return sb.toString().trim();
    }

    // Nguyen Van Tuan Nam
    // -> namnvt@ptit.edu.vn
    private static String createEmail(String name) {
        String[] words = name.trim().toLowerCase().split("\\s+");

        StringBuilder sb = new StringBuilder();
        sb.append(words[words.length - 1]);
        for (int i = 0; i < words.length - 1; i++) {
            sb.append(words[i].charAt(0));
        }
        sb.append("@ptit.edu.vn");
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String url = "36.50.135.242";
        int PORT = 2209;
        InetAddress address = InetAddress.getByName(url);
        DatagramSocket socket = new DatagramSocket();

        byte[] qCode = ";B23DCCN287;FFM5Ql3K".getBytes(StandardCharsets.UTF_8);
        DatagramPacket requestPacket = new DatagramPacket(
                qCode,
                qCode.length,
                address,
                PORT);
        socket.send(requestPacket);

        byte[] buffer = new byte[1024];
        DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(responsePacket);
        byte[] data = responsePacket.getData();
        String requestId = new String(data,0,8,StandardCharsets.UTF_8);
        ByteArrayInputStream bais= new ByteArrayInputStream(
                data,
                8,
                data.length-8
        );

        ObjectInputStream ois  = new ObjectInputStream(bais);
        Student student = (Student) ois.readObject();

        System.out.println(student);

        String normalizeName = normalizeName(student.getName());
        String email = createEmail(normalizeName);

        student.setName(normalizeName);
        student.setEmail(email);

        System.out.println(student);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(student);
        oos.flush();

        byte[] studentData = baos.toByteArray();

        byte[] requestIdData = requestId.getBytes(StandardCharsets.UTF_8);
        byte[] finalData = new byte[8+studentData.length];

        System.arraycopy(requestIdData,0,finalData,0,8);
        System.arraycopy(studentData,0,finalData,8,studentData.length);

        DatagramPacket finalPacket = new DatagramPacket(
                finalData,
                finalData.length,
                address,
                PORT
        );

        socket.send(finalPacket);

        socket.close();
        System.out.println("OK");

    }
}