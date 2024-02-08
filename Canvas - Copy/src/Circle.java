import java.awt.*;

class Circle extends Obj{ 
    public Circle(int X,int Y,int Radius, Color Color,boolean tf){   
        super("Circle",X,Y,Radius,Radius,Color,tf);
        this.width=Radius;
    }
    @Override
    public void setSize(int newSize){
        this.width=newSize;
        this.length=newSize;
    }
}