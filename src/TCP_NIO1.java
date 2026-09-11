import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TCP_NIO1 {

    static void writeFull(SocketChannel channel,ByteBuffer buffer) throws Exception{
        while(buffer.hasRemaining())
            channel.write(buffer);
    }

    static void readFull(SocketChannel channel, ByteBuffer buffer) throws Exception{
        while(buffer.hasRemaining()){
            int n = channel.read(buffer);

            if(n==-1) throw new Exception("Server close");
        }
    }

    static void sendFrame(SocketChannel channel,String msg)throws Exception{
        byte[] data = msg.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(4+data.length);

        buffer.putInt(data.length);
        buffer.put(data);
        buffer.flip();
        writeFull(channel,buffer);
    }
    static String readFrame(SocketChannel channel)throws Exception{
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        readFull(channel,lengthBuffer);
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();
        ByteBuffer payloadBuffer = ByteBuffer.allocate(length);

        readFull(channel,payloadBuffer);
        payloadBuffer.flip();
        return StandardCharsets.UTF_8.decode(payloadBuffer).toString();
    }
    public static void main(String[] args) throws Exception{
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("36.50.135.242",2211));

        sendFrame(channel,"B23DCCN287;3gJTx2gl");
        String frame1 = readFrame(channel);
        String frame2 = readFrame(channel);
        String frame3 = readFrame(channel);

        String httpRequest = frame1+frame2+frame3;
        System.out.println("HTTP REQ: "+httpRequest);

        String[] lines = httpRequest.split("\\r\\n");
        String[] requestLine = lines[0].split(" ");

        String method = requestLine[0];
        String path = requestLine[1];

        String host = "";
        for(String line:lines){
            if(line.regionMatches(true,0,"Host:",0,5)){
                host = line.substring(5).trim();
                break;
            }
        }
        String result = method +";"+path+";"+host;
        System.out.println("Result: "+result);
        sendFrame(channel,result);

        channel.close();
    }
}
