package Canvas.Shapes;
import java.awt.*;

public class Square extends Polygoni{
    public Square(double X,double Y,double SideLength, Color Color,boolean tf){
        super("Square",X,Y,new double []{0,SideLength,SideLength,0},new double []{0,0,SideLength,SideLength},Color,tf);
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