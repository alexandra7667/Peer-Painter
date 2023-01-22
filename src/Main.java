/**
 * This class starts the program by creating a DataSocket connection from this user's port.
 * It creates a new thread: PointSender, which sends points painted by this user to the connected user.
 * It creates a new thread: PointReceiver, which receives points painted by the other user to this user.
 * It also instantiates an object 'draw' of the logic class Draw.
 *
 * @author alexandraharnstrom
 * @version 1
 */

import java.net.*;

public class Main {

    public static void main(String[] args){
        int myPort = 0;
        InetAddress hostAddress = null;
        int hostPort = 0;

        try {
            myPort = Integer.parseInt(args[0]);
            hostAddress = InetAddress.getByName(args[1]);
            hostPort = Integer.parseInt(args[2]);
        }
        catch(Exception e) {
            System.out.println("Wrong number/format of args. Must be: (int) your port (String) remote host address (int) remote host port");
            System.exit(-1);
        }

        DatagramSocket datagramSocket = null;

        try {
            datagramSocket = new DatagramSocket(myPort);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Draw draw = new Draw();

        PointSender pointSender = new PointSender(myPort, hostAddress, hostPort, datagramSocket, draw);
        Thread senderThread = new Thread(pointSender);
        senderThread.start();

        PointReceiver pointReceiver = new PointReceiver(datagramSocket, draw);
        Thread receiverThread = new Thread(pointReceiver);
        receiverThread.start();
    }
}

