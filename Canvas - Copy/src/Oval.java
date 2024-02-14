import java.awt.*;

class Oval extends Obj{
    public Oval(int X,int Y,int Width,int Length, Color Color,boolean tf){   
        super("Oval",X,Y,Width,Length,Color,tf);
    }
    public void changeWidth(int newSize){
        this.width=newSize;
    }
    public void changeLength(int newSize){
        this.length=newSize;
    }

    @Override
    public void show(Graphics2D g2dBuffer){
        if (fill){
            g2dBuffer.fillOval(-width/2, -length/2, width, length);
        }else{
            g2dBuffer.drawOval(-width/2, -length/2, width, length);
        }
    }
}