package GraphicsZ;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

abstract class VisualJ extends JFrame {
    public static Obj[] shapes=new Obj[100];
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
                //System.out.println(shapes[i].xxcoord);
                int yp=shapes[i].ycoord+shapes[i].xycoord;
                double radians=(Math.toRadians(shapes[i].degree));
                int rotX=xp+shapes[i].width/2;
                int rotY=yp+shapes[i].length/2;
                int xtra=-shapes[i].width/2;
                int ytra=-shapes[i].length/2;


                g2dBuffer.setColor(shapes[i].col);

                
                g2dBuffer.translate(rotX,rotY);
                
                g2dBuffer.rotate(radians);
                if (shapes[i].fill){
                    if (shapes[i].type.equals("Square")||shapes[i].type.equals("Rectangle")||shapes[i].type.equals("Polygon")||shapes[i].type.equals("Line")){
                    Object[] poly=shapes[i].objectInformation();
                    int[] arrintx=(int[])poly[0];
                    int[] arrinty=(int[])poly[1];
                    int[] z=new int[arrintx.length];
                    int[] a=new int[arrinty.length];
                    for (int y=0;y<arrintx.length;y++){
                        z[y]=-shapes[i].width/2;
                        a[y]=-shapes[i].length/2;
                    }
                    int[] b=ArrMath.addArrs(arrintx,z);
                    int[] c=ArrMath.addArrs(arrinty,a);
                    g2dBuffer.fillPolygon(b,c,arrintx.length);
                    z=ArrMath.minusArrs(arrintx,z);
                    a=ArrMath.minusArrs(arrinty,a);

                }else if(shapes[i].type.equals("Circle")){
                        g2dBuffer.fillOval(xtra,ytra, shapes[i].width, shapes[i].length);
                    }else if(shapes[i].type.equals("Oval")){
                        g2dBuffer.fillOval(xtra,ytra, shapes[i].width, shapes[i].length);
                    }
                }else if(!shapes[i].fill){
                    if(shapes[i].type.equals("Circle")){
                        g2dBuffer.drawOval(xtra,ytra, shapes[i].width, shapes[i].length);
                    }else if(shapes[i].type.equals("Oval")){
                        g2dBuffer.drawOval(xtra,ytra, shapes[i].width, shapes[i].length);
                    }else if(shapes[i].type.equals("Text")){
                        
                        Object[] stuff=shapes[i].objectInformation();
                        
                                  
                        g2dBuffer.rotate(-radians);
                        g2dBuffer.translate(-rotX,-rotY);
                        g2dBuffer.translate(rotX,yp+shapes[i].length/2);
                        g2dBuffer.rotate(radians);
                        

                        g2dBuffer.drawString(stuff[0].toString(),0,0);
                        
                        
                        g2dBuffer.rotate(-radians);
                        g2dBuffer.translate(-rotX,-yp-shapes[i].length/2);
                        g2dBuffer.translate(rotX,rotY);
                        g2dBuffer.rotate(radians);
                    }else if (shapes[i].type.equals("Square")||shapes[i].type.equals("Rectangle")||shapes[i].type.equals("Polygon")||shapes[i].type.equals("Line")){
                        Object[] poly=shapes[i].objectInformation();
                        int[] arrintx=(int[])poly[0];
                        int[] arrinty=(int[])poly[1];
                        int[] z=new int[arrintx.length];
                        int[] a=new int[arrinty.length];
                        for (int y=0;y<arrintx.length;y++){
                            z[y]=-shapes[i].width/2;
                            a[y]=-shapes[i].length/2;
                        }
                        int[] b=ArrMath.addArrs(arrintx,z);
                        int[] c=ArrMath.addArrs(arrinty,a);
                        g2dBuffer.drawPolygon(b,c,arrintx.length);
                        z=ArrMath.minusArrs(arrintx,z);
                        a=ArrMath.minusArrs(arrinty,a);
                    }
                }
                g2dBuffer.rotate(-radians);
                g2dBuffer.translate(-rotX,-rotY);
            }
        }
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
    public static void run() {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }

    
    public void createWorld(String title,int width, int height,Color color){
        setTitle(title);
        setSize(width, height);
        setBackground(color);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }
    public static void setArrSize(int x){
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
    public static Obj[] getObjArray(){
        return shapes;
    }
    
}