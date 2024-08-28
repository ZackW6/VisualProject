package Canvas.Pathing.RRT;

import java.util.List;

import Canvas.Pathing.RRT.RRTHelperBase.Field;
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

    public List<Node> getNodes();

    public Vector2D getStart();

    public Vector2D getGoal();

    public double getMinimumDist();

    public double getMaxStepSize();

    public Vector2D getDrawingCoords();

    public double getBias();
}
