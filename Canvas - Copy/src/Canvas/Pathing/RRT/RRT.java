package Canvas.Pathing.RRT;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Canvas.Shapes.Circle;
import Canvas.Shapes.PolyShape;
import Canvas.Shapes.VisualJ;

public class RRT  extends RRTBase{

    private boolean isFinished = false;

    private VisualJ vis;

    List<PolyShape> paths = new ArrayList<>();

    public RRT(VisualJ vis, Field field) {
        super(vis, field);
        this.vis = vis;
    }

    protected void runAction(){
        Node randomPoint = getRandomPoint(getBias());
        Node nearestPoint = getNearestPoint(randomPoint);
        Node newPoint = getNewPoint(randomPoint, nearestPoint);

        if (!collidesObstacle(newPoint) && !isFinished()) {
            List<Node> nodes = getNodes();
            
            drawing.add(newPoint.getCircle());
            
            nodes.add(newPoint);
            
            if (newPoint.distanceTo(getGoal()) < getMinimumDist()) {
                nodes.add(new Node(getGoal().x, getGoal().y, newPoint));
                isFinished = true;
                
                paths.add(getPath(nodes.get(nodes.size()-1)));
                vis.add(paths.get(paths.size()-1));
            }
        }
    }

    public boolean isFinished() {
        return isFinished;
    }
}
