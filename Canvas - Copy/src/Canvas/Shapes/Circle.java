package Canvas.Shapes;
import java.awt.*;

public class Circle extends Obj{ 
    public Circle(int X,int Y,double Diameter, Color Color,boolean tf){   
        super(X,Y,Diameter,Diameter,Color,tf);
        this.width=Diameter;
    }
    /**
     * Set the size of the circle, reset diameter
     */
    public void setSize(int newSize){
        this.width=newSize;
        this.length=newSize;
    }
    
    /**
     * The circle application of the show method, draws a circle based on instance data
     */
    @Override
    public void show(Graphics2D g2dBuffer){
        if (fill){
            g2dBuffer.fillOval((int)-width/2, (int)-width/2, (int)width, (int)width);
        }else{
            g2dBuffer.drawOval((int)-width/2, (int)-width/2, (int)width, (int)width);
        }
    }
}