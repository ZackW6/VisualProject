package Canvas.Shapes;
import java.awt.*;

public class Oval extends Obj{
    public Oval(int X,int Y,int Width,int Length, Color Color,boolean tf){   
        super(X,Y,Width,Length,Color,tf);
    }
    /**
     * Set width of the oval
     * @param newSize
     */
    public void setWidth(int newSize){
        this.width=newSize;
    }
    /**
     * Set length of the oval
     * @param newSize
     */
    public void setLength(int newSize){
        this.length=newSize;
    }
    /**
     * The oval application of the show method, draws an oval based on instance data
     */
    @Override
    public void show(Graphics2D g2dBuffer){
        if (fill){
            g2dBuffer.fillOval((int)-width/2, (int)-length/2, (int)width, (int)length);
        }else{
            g2dBuffer.drawOval((int)-width/2, (int)-length/2, (int)width, (int)length);
        }
    }
}