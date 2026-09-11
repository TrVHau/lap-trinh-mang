import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TCP_NIO2 {

    private static void writeFully(SocketChannel channel, ByteBuffer buffer) throws Exception{
        while(buffer.hasRemaining())
            channel.write(buffer);
    }
    private static void readFully(SocketChannel channel,ByteBuffer buffer) throws Exception {
        while(buffer.hasRemaining()){
            int n = channel.read(buffer);
            if(n==-1) throw new Exception("Server close");
        }
    }

    private static void sendFrame(SocketChannel channel,String msg) throws Exception{
        byte[] data = msg.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(4+data.length);
        buffer.putInt(data.length);
        buffer.put(data);
        buffer.flip();
        writeFully(channel,buffer);
    }

    private static String readFrame(SocketChannel channel) throws Exception{
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        readFully(channel,lengthBuffer);
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();
        ByteBuffer buffer = ByteBuffer.allocate(length);
        readFully(channel,buffer);
        buffer.flip();
        return StandardCharsets.UTF_8.decode(buffer).toString();
    }

    private static String getValue(String json, String key) {
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern) + pattern.length();

        while (json.charAt(start) == ' ') {
            start++;
        }

        if (json.charAt(start) == '"') {
            start++;
            int end = json.indexOf('"', start);
            return json.substring(start, end);
        }

        int end = json.indexOf(',', start);

        if (end == -1) {
            end = json.indexOf('}', start);
        }

        return json.substring(start, end).trim();
    }

    public static void main(String[] args) throws Exception{
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("36.50.135.242",2211));

        sendFrame(channel,"B23DCCN287;zERNLf0G");

        String frame1 = readFrame(channel);
        String frame2 = readFrame(channel);

        String json = frame1+frame2;
        System.out.println("Server: "+json);

        String event = getValue(json,"event");
        String user = getValue(json,"user");
        boolean ok=Boolean.parseBoolean(getValue(json,"ok"));

        String result = "event="+event+";user="+user+";ok="+(ok?"1":0);
        System.out.println("Send: "+result);
        sendFrame(channel,result);

        channel.close();
    }
}
