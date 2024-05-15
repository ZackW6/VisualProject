package Canvas.Shapes;
import java.awt.*;

public class Line extends Polygoni{
    public Line(int X,int Y,double[] arrintx,double[] arrinty, Color Color,int LineWidth){
        super("Line",X,Y,arrintx,arrinty,Color,true);
        remakeLine(arrintx, arrinty, LineWidth);
    }
    public Line(int X,int Y,int[] arrintx,int[] arrinty, Color Color,int LineWidth){   
        super("Line",X,Y,arrintx,arrinty,Color,true);
        remakeLine(arrintx, arrinty, LineWidth);
    }

    private void remakeLine(double[] arrintx, double[] arrinty, double LineWidth){
        double slope;
        if (arrintx[0]<arrintx[1]){
            slope = (arrinty[1]-arrinty[0])/(arrintx[1]-arrintx[0]);
        }else{
            slope = (arrinty[0]-arrinty[1])/(arrintx[0]-arrintx[1]);
        }
        double perpendicular = -1.0/(slope+0.00000000001);

        double[] newx=new double[4];
        double[] newy=new double[4];

        double movex = Math.sqrt((Math.pow(LineWidth/2,2))/(1+Math.pow(perpendicular,2)));
        double movey = movex * perpendicular;

        newx[0]=(arrintx[0]+movex);
        newy[0]=(arrinty[0]+movey);
        newx[1]=(arrintx[0]-movex);
        newy[1]=(arrinty[0]-movey);
        newx[2]=(arrintx[1]-movex);
        newy[2]=(arrinty[1]-movey);
        newx[3]=(arrintx[1]+movex);
        newy[3]=(arrinty[1]+movey);
        this.xcoords=newx;
        this.ycoords=newy;
    }
    private void remakeLine(int[] arrintx, int[] arrinty, int LineWidth){
        double[] newx = intToDoubleArray(arrintx);
        double[] newy = intToDoubleArray(arrinty);
        remakeLine(newx, newy, (double)LineWidth);
    }

    public static double[] intToDoubleArray(int[] arr) {
        double[] temp = new double[arr.length];

        for (int i = 0; i < arr.length; i++) {
            temp[i] = arr[i];
        }
        return temp;
    }
    
    @Override
    public void changeVertexPos(int indexOfPoint, double x, double y){
        this.xcoords[indexOfPoint]=x;
        this.ycoords[indexOfPoint]=y;
    }
}