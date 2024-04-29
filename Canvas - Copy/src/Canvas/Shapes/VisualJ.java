package Canvas.Shapes;
import javax.swing.*;

import Canvas.Inputs.UserInput;
import Canvas.Util.Profile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class VisualJ extends JFrame{

    private Profile profile = new Profile();
    public ArrayList<Obj> shapes=new ArrayList<Obj>();
    //shapes encompasses everything that will be included in the graphics project
    public int WIDTH;
    public int HEIGHT;

    private BufferedImage buffer;

    private int moveX = 0;
    private int moveY = 0;
    private double timeStep;
    @Override
    public void paint(Graphics g) {
        
        profile.timeStart();
        if (buffer == null || buffer.getWidth() != getWidth() || buffer.getHeight() != getHeight()) {
            // Create a new buffer if not initialized or if the size has changed
            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }
        // Get the graphics context of the buffer
        Graphics2D g2dBuffer = buffer.createGraphics();
        g2dBuffer.translate(moveX,moveY);
        g2dBuffer.setColor(getBackground());
        g2dBuffer.fillRect(0, 0, WIDTH, HEIGHT);
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i)!=null){
                double xp=shapes.get(i).coords.x+shapes.get(i).xxcoord;
                double yp=shapes.get(i).coords.y+shapes.get(i).xycoord;
                double radians=(Math.toRadians(shapes.get(i).degree));
                double rotX=xp+shapes.get(i).width/2;
                double rotY=yp+shapes.get(i).length/2;
                g2dBuffer.setColor(shapes.get(i).col);
                g2dBuffer.translate(rotX,rotY);
                g2dBuffer.rotate(radians);
                shapes.get(i).show(g2dBuffer);
                g2dBuffer.rotate(-radians);
                g2dBuffer.translate(-rotX,-rotY);
            }
        }
        double time=profile.getTime();
        g2dBuffer.setColor(Color.RED);
        g2dBuffer.drawString(time+" "+shapes.size(),100,100);
        g.drawImage(buffer, 0, 0, this);
        profile.timeEnd();
        timeStep++;
    }
    public void createWorld(String title,int width, int height,Color color){
        setTitle(title);
        setSize(width, height);
        setBackground(color);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }
    /**
     * Get the array of objects currently being presented
     * @return
     */
    public ArrayList<Obj> getObjArray(){
        return shapes;
    }
    /**
     * Remove an object from the current array of objects
     * @param object
     */
    public void remove(Obj object){
        shapes.remove(object);
    }
    /**
     * Add an object to the current array of objects
     * @param object
     */
    public void add(Obj object){
        shapes.add(object);
    }
    /**
     * Start the graphics thread, which runs as fast as possible
     */
    public void startThread() {
        Thread animationThread = new Thread(() -> {
            while (true) {
                repaint(); // Request a repaint of the component
                try {
                    Thread.sleep((long) 0);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        animationThread.start(); // Start the animation thread
    }


    public int getBackgroundHeight(){
        return HEIGHT;
    }
    public int getBackgroundWidth(){
        return WIDTH;
    }

    public VisualJ(String title,int width, int height, Color background){
        WIDTH=width;
        HEIGHT=height; 
        SwingUtilities.invokeLater(() -> setVisible(true));
        setTitle(title);
        setSize(width, height);
        setBackground(background);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }

    public double getTimeStep(){
        return timeStep;
    }

    public void moveFrame(int x, int y){
        moveX += x;
        moveY += y;
    }
    public void setFrame(int x, int y){
        moveX = x;
        moveY = y;
    }
    public int[] getFrameMove(){
        return new int[]{moveX,moveY};
    }
}