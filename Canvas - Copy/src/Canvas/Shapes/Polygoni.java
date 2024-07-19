package Canvas.Shapes;
import java.awt.*;
import java.awt.Polygon;
import java.awt.image.AbstractMultiResolutionImage;

import Canvas.Util.ArrMath;

public abstract class Polygoni extends Obj{
    protected double[] xcoords;
    protected double[] ycoords;
    public Polygoni(String type,double X,double Y,double[] arrintx,double[] arrinty, Color Color,boolean tf){
        super(X,Y,0,0,Color,tf);
        this.xcoords=arrintx;
        this.ycoords=arrinty;
        double greatx=arrintx[0];
        double greaty=arrinty[0];
        for (int i=1;i<this.xcoords.length;i++){
            if (this.xcoords[i]>greatx){
                greatx=this.xcoords[i];
            }
            if (this.ycoords[i]>greaty){
                greaty=this.ycoords[i];
            }
        }

        this.width=greatx;
        this.length=greaty;
    }
    public Polygoni(String type, double X, double Y,int[] arrintx,int[] arrinty, Color Color,boolean tf){
        super(X,Y,0,0,Color,tf);
        this.xcoords = ArrMath.intToDoubleArray(arrintx);
        this.ycoords = ArrMath.intToDoubleArray(arrinty);
        double greatx = arrintx[0];
        double greaty = arrinty[0];
        for (int i=1;i<this.xcoords.length;i++){
            if (this.xcoords[i]>greatx){
                greatx = this.xcoords[i];
            }
            if (this.ycoords[i]>greaty){
                greaty = this.ycoords[i];
            }
        }

        this.width=greatx;
        this.length=greaty;
    }

    public void resetPolygon(){
        
    }
    
    /**
     * Add a vertex to the current polygon
     * @param index where to add the vertex
     * @param x
     * @param y
     */
    public void addVertex(int index, double x, double y){
        double[] arrx=new double[xcoords.length+1];
        double[] arry=new double[xcoords.length+1];

        for (int i=0;i<arrx.length;i++){
            if (i==index){
                arrx[i]=x;
                arry[i]=y;
                i++;
            }else{
                arrx[i]=this.xcoords[i];
                arry[i]=this.ycoords[i];
            }
        }
        this.xcoords=arrx;
        this.ycoords=arry;
    }
    /**
     * change the vertex of any index point
     * @param indexOfPoint
     * @param x
     * @param y
     */
    public void changeVertexPos(int indexOfPoint, double x, double y){
        this.xcoords[indexOfPoint]=x;
        this.ycoords[indexOfPoint]=y;

    }
    /**
     * The Polygon application of the show method, draws a Polygon based on instance data
     */
    @Override
    public void show(Graphics2D g2dBuffer){
        int[] arrintx=ArrMath.doubleToIntArray(this.xcoords);
        int[] arrinty=ArrMath.doubleToIntArray(this.ycoords);
        int[] z=new int[arrintx.length];
        int[] a=new int[arrinty.length];
        int[] b=ArrMath.addArrs(arrintx,z);
        int[] c=ArrMath.addArrs(arrinty,a);
        for (int y=0;y<arrintx.length;y++){
            z[y]=(int)-width/2;
            a[y]=(int)-length/2;
        }
        if (fill){
            g2dBuffer.fillPolygon(b,c,arrintx.length);
            
        }else{
            g2dBuffer.drawPolygon(b,c,arrintx.length);
        }
        z=ArrMath.minusArrs(arrintx,z);
        a=ArrMath.minusArrs(arrinty,a);
    }
}