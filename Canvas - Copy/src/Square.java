import java.awt.*;

class Square extends Polygoni{
    public Square(int X,int Y,int SideLength, Color Color,boolean tf){
        super("Square",X,Y,new int []{0,SideLength,SideLength,0},new int []{0,0,SideLength,SideLength},Color,tf);
    }
    @Override
    public void setSize(int newSize){
        this.width=newSize;
        this.length=newSize;
        resetRect();
    }
}