/**
 * This class is responsible for receiving points from the connected user.
 * It continuously checks whether a point has been sent, and if so,
 * the point is added to the nested class Paper's set of points.
 *
 * @author alexandraharnstrom
 * @version 1
 */

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class PointReceiver implements Runnable{

    private DatagramSocket datagramSocket;
    private Draw draw;

    public PointReceiver(DatagramSocket datagramSocket, Draw draw) {
        this.datagramSocket = datagramSocket;
        this.draw = draw;
    }

    /**
     * This method continuously listens for new points sent by the connected user.
     * If a point is received, it is transferred to Paper's set of points.
     */
    @Override
    public void run() {
        while(true) {
            byte[] byteArrayPoint = new byte[8192];

            DatagramPacket datagramPacket = new DatagramPacket(byteArrayPoint, byteArrayPoint.length);

            try {
                datagramSocket.receive(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String data = new String(datagramPacket.getData(), 0, datagramPacket.getLength());

            String[] xy = data.split(" ");

            int x = Integer.parseInt(xy[0]);
            int y = Integer.valueOf(xy[1]);

            Point pointReceived = new Point(x, y);

            draw.paper.addPoint(pointReceived, false);
        }
    }
}
