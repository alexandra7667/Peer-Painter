/**
 * This class is responsible for sending points painted by this user to the connected user.
 * The connected user's InetAddress is 'hostAddress' and their port is 'hostPort'.
 *
 * @author alexandraharnstrom
 * @version 1
 */

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PointSender implements Runnable{

    private int myPort;
    private InetAddress hostAddress;
    private int hostPort;
    private Draw draw;
    private DatagramSocket datagramSocket;

    public PointSender(int myPort, InetAddress hostAddress, int hostPort, DatagramSocket datagramSocket, Draw draw) {
        this.myPort = myPort;
        this.hostAddress = hostAddress;
        this.hostPort = hostPort;
        this.datagramSocket = datagramSocket;
        this.draw = draw;
    }

    /**
     * This method continuously checks if this user has drawn a new point, and if so,
     * sends the point to the connected user packaged as a byte array.
     */
    @Override
    public void run() {
        while(true) {
            try {
                if(draw.paper.hasNewPoint()) {
                    Point point = draw.paper.getPointToSend();

                    String stringPoint = point.x + " " + point.y;

                    byte[] byteArrayPoint = stringPoint.getBytes();

                    DatagramPacket datagramPacket = new DatagramPacket(byteArrayPoint, byteArrayPoint.length, hostAddress, hostPort);

                    datagramSocket.send(datagramPacket);
                }
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
