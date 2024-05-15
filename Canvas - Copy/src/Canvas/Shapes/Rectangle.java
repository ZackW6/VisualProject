package Canvas.Shapes;
import java.awt.*;

public class Rectangle extends Polygoni{
    public Rectangle(double X,double Y,double Width,double Height, Color Color,boolean tf){   
        super("Rectangle",X,Y,new double []{0,Width,Width,0},new double []{0,0,Height,Height},Color,tf);
    }
    public Rectangle(String type, double X,double Y,double Width,double Height, Color Color,boolean tf){   
        super(type,X,Y,new double []{0,Width,Width,0},new double []{0,0,Height,Height},Color,tf);
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
    protected void resetRect(){
        xcoords[0]=0;
        ycoords[0]=0;
        xcoords[1]=width;
        ycoords[1]=0;
        xcoords[2]=width;
        ycoords[2]=length;
        xcoords[3]=0;
        ycoords[3]=length;
    }
}