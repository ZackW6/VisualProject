package Canvas.Pathing.RRT;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Canvas.Pathing.RRT.RRTHelperBase.Field;
import Canvas.Shapes.Line;
import Canvas.Shapes.PolyShape;
import Canvas.Util.KDTree;
import Canvas.Util.Vector2D;

public interface RRTBase {

    public void process();
    
    public void scheduleObstacles(List<Obstacle> obstacles);

    public void scheduleStart(Vector2D start);

    public void scheduleGoal(Vector2D goal);

    public void setObstacles(List<Obstacle> obstacles);

    public void addObstacles(List<Obstacle> obstacles);

    public void removeObstacles(List<Obstacle> obstacles);

    public void setStart(Vector2D start);

    public void setGoal(Vector2D goal);

    public void setMinimumDistance(double dist);

    public void setMaxStepDist(double dist);

    public void setDrawingCoords(double x, double y);

    public void setBias(double bias);

    public Field getField();

    public List<Obstacle> getObstacles();

    public default KDTree<Obstacle> getKDTreeObstacles(){
        return null;
    }

    public List<Node> getNodes();

    public Vector2D getStart();

    public Vector2D getGoal();

    public double getMinimumDist();

    public double getMaxStepSize();

    public Vector2D getDrawingCoords();

    public double getBias();

    public default PolyShape getPath(Node nodePathEnd){
        Node activeNode = new Node(nodePathEnd);
        PolyShape poly = new PolyShape(0, 0);
        int i = 0;
        while(activeNode.getParent() != null && i <100){
            poly.add(
                new Line(activeNode.x + Math.min(activeNode.getParent().x-activeNode.x,0), activeNode.y + Math.min(activeNode.getParent().y-activeNode.y,0)
                , List.of(Vector2D.of(0,0)
                ,Vector2D.of(activeNode.getParent().x-activeNode.x,activeNode.getParent().y-activeNode.y))
                , Color.GREEN, 3)
            );
            activeNode = activeNode.getParent();
            i++;
        }
        return poly;
    }

    public default List<Node> findCornerNodes(Obstacle obstacle){
        Vector2D corner1 = obstacle.getCoords();
        Vector2D corner2 = new Vector2D(corner1.x + obstacle.getWidth(), corner1.y);
        Vector2D corner3 = new Vector2D(corner1.x, corner1.y + obstacle.getHeight());
        Vector2D corner4 = new Vector2D(corner1.x + obstacle.getWidth(), corner1.y + obstacle.getHeight());
        ArrayList<Node> workingNodes = new ArrayList<>();

        Node node1 = new Node(corner1.add(Vector2D.of(-.001,-.001)));
        if (!collidesObstacle(new Node(corner1.add(Vector2D.of(-.001,-.001))), new Node(corner1.add(Vector2D.of(-.001,-.001))))){
            workingNodes.add(node1);
        }
        Node node2 = new Node(corner2.add(Vector2D.of(.001,-.001)));
        if (!collidesObstacle(new Node(corner2.add(Vector2D.of(.001,-.001))), new Node(corner2.add(Vector2D.of(.001,-.001))))){
            workingNodes.add(node2);
        }
        Node node3 = new Node(corner3.add(Vector2D.of(-.001,.001)));
        if (!collidesObstacle(new Node(corner3.add(Vector2D.of(-.001,.001))), new Node(corner3.add(Vector2D.of(-.001,.001))))){
            workingNodes.add(node3);
        }
        Node node4 = new Node(corner4.add(Vector2D.of(.001,.001)));
        if (!collidesObstacle(new Node(corner4.add(Vector2D.of(.001,.001))), new Node(corner4.add(Vector2D.of(.001,.001))))){
            workingNodes.add(node4);
        }
        return workingNodes;
    }

    public default boolean collidesObstacle(Node point) {
        if (point.getParent() == null){
            System.out.println("INCORRECT USE OF OBSTACLES, CHECK RRTHelperBASE 114");
            return true;
        }

        if (getKDTreeObstacles().getRoot() == null){
            return false;
        }

        double[] obstacleDimensions = new double[]{0,0};
        getKDTreeObstacles().traverseNodes(obstacle -> {
            if (obstacle.getWidth() > obstacleDimensions[0]){
                obstacleDimensions[0] = obstacle.getWidth();
            }
            if (obstacle.getHeight() > obstacleDimensions[1]){
                obstacleDimensions[1] = obstacle.getHeight();
            }
        });
        
        Vector2D upperBound;
        Vector2D lowerBound;
        if (point.x > point.getParent().x){
            if (point.y > point.getParent().y){
                upperBound = point;
                lowerBound = point.getParent();
            }else{
                upperBound = Vector2D.of(point.x, point.getParent().y);
                lowerBound = Vector2D.of(point.getParent().x, point.y);
            }
            
        }else{
            if (point.y > point.getParent().y){
                upperBound = Vector2D.of(point.getParent().x, point.y);
                lowerBound = Vector2D.of(point.x, point.getParent().y);
            }else{
                lowerBound = point;
                upperBound = point.getParent();
            }
        }
        
        for (Obstacle obstacle : getKDTreeObstacles().findInRange(
            Vector2D.of(lowerBound.x - obstacleDimensions[0]
            , lowerBound.y - obstacleDimensions[1]), Vector2D.of(upperBound.x+obstacleDimensions[0],upperBound.y+obstacleDimensions[1]))) {
            if (obstacle.didCollide(point, point.getParent())){
                return true;
            }
        }
        return false;
    }

    public default boolean collidesObstacle(Node one, Node two) {
        return collidesObstacle(new Node(one, two));
    }

}
