import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.IOException;



public class ServerUDP {


    public static void main(String[] args) throws IOException {

        int port = Integer.parseInt(args[0]);
        DatagramSocket socket = new DatagramSocket(port);


        byte[] buffer = new byte[1024];
        boolean gotFirstMessage = false;
        Map<Integer, String> messages = new TreeMap<>();


        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);


            byte[] data = packet.getData();
            int sequenceNumber = data[0];
            int messageLength = data[1];
            String message = new String(data, 2, messageLength, StandardCharsets.UTF_8);


            if (message.equals("WELCOME") && !gotFirstMessage) {
                gotFirstMessage = true;
                sendMessage(socket, packet.getAddress(), packet.getPort(), (byte) 0, "WELCOME");
            }


            if (gotFirstMessage && (sequenceNumber == 6 || sequenceNumber == 9 || sequenceNumber == 14)) {
                messages.put((int) sequenceNumber, message);
            }



            if (messages.size() == 3) {
                StringBuilder combinedMessage = new StringBuilder();
                messages.values().forEach(combinedMessage::append);



                sendMessage(socket, packet.getAddress(), packet.getPort(), (byte) 15, combinedMessage.toString());
                break;
            }
        }
        socket.close();
    }

    public static void sendMessage(DatagramSocket socket, InetAddress address, int port, byte sequenceNumber, String message) throws IOException {
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] data = new byte[2 + messageBytes.length];
        data[0] = sequenceNumber;
        data[1] = (byte) messageBytes.length;
        System.arraycopy(messageBytes, 0, data, 2, messageBytes.length);

        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        socket.send(packet);
    }
}