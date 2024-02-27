package Canvas;
import java.awt.*;
import java.awt.Rectangle;
/**
 * Base class for all shapes of the library
 */
public class Obj{
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
    /**
     * Base class public for the Obj class, not meant to be implimented 
     * @param type
     * @param xcoord
     * @param ycoord
     * @param width
     * @param length
     * @param col
     * @param fill
     */
    protected Obj(String type,int xcoord,int ycoord,int width, int length,Color col,boolean fill){
        this.type=type;
        this.xcoord=xcoord;
        this.ycoord=ycoord;
        this.col=col;
        this.width=width;
        this.length=length;
        this.fill=fill;
    }
    /**
     * Rotate based on center
     * @param rotateDegree
     */
    public void rotate(int rotateDegree){
        while (rotateDegree>=360){
            rotateDegree-=360;
        }
        this.degree=rotateDegree;
    }
    /**
     * Add on to current x and y
     * @param moveX
     * @param moveY
     */
    public void move(int moveX,int moveY){
        this.xcoord=moveX+xcoord;
        this.ycoord=moveY+ycoord;
    }

    public int getDegree(){
        return degree;
    }
    public int getRotPointDegree(){
        return degrees2;
    }
    

    /**
     * Return the index of this object in the array
     * @param x
     * @return
     */
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


    /**
     * Change whether or not the shape is filled
     * @param fillOrNo
     */
    public void changeFill(boolean fillOrNo){
        this.fill=fillOrNo;
    }
    /**
     * Rotate the shape around a certain point, will not rotate around self
     * @param x
     * @param y
     * @param deg
     */
    public void rotPoint(int x, int y, int deg){
        degrees2=deg;
        double radius=Math.sqrt(Math.pow(x-(xcoord+width/2),2)+Math.pow(y-(ycoord+length/2),2));
        double curRad = Math.toDegrees(Math.atan2((ycoord+length/2) - y, (xcoord+width/2) - x));
        xxcoord=(int)(x+radius*Math.cos(Math.toRadians(degrees2+curRad)))-xcoord-width/2;
        xycoord=(int)(y+radius*Math.sin(Math.toRadians(degrees2+curRad)))-ycoord-length/2;
    }
    /**
     * Set the position of the shape
     * @param x
     * @param y
     */
    public void setPosition(int x, int y){
        this.xcoord=x;
        this.ycoord=y;
    }
    /**
     * Set the current color of the shape
     * @param newCol
     */
    public void setColor(Color col){
        this.col=col;
    }

    /**
     * Should not be called directly from Obj class
     * @param g2dBuffer
     */
    public void show(Graphics2D g2dBuffer){

    }
}
