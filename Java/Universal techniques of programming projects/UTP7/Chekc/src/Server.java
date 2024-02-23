import java.util.*;
import java.net.*;
import java.io.*;


public class Server {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);

        byte[] data = new byte[1024];

        InputStream is = new DataInputStream(System.in);

        is.read(data, 0, 1024);

        List<byte[]> packets = new ArrayList<byte[]>();

        for (int i = 0; i < 103; i++) {
            byte[] packet = new byte[12];
            packet[0] = (byte)i;
            if (i == 102) {
                packet[1] = (byte)4;
                for (int j = 0; j < 4; j++) {
                    packet[2 + j] = data[i * 10 + j];
                }
            } else {
                packet[1] = (byte)10;
                for (int j = 0; j < 10; j++) {
                    packet[2 + j] = data[i * 10 + j];
                }
            }
            packets.add(packet);
        }

        DatagramSocket socket = new DatagramSocket(port);
        byte[] received = new byte[16];
        DatagramPacket rec = new DatagramPacket(received, received.length);
        socket.receive(rec);
        InetAddress sender = rec.getAddress();
        int cport = rec.getPort();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < packets.size(); j++) {
                DatagramPacket to_send = new DatagramPacket(packets.get(j), packets.get(j).length, sender, cport);
                socket.send(to_send);
            }
        }

        socket.close();
    }
}