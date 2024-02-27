package Canvas;
import java.awt.*;
import java.awt.Polygon;

public class Polygoni extends Obj{
    protected int[] xcoords;
    protected int[] ycoords;
    public Polygoni(String type,int X,int Y,int[] arrintx,int[] arrinty, Color Color,boolean tf){
        super(type,X,Y,0,0,Color,tf);
        this.xcoords=arrintx;
        this.ycoords=arrinty;
        int greatx=arrintx[0];
        int greaty=arrinty[0];
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
    public void addVertex(int index, int x, int y){
        int[] arrx=new int[xcoords.length+1];
        int[] arry=new int[xcoords.length+1];
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
    public void changeVertexPos(int indexOfPoint, int x, int y){
        this.xcoords[indexOfPoint]=x;
        this.ycoords[indexOfPoint]=y;

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
    /**
     * The Polygon application of the show method, draws a Polygon based on instance data
     */
    @Override
    public void show(Graphics2D g2dBuffer){
        if (fill){
            Object[] poly=objectInformation();
            int[] arrintx=(int[])poly[0];
            int[] arrinty=(int[])poly[1];
            int[] z=new int[arrintx.length];
            int[] a=new int[arrinty.length];
            for (int y=0;y<arrintx.length;y++){
                z[y]=-width/2;
                a[y]=-length/2;
            }
            int[] b=ArrMath.addArrs(arrintx,z);
            int[] c=ArrMath.addArrs(arrinty,a);
            g2dBuffer.fillPolygon(b,c,arrintx.length);
            z=ArrMath.minusArrs(arrintx,z);
            a=ArrMath.minusArrs(arrinty,a);
        }else{
            Object[] poly=objectInformation();
            int[] arrintx=(int[])poly[0];
            int[] arrinty=(int[])poly[1];
            int[] z=new int[arrintx.length];
            int[] a=new int[arrinty.length];
            for (int y=0;y<arrintx.length;y++){
                z[y]=-width/2;
                a[y]=-length/2;
            }
            int[] b=ArrMath.addArrs(arrintx,z);
            int[] c=ArrMath.addArrs(arrinty,a);
            g2dBuffer.drawPolygon(b,c,arrintx.length);
            z=ArrMath.minusArrs(arrintx,z);
            a=ArrMath.minusArrs(arrinty,a);
        }
        
    }
}