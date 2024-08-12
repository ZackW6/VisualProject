package Canvas.Pathing.RRT;

import java.awt.Color;

import Canvas.Shapes.Circle;
import Canvas.Shapes.Line;
import Canvas.Util.Vector2D;

public class Node extends Vector2D{

    private Node parent = null;
    private Circle circle = new Circle(0, 0, 5, Color.BLUE, true);
    public Node(double x, double y, Node parent) {
        super(x, y);
        this.parent = parent;
        circle.setPosition(x-circle.getWidth()/2,y-circle.getHeight()/2);
    }

    public Node(Node copy) {
        super(copy.x, copy.y);
        this.parent = copy.parent;
        circle.setPosition(x-circle.getWidth()/2,y-circle.getHeight()/2);
    }

    public Node(double x, double y){
        super(x, y);
        circle.setPosition(x-circle.getWidth()/2,y-circle.getHeight()/2);
    }

    public Node(Node place, Node parent){
        super(place.x, place.y);
        this.parent = parent;
        circle.setPosition(x-circle.getWidth()/2,y-circle.getHeight()/2);
    }

    public Vector2D getVector2D(){
        return Vector2D.of(this.x, this.y);
    }

    public Node getParent(){
        return parent;
    }

    public void setParent(Node parent){
        this.parent = parent;
    }

    public boolean isDescendedOf(Node possibleParent){
        if (this.equals(possibleParent)){
            return true;
        }
        Node temp = new Node(this);
        while (temp != null){
            if (temp.equals(possibleParent)){
                return true;
            }
            temp = temp.getParent();
        }
        return false;
    }

    public void setColor(Color col){
        circle.setColor(col);
    }

    public void setWidth(double width){
        circle.setWidth(width);
        circle.setPosition(x-circle.getWidth()/2,y-circle.getHeight()/2);
    }

    public Circle getCircle(){
        return circle;
    }
}
