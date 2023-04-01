import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Rfc865UdpClient {

    static int PORT = 17;
    static String SERVER_NAME = "CiHui_0409";

    public static void main(String[] argv) {
        /*
         * 1. Open UDP socket
         * sends a DatagramPacket to a server specified by hostname and port
         */
        DatagramSocket socket = null;
        try {
            InetAddress serverAddress = InetAddress.getByName(SERVER_NAME);
            socket = new DatagramSocket();
            socket.connect(serverAddress, PORT);
            System.out.println(
                    "UDP Client connected on port " + PORT + " to server: " + serverAddress.getCanonicalHostName());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            /*
             * Prepare request content
             */
            String content = "Lee Ci Hui, A58, " + InetAddress.getLocalHost().getHostAddress();
            byte[] buffer = content.getBytes("UTF-8");
            System.out.println("Content to send: " + content);

            /*
             * 2. Send UDP request to server
             */
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            System.out.println("Sending request...");
            socket.send(request);
            System.out.println("Request sent to server");

            /*
             * 3. Receive UDP reply from server
             */
            byte[] replyBuf = new byte[512];
            DatagramPacket reply = new DatagramPacket(replyBuf, replyBuf.length);
            System.out.println("Waiting for reply...");
            socket.receive(reply);

            /*
             * Process the reply
             */
            String replyContent = new String(replyBuf);
            System.out.println("Received reply: " + replyContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}
