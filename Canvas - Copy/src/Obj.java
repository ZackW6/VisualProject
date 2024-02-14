import java.awt.*;
import java.awt.Rectangle;

public class Obj{
    protected Shape shape;
    protected String type;
    protected int xcoord;
    protected int ycoord;
    protected int width;
    protected int length;
    protected Color col;
    protected int degree=0;
    protected boolean fill;
    protected int xxcoord;
    protected int xycoord;
    protected int degrees2=0;
    public Obj(String type,int xcoord,int ycoord,int width, int length,Color col,boolean fill){
        this.type=type;
        this.xcoord=xcoord;
        this.ycoord=ycoord;
        this.col=col;
        this.width=width;
        this.length=length;
        this.fill=fill;
    }
    public void rotate(int rotateDegree){
        while (rotateDegree>=360){
            rotateDegree-=360;
        }
        this.degree=rotateDegree;
    }
    public void move(int moveX,int moveY){
        this.xcoord=moveX+xcoord;
        this.ycoord=moveY+ycoord;
    }


    


    public int getIndex(Obj[] x){
        for (int i=0;i<x.length;i++){
            if (x[i].equals(this)){
                return i;
            }
        }
        return -1;
    }
    protected Object[] objectInformation(){
        return new Object[]{0,0};
    }



    public void changeFill(boolean fillOrNo){
        this.fill=fillOrNo;
    }
    public void changeColor(Color newCol){
        this.col=newCol;
    }
    public void rotPoint(int x, int y, int deg){
        degrees2=deg;
        double radius=Math.sqrt(Math.pow(x-(xcoord+width/2),2)+Math.pow(y-(ycoord+length/2),2));
        double curRad = Math.toDegrees(Math.atan2((ycoord+length/2) - y, (xcoord+width/2) - x));
        xxcoord=(int)(x+radius*Math.cos(Math.toRadians(degrees2+curRad)))-xcoord-width/2;
        xycoord=(int)(y+radius*Math.sin(Math.toRadians(degrees2+curRad)))-ycoord-length/2;
    }



    public void setSize(int newSize){
        
    }
    public void setPosition(int x, int y){
        this.xcoord=x;
        this.ycoord=y;
    }
    public void setColor(Color col){
        this.col=col;
    }
    public Shape defineShape(){
        return new Rectangle();
    }
    public void show(Graphics2D g2dBuffer){

    }
}
