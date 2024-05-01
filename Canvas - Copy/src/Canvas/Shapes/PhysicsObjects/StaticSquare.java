package Canvas.Shapes.PhysicsObjects;

import java.awt.Color;
import java.util.ArrayList;

import Canvas.Shapes.Square;

public class StaticSquare extends Square{
    public static ArrayList<StaticSquare> arr = new ArrayList<StaticSquare>();
    public StaticSquare(int X,int Y,int SideLength, Color Color,boolean tf){
        super(X,Y,SideLength,Color,tf);
        arr.add(this);
    }
    public static ArrayList<StaticSquare> getArrayList(){
        return arr;
    }
}
