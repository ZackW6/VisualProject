package Canvas;
import java.awt.*;

public class Line extends Polygoni{
    public Line(int X,int Y,int[] arrintx,int[] arrinty, Color Color,int LineWidth){   
        super("Line",X,Y,arrintx,arrinty,Color,true);
        int xm=arrintx[0]-arrintx[1];
        int ym=arrinty[0]-arrinty[1];
        if (ym/xm==1||ym/xm==-1){
            xm++;
        }
        double slope=(1/((double)ym/xm));
        int[] newx=new int[4];
        int[] newy=new int[4];

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
    @Override
    public void changeVertexPos(int indexOfPoint, int x, int y){
        this.xcoords[indexOfPoint]=x;
        this.ycoords[indexOfPoint]=y;
    }
}