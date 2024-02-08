import java.awt.*;

class Rectangle extends Polygoni{
    public Rectangle(int X,int Y,int Width,int Height, Color Color,boolean tf){   
        super("Rectangle",X,Y,new int []{0,Width,Width,0},new int []{0,0,Height,Height},Color,tf);
    }
    public void changeWidth(int newSize){
        this.width=newSize;
        resetRect();
    }
    public void changeLength(int newSize){
        this.length=newSize;
        resetRect();
    }
}