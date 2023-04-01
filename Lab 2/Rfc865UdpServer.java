import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Rfc865UdpServer {

    /*
     * A server listens for UDP datagrams on UDP port
     * 17. When a datagram is received, an answering datagram is sent
     * containing a quote (the data in the received datagram is ignored).
     */
    static int PORT = 17;
    static String QUOTE = "May the world be more sincere -- CH";

    public static void main(String[] argv) {

        /*
         * 1. Open UDP socket at well-known port
         */
        DatagramSocket socket = null;
        try {
            // creates a UDP server listening on port 17
            socket = new DatagramSocket(PORT);
            System.out.println("UDP Server listening on port " + PORT);
        } catch (SocketException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            while (true) {
                /*
                 * 2. Listen for UDP request from client, wait for the client's request
                 */
                byte[] buffer = new byte[512];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                System.out.println("Waiting for request...");
                socket.receive(request);

                /*
                 * Process the request
                 * The receive() method blocks until a datagram is received.
                 * Sends a DatagramPacket to the client
                 */
                String requestContent = new String(buffer);
                System.out.println("Received request: " + requestContent);

                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();
                System.out.println("From client: " + clientAddress.getCanonicalHostName());

                /*
                 * 3. Send UDP reply to client
                 */
                String replyContent = QUOTE;
                byte[] replyBuf = replyContent.getBytes("UTF-8");
                System.out.println("Reply content: " + replyContent);

                DatagramPacket reply = new DatagramPacket(replyBuf, replyBuf.length, clientAddress, clientPort);
                System.out.println("Sending reply...");
                socket.send(reply);
                System.out.println("Reply sent");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }

    }
}