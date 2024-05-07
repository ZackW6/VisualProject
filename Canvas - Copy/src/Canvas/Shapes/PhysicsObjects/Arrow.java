package Canvas.Shapes.PhysicsObjects;

import java.awt.Color;
import java.util.ArrayList;

import Canvas.Shapes.Polygon;
import Canvas.Shapes.VisualJ;

public class Arrow extends Polygon{
    /**
     * width is width of arrow, not line
     * @param X
     * @param Y
     * @param length
     * @param width
     * @param Color
     * @param fill
     */
    public Arrow(int X, int Y, int length, int width, Color Color, boolean fill) {
        super(X, Y, new int[]{-width/4, width/4, width/4, width/2, 0 ,-width/2, -width/4}, new int[]{0, 0, length, length, length+length/5,length, length}, Color, fill);


    }

    public void rotateAroundBase(double degree){
        this.rotPoint(coords.x, coords.y, degree);
        this.rotate(degrees2);
    }

    public void setLength(double length){
        changeVertexPos(2, width/4, length);
        changeVertexPos(3, width/2, length);
        changeVertexPos(4, 0, length+length/5);
        changeVertexPos(5, -width/2, length);
        changeVertexPos(6, -width/4, length);
    }

    public void setWidth(double width){
        changeVertexPos(0, -width/4, 0);
        changeVertexPos(1, width/4, 0);
        changeVertexPos(2, width/4, length);
        changeVertexPos(3, width/2, length);
        changeVertexPos(5, -width/2, length);
        changeVertexPos(6, -width/4, length);
    }

    public void setSize(double width, double length){
        changeVertexPos(0, -width/4, 0);
        changeVertexPos(1, width/4, 0);
        changeVertexPos(2, width/4, length);
        changeVertexPos(3, width/2, length);
        changeVertexPos(4, 0, length+length/5);
        changeVertexPos(5, -width/2, length);
        changeVertexPos(6, -width/4, length);
    }
}
