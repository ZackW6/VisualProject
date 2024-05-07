package Canvas.Shapes;
import java.awt.*;
import java.awt.Rectangle;

import Canvas.Shapes.PhysicsObjects.Vector2D;
/**
 * Base class for all shapes of the library
 */
public abstract class Obj{
    protected String type;
    protected Vector2D coords = new Vector2D(0, 0);
    protected double width;
    protected double length;
    protected Color col;
    protected double degree=0;
    protected boolean fill;
    protected double xxcoord;
    protected double xycoord;
    protected double degrees2=0;
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
    protected Obj(String type,double xcoord,double ycoord,int width, int length,Color col,boolean fill){
        this.type=type;
        this.coords.x=xcoord;
        this.coords.y=ycoord;
        this.col=col;
        this.width=width;
        this.length=length;
        this.fill=fill;
    }
    /**
     * Rotate based on center
     * @param rotateDegree
     */
    public void rotate(double rotateDegree){
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
    public void move(double moveX,double moveY){
        this.coords.x=moveX+coords.x;
        this.coords.y=moveY+coords.y;
    }

    public double getDegree(){
        return degree;
    }
    public double getRotPointDegree(){
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
    public void rotPoint(double x, double y, double deg){
        degrees2=deg;
        double radius=Math.sqrt(Math.pow(x-(coords.x+width/2),2)+Math.pow(y-(coords.y+length/2),2));
        double curRad = Math.toDegrees(Math.atan2((coords.y+length/2) - y, (coords.x+width/2) - x));
        xxcoord=(int)(x+radius*Math.cos(Math.toRadians(degrees2+curRad)))-(int)coords.x-width/2;
        xycoord=(int)(y+radius*Math.sin(Math.toRadians(degrees2+curRad)))-(int)coords.y-length/2;
    }

    /**
     * Set the position of the shape
     * @param x
     * @param y
     */
    public void setPosition(double x, double y){
        this.coords.x=x;
        this.coords.y=y;
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

    public Vector2D getCoords(){
        return coords;
    }

    public double getWidth(){
        return width;
    }

    public double getLength(){
        return length;
    }
}
