package Canvas.Shapes;
import java.awt.*;

public class Rectangle extends Polygoni{
    public Rectangle(double X,double Y,double Width,double Height, Color Color,boolean tf){   
        super("Rectangle",X,Y,new double []{0,Width,Width,0},new double []{0,0,Height,Height},Color,tf);
    }
    /**
     * Set width of the oval
     * @param newSize
     */
    public void changeWidth(int newSize){
        this.width=newSize;
        resetRect();
    }
    /**
     * Set length of the oval
     * @param newSize
     */
    public void changeLength(int newSize){
        this.length=newSize;
        resetRect();
    }
}