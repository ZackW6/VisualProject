package Canvas.Shapes;
import java.awt.*;

public class Polygon extends Polygoni{
    public Polygon(double X,double Y,double[] arrintx,double[] arrinty, Color Color,boolean tf){
        super("Polygon",X,Y,arrintx,arrinty,Color,tf);
    }
    public Polygon(double X,double Y,int[] arrintx,int[] arrinty, Color Color,boolean tf){
        super("Polygon",X,Y,arrintx,arrinty,Color,tf);
    }
}