/**
 * This class draws a GUI canvas on the screen.
 *
 * (This class was pre-written as a template)
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Draw extends JFrame {
    protected Paper paper = new Paper();

    public static void main(String[] args) {
        new Draw();
    }

    public Draw() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(paper, BorderLayout.CENTER);

        setSize(640, 480);
        setVisible(true);
    }
}

/**
 * This class draws points on the canvas.
 *
 * (This class was pre-written as a template)
 */
class Paper extends JPanel {
    private HashSet hs = new HashSet();

    private Point pointToSend;
    private boolean newPoint;

    public Paper() {
        setBackground(Color.white);
        addMouseListener(new L1());
        addMouseMotionListener(new L2());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        Iterator i = hs.iterator();
        while(i.hasNext()) {
            Point p = (Point)i.next();
            g.fillOval(p.x, p.y, 2, 2);
        }
    }

    /**
     * This method adds a new point to this user's canvas.
     * If the user themselves painted the point, the boolean 'newPoint' is set to true
     * (this point will be sent to the connected user).
     * Else if the point was received from the connected user, 'newPoint' is false.
     *
     * @param point - The point to be added to the set and drawn to canvas.
     * @param newPoint - A boolean indicating whether this point was drawn by the user or the connected user.
     */
    synchronized void addPoint(Point point, boolean newPoint) {
        hs.add(point);

        this.newPoint = newPoint;

        if(newPoint) {
            pointToSend = point;
        }
        repaint();
    }

    /**
     * This method checks if a new point has been drawn.
     * @return - True or False if a new point has been drawn.
     */
    public boolean hasNewPoint() {
        return newPoint;
    }

    /**
     * This method re-sets the boolean newPoint and returns the newly drawn point.
     * This makes sure each new point is only sent once.
     *
     * @return - The newly drawn point
     */
    public Point getPointToSend() {
        newPoint = false;
        return pointToSend;
    }

    class L1 extends MouseAdapter {
        public void mousePressed(MouseEvent me) {
            addPoint(me.getPoint(), true);
        }
    }

    class L2 extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent me) {
            addPoint(me.getPoint(), true);
        }
    }
}