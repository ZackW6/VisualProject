import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

class VisualJ extends JFrame{
    public Obj[] shapes=new Obj[100];
    //shapes encompasses everything that will be included in the graphics project
    int WIDTH = 20000;
    int HEIGHT = 10000;

    private BufferedImage buffer;


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
        Profile.timeEnd();
        
        //System.out.println(Profile.getTime());
    }
    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VisualJ().setVisible(true);
            }
        });
    }*/
    /*public static void innit() {
        SwingUtilities.invokeLater(() -> new VisualJava().setVisible(true));
    }*/

    
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
    public Obj[] getObjArray(){
        return shapes;
    }

    public void remove(Obj object){
        for (int i=0;i<shapes.length;i++){
        if (shapes[i].equals(object)){
          shapes[i]=null;
          i=shapes.length;
        }
      }
    }
    public void add(Obj object){
      for (int i=0;i<shapes.length;i++){
        if (shapes[i]==null){
          shapes[i]=object;
          i=shapes.length;
        }
      }
    }
    public void startGraphics() {
        Thread animationThread = new Thread(() -> {
            while (true) {
                repaint(); // Request a repaint of the component
            }
        });
        animationThread.start(); // Start the animation thread
    }




    public VisualJ(String title,int width, int height, Color background){
        WIDTH=width;
        HEIGHT=height;
        SwingUtilities.invokeLater(() -> setVisible(true));
        setArrSize(10001);
        Timer timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runAll();
            }
        });
        timer.start();


        // Perform the operation you want to measure




        // Stop the timer
        setTitle(title);
        setSize(width, height);
        setBackground(background);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        for (int i=0;i<shapes.length-1;i++){
            int rand1=Random.randInt(0, WIDTH);
            int radn2=Random.randInt(0,HEIGHT);
            int dist=(int)((Math.sqrt((Math.pow(WIDTH/2-rand1,2))+(Math.pow(HEIGHT/2-radn2,2))))/50);
            shapes[i]=new Square(rand1,radn2,1+dist,ColorEXT.getRandomColor(),true);
            //shapes[i]=new Circle(rand1,radn2,1+dist,ColorEXT.getRandomColor(),true);
            //shapes[i]=new Polygon(rand1,radn2,new int[]{0,2+dist,(int)(1+dist/2)},new int[]{0,0,3+dist},ColorEXT.getRandomColor(), false);
            //shapes[i]=new Line(rand1,radn2,new int[]{0,2+dist},new int[]{0,3+dist},ColorEXT.getColorBasedXY(rand1,radn2,WIDTH,HEIGHT), 1+dist);
            //shapes[i]=new Text(rand1,radn2,1+dist*10,ColorEXT.getRandomColor(),"HI");//TEXT BROKEN
            //shapes[i].customRotationCenter(WIDTH/2,HEIGHT/2);
        }
        // shapes[9999]=new Circle(100,100,100,ColorEXT.getRandomColor(),true);
        // Text txt=new Text(100, 100, 200, Color.blue, 20.0+"");
        // shapes[10000]=txt;


    }
    private void runAll(){
        for (int i=0;i<shapes.length;i++){
            if (shapes[i]!=null){
                int rand=Random.randInt(0, 5);
                shapes[i].rotate(shapes[i].degrees2+rand);
                shapes[i].rotPoint(WIDTH/2,HEIGHT/2,shapes[i].degrees2+rand);
               
                //shapes[i].setPosition(Random.randInt(0,WIDTH),Random.randInt(0,HEIGHT));
                //shapes[i].setColor(ColorEXT.getColorBasedXY(shapes[i].xcoord, shapes[i].ycoord, WIDTH, HEIGHT));
               
            }
        }
        //repaint(0,0,WIDTH,HEIGHT);
    }
}