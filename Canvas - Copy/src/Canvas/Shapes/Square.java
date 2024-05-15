package Canvas.Shapes;
import java.awt.*;

public class Square extends Rectangle{
    public Square(double X,double Y,double SideLength, Color Color,boolean tf){
        super("Square",X,Y,SideLength,SideLength,Color,tf);
    }
    /**
     * Set the size of the square
     */
    public void setSize(int newSize){
        this.width=newSize;
        this.length=newSize;
        resetRect();
    }
}