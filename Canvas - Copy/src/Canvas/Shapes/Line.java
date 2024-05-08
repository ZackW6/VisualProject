package Canvas.Shapes;
import java.awt.*;

public class Line extends Polygoni{
    public Line(int X,int Y,double[] arrintx,double[] arrinty, Color Color,int LineWidth){   
        super("Line",X,Y,arrintx,arrinty,Color,true);
        double xm=arrintx[0]-arrintx[1];
        double ym=arrinty[0]-arrinty[1];
        if (ym/xm==1||ym/xm==-1){
            xm++;
        }
        double slope=(1/((double)ym/xm));
        double[] newx=new double[4];
        double[] newy=new double[4];

        newx[0]=(LineWidth+this.xcoords[0]);
        newy[0]=((int)(LineWidth*slope)+this.ycoords[0]);
        newx[1]=(-LineWidth+this.xcoords[0]);
        newy[1]=(-(int)(LineWidth*slope)+this.ycoords[0]);
        newx[2]=(-LineWidth+this.xcoords[1]);
        newy[2]=(-(int)(LineWidth*slope)+this.ycoords[1]);
        newx[3]=(LineWidth+this.xcoords[1]);
        newy[3]=((int)(LineWidth*slope)+this.ycoords[1]);
        this.xcoords=newx;
        this.ycoords=newy;
    }
    public Line(int X,int Y,int[] arrintx,int[] arrinty, Color Color,int LineWidth){   
        super("Line",X,Y,arrintx,arrinty,Color,true);
        double xm=arrintx[0]-arrintx[1];
        double ym=arrinty[0]-arrinty[1];
        if (ym/xm==1||ym/xm==-1){
            xm++;
        }
        double slope=(1/((double)ym/xm));
        double[] newx=new double[4];
        double[] newy=new double[4];

        newx[0]=(LineWidth+this.xcoords[0]);
        newy[0]=((int)(LineWidth*slope)+this.ycoords[0]);
        newx[1]=(-LineWidth+this.xcoords[0]);
        newy[1]=(-(int)(LineWidth*slope)+this.ycoords[0]);
        newx[2]=(-LineWidth+this.xcoords[1]);
        newy[2]=(-(int)(LineWidth*slope)+this.ycoords[1]);
        newx[3]=(LineWidth+this.xcoords[1]);
        newy[3]=((int)(LineWidth*slope)+this.ycoords[1]);
        this.xcoords=newx;
        this.ycoords=newy;
    }

    private void remakeLine(int[] arrintx, int[] arrinty, double LineWidth){
        double slope;
        if (arrintx[0]<arrintx[1]){
            slope = (arrinty[1]-arrinty[0]);
        }else{
            slope = (arrinty[0]-arrinty[1]);
        }
    }
    
    @Override
    public void changeVertexPos(int indexOfPoint, double x, double y){
        this.xcoords[indexOfPoint]=x;
        this.ycoords[indexOfPoint]=y;
    }
}