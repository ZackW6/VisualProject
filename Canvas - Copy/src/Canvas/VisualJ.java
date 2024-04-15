package Canvas;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class VisualJ extends JFrame{
    public Obj[] shapes=new Obj[100];
    //shapes encompasses everything that will be included in the graphics project
    public int WIDTH = 20000;
    public int HEIGHT = 10000;

    private BufferedImage buffer;


    private double timeStep;
    private UserInput userInput;
    @Override
    public void paint(Graphics g) {
        Profile.timeStart();
        if (buffer == null || buffer.getWidth() != getWidth() || buffer.getHeight() != getHeight()) {
            // Create a new buffer if not initialized or if the size has changed
            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }
        // Get the graphics context of the buffer
        Graphics2D g2dBuffer = buffer.createGraphics();
        g2dBuffer.setColor(getBackground());
        g2dBuffer.fillRect(0, 0, WIDTH, HEIGHT);
        for (int i = 0; i < shapes.length; i++) {
            if (shapes[i]!=null){
                int xp=shapes[i].xcoord+shapes[i].xxcoord;
                int yp=shapes[i].ycoord+shapes[i].xycoord;
                double radians=(Math.toRadians(shapes[i].degree));
                int rotX=xp+shapes[i].width/2;
                int rotY=yp+shapes[i].length/2;
                g2dBuffer.setColor(shapes[i].col);
                g2dBuffer.translate(rotX,rotY);
                g2dBuffer.rotate(radians);
                shapes[i].show(g2dBuffer);
                g2dBuffer.rotate(-radians);
                g2dBuffer.translate(-rotX,-rotY);
            }
        }
        double time=Profile.getTime();
        g2dBuffer.drawString(time+"",100,100);
        g.drawImage(buffer, 0, 0, this);
        System.out.println(Profile.getTime());
        timeStep+=Profile.getTime()/1000;
        Profile.timeEnd();
    }
    public void createWorld(String title,int width, int height,Color color){
        setTitle(title);
        setSize(width, height);
        setBackground(color);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }
    public void setArrSize(int x){
        Obj[] arr=new Obj[x];
        int max;
        if (shapes.length<x){
            max=shapes.length;
        }else{
            max=x;
        }
        for (int i=0;i<max;i++){
            arr[i]=shapes[i];
        }
        shapes=arr;
    }
    /**
     * Get the array of objects currently being presented
     * @return
     */
    public Obj[] getObjArray(){
        return shapes;
    }
    /**
     * Remove an object from the current array of objects
     * @param object
     */
    public void remove(Obj object){
        for (int i=0;i<shapes.length;i++){
        if (shapes[i].equals(object)){
          shapes[i]=null;
          i=shapes.length;
        }
      }
    }
    /**
     * Add an object to the current array of objects
     * @param object
     */
    public void add(Obj object){
      for (int i=0;i<shapes.length;i++){
        if (shapes[i]==null){
          shapes[i]=object;
          i=shapes.length;
        }
      }
    }
    /**
     * Start the graphics thread, which runs as fast as possible
     */
    public void startThread() {
        Thread animationThread = new Thread(() -> {
            while (true) {
                repaint(); // Request a repaint of the component
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
        setArrSize(10000);
        setTitle(title);
        setSize(width, height);
        setBackground(background);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        
    }

    public double getTimeStep(){
        return timeStep;
    }
}