import UDP.Customer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDP_OBJECT2 {

    // nguyen van hai duong
    // -> DUONG, Nguyen Van Hai
    private static String normalizeName(String name) {
        String[] words = name.trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        result.append(words[words.length - 1].toUpperCase());
        result.append(", ");
        for (int i = 0; i < words.length - 1; i++) {
            String word = words[i].toLowerCase();

            result.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1));

            if (i < words.length - 2) {
                result.append(" ");
            }
        }
        return result.toString();
    }

    // 10-11-2012 -> 11/10/2012
    private static String convertDate(String date) {
        String[] parts = date.split("-");

        String mm = parts[0];
        String dd = parts[1];
        String yyyy = parts[2];

        return dd + "/" + mm + "/" + yyyy;
    }

    // nguyen van hai duong
    // -> nvhduong
    private static String createUsername(String name) {
        String[] words = name.trim().toLowerCase().split("\\s+");
        StringBuilder username = new StringBuilder();
        for (int i = 0; i < words.length - 1; i++) {
            username.append(words[i].charAt(0));
        }
        username.append(words[words.length - 1]);
        return username.toString();
    }

    public static void main(String[] args) throws Exception {
        String server = "36.50.135.242";
        int port = 2209;

        InetAddress address = InetAddress.getByName(server);
        DatagramSocket socket = new DatagramSocket();

        String message = ";B23DCCN287;67ddNiNr";
        byte[] sendData =
                message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket sendPacket = new DatagramPacket(
                sendData,
                sendData.length,
                address,
                port
        );

        socket.send(sendPacket);

        byte[] buffer = new byte[4096];
        DatagramPacket receivePacket =
                new DatagramPacket(buffer, buffer.length);
        socket.receive(receivePacket);
        int length = receivePacket.getLength();

        String requestId = new String(
                receivePacket.getData(),
                0,
                8,
                StandardCharsets.UTF_8
        );

        System.out.println("requestId: " + requestId);

        ByteArrayInputStream bais =
                new ByteArrayInputStream(
                        receivePacket.getData(),
                        8,
                        length - 8
                );

        ObjectInputStream ois =
                new ObjectInputStream(bais);

        Customer customer =
                (Customer) ois.readObject();

        System.out.println("Customer: "+customer);
        String oldName = customer.getName();
        String newName = normalizeName(oldName);

        String newDate =
                convertDate(customer.getDayOfBirth());

        String userName =
                createUsername(oldName);

        customer.setName(newName);
        customer.setDayOfBirth(newDate);
        customer.setUserName(userName);

        System.out.println("Customer: "+customer);

        ByteArrayOutputStream baos =
                new ByteArrayOutputStream();

        ObjectOutputStream oos =
                new ObjectOutputStream(baos);

        oos.writeObject(customer);
        oos.flush();

        byte[] customerData =
                baos.toByteArray();

        byte[] requestIdData =
                requestId.getBytes(StandardCharsets.UTF_8);

        byte[] finalData =
                new byte[8 + customerData.length];

        System.arraycopy(
                requestIdData,
                0,
                finalData,
                0,
                8
        );

        System.arraycopy(
                customerData,
                0,
                finalData,
                8,
                customerData.length
        );

        DatagramPacket finalPacket =
                new DatagramPacket(
                        finalData,
                        finalData.length,
                        address,
                        port
                );

        socket.send(finalPacket);
        socket.close();
        System.out.println("Done!");
    }
}