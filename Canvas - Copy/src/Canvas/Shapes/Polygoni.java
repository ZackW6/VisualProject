package Canvas.Shapes;
import java.awt.*;
import java.awt.Polygon;

import Canvas.Util.ArrMath;

public abstract class Polygoni extends Obj{
    protected double[] xcoords;
    protected double[] ycoords;
    public Polygoni(String type,double X,double Y,double[] arrintx,double[] arrinty, Color Color,boolean tf){
        super(type,X,Y,0,0,Color,tf);
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
    public Polygoni(String type,double X,double Y,int[] arrintx,int[] arrinty, Color Color,boolean tf){
        super(type,X,Y,0,0,Color,tf);
        this.xcoords=intToDoubleArray(arrintx);
        this.ycoords=intToDoubleArray(arrinty);
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
    public static double[] intToDoubleArray(int[] arr) {
        double[] temp = new double[arr.length];

        for (int i = 0; i < arr.length; i++) {
            temp[i] = arr[i];
        }
        return temp;
    }
    public static int[] doubleToIntArray(double[] arr) {
        int[] temp = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            temp[i] = (int)arr[i];
        }

        return temp;
    }
    public void resetPolygon(){
        
    }
    @Override
    protected Object[] objectInformation(){
        return new Object[]{this.xcoords,this.ycoords};
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
        if (fill){
            Object[] poly=objectInformation();
            int[] arrintx=doubleToIntArray((double[])poly[0]);
            int[] arrinty=doubleToIntArray((double[])poly[1]);
            int[] z=new int[arrintx.length];
            int[] a=new int[arrinty.length];
            for (int y=0;y<arrintx.length;y++){
                z[y]=(int)-width/2;
                a[y]=(int)-length/2;
            }
            int[] b=ArrMath.addArrs(arrintx,z);
            int[] c=ArrMath.addArrs(arrinty,a);
            g2dBuffer.fillPolygon(b,c,arrintx.length);
            z=ArrMath.minusArrs(arrintx,z);
            a=ArrMath.minusArrs(arrinty,a);
        }else{
            Object[] poly=objectInformation();
            int[] arrintx=doubleToIntArray((double[])poly[0]);
            int[] arrinty=doubleToIntArray((double[])poly[1]);
            int[] z=new int[arrintx.length];
            int[] a=new int[arrinty.length];
            for (int y=0;y<arrintx.length;y++){
                z[y]=(int)-width/2;
                a[y]=(int)-length/2;
            }
            int[] b=ArrMath.addArrs(arrintx,z);
            int[] c=ArrMath.addArrs(arrinty,a);
            g2dBuffer.drawPolygon(b,c,arrintx.length);
            z=ArrMath.minusArrs(arrintx,z);
            a=ArrMath.minusArrs(arrinty,a);
        }
        
    }
}