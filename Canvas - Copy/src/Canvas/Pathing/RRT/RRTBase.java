package Canvas.Pathing.RRT;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Canvas.Shapes.Circle;
import Canvas.Shapes.Line;
import Canvas.Shapes.Obj;
import Canvas.Shapes.PolyShape;
import Canvas.Shapes.Rectangle;
import Canvas.Shapes.Square;
import Canvas.Shapes.VisualJ;
import Canvas.Util.KDTree;
import Canvas.Util.Vector2D;

public abstract class RRTBase {
    // protected KDTree nodes = new KDTree();
    protected ArrayList<Node> nodes = new ArrayList<>();

    protected ArrayList<Obstacle> obstacles = new ArrayList<>();

    protected Node start = new Node(0, 0);

    protected Node goal = new Node(1000,600);

    private Field field = new Field(0, 0, 1000, 600);

    private double minimumDist = 10;

    private double maxStepSize = 10;

    protected PolyShape drawing;

    private double bias = 0;
    
    private Runnable[] initActions = new Runnable[3];
    /**
     * 
     * @param vis screen to act on
     */
    public RRTBase(VisualJ vis, Field field){
        for (int i = 0; i < initActions.length; i++){
            initActions[i] = ()->{};
        }

        this.field = field;
        ArrayList<Obj> temp = new ArrayList<>();
        this.start.getCircle().setColor(Color.ORANGE);
        temp.add(start.getCircle());
        goal.setColor(Color.MAGENTA);
        temp.add(goal.getCircle());
        temp.addAll(obstacles);
        drawing = new PolyShape(0, 0, temp);
        vis.add(drawing);
        nodes.add(start);
    }

    public void process(){
        for (int i = 0; i < initActions.length; i++){
            initActions[i].run();
            initActions[i] = ()->{};
        }
        
        runAction();
    }

    protected abstract void runAction();

    public PolyShape getPath(Node nodePathEnd){
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

    protected Node getRandomPoint(double bias) {
        double chance = Math.random();
        if (chance > bias){
            double x = ((Math.random()*(field.width-field.x))+field.x);//Random.randDouble(0.0, field.width());
            double y = ((Math.random()*(field.height-field.y))+field.y);//Random.randDouble(0.0, field.height());
            return new Node(x, y);
        }

        return new Node(goal.x,goal.y);
    }

    protected Node getNearestPoint(Node newPoint) {
        try {
            Node nearestPoint = nodes.get(0);
            double minDist = newPoint.distanceTo(nearestPoint);

            for (Node point : nodes) {
                double dist = newPoint.distanceTo(point);
                if (dist < minDist) {
                    minDist = dist;
                    nearestPoint = point;
                }
            }
            return nearestPoint; 
        } catch (Exception e) {
            return null;
        }
    }

    protected Node getNewPoint( Node random, Node nearest) {
        double theta = Math.atan2(random.y - nearest.y, random.x - nearest.x);
        double newX = (nearest.x + maxStepSize * Math.cos(theta));
        double newY = (nearest.y + maxStepSize * Math.sin(theta));

        return new Node(newX, newY, nearest);
    }

    protected boolean collidesObstacle(Node point) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.didCollide(point, point.getParent())){
                return true;
            }
        }
        return false;
    }

    protected boolean collidesObstacle(Node one, Node two) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.didCollide(one, two)){
                return true;
            }
        }
        return false;
    }

    public void scheduleObstacles(List<Obstacle> obstacles){
        initActions[0] = ()->{setObstacles(obstacles);};
    }

    public void scheduleStart(Vector2D start){
        initActions[1] = ()->{setStart(start);};
    }

    public void scheduleGoal(Vector2D goal){
        initActions[2] = ()->{setGoal(goal);};
    }

    protected synchronized void setObstacles(List<Obstacle> obstacles){
        drawing.remove(drawing.getIndex(this.start.getCircle()));
        for (int i = 0; i < nodes.size(); i++){
            if (drawing.getIndex(nodes.get(i).getCircle()) != -1){
                drawing.remove(drawing.getIndex(nodes.get(i).getCircle()));
            }
        }
        
        nodes.clear();
        this.start = new Node(start.x, start.y);
        nodes.add(this.start);
        this.start.getCircle().setColor(Color.ORANGE);
        drawing.add(this.start.getCircle());

        drawing.getArray().removeAll(this.obstacles);
        this.obstacles = new ArrayList<>(obstacles);
        drawing.getArray().addAll(obstacles);
    }

    protected synchronized void setStart(Vector2D start){
        // if (drawing.getIndex(this.start.getCircle()) != -1){
            try {
                drawing.remove(drawing.getIndex(this.start.getCircle()));
            } catch (Exception e) {
                
            }
        // }
        
        for (int i = 0; i < nodes.size(); i++){
            try {
                // if (drawing.getIndex(nodes.get(i).getCircle()) != -1){
                    drawing.remove(drawing.getIndex(nodes.get(i).getCircle()));
                // }
            } catch (Exception e) {
                
            }
            
        }
        nodes.clear();
        this.start = new Node(start.x, start.y);

        nodes.add(this.start);
        this.start.getCircle().setColor(Color.ORANGE);
        drawing.add(this.start.getCircle());
    }

    protected synchronized void setGoal(Vector2D goal){
        
        if (drawing.indexOf(this.goal.getCircle())!=-1){
            drawing.remove(drawing.indexOf(this.goal.getCircle()));
        }
        this.goal = new Node(goal.x,goal.y, start);
        this.goal.setColor(Color.MAGENTA);
        drawing.add(this.goal.getCircle());
    }

    public void setMinimumDistance(double dist){
        this.minimumDist = dist;
    }

    public void setMaxStepDist(double dist){
        this.maxStepSize = dist;
    }

    public void setDrawingCoords(double x, double y){
        this.drawing.setPosition(x, y);
    }

    public void setBias(double bias){
        this.bias = bias;
    }

    public Field getField(){
        return field;
    }

    @SuppressWarnings("unchecked")
    public List<Obstacle> getObstacles(){
        return (List<Obstacle>) obstacles.clone();
    }

    public List<Node> getNodes(){
        return nodes;
    }

    public Vector2D getStart(){
        return start;
    }

    public Vector2D getGoal(){
        return goal;
    }

    public double getMinimumDist(){
        return minimumDist;
    }

    public double getMaxStepSize(){
        return maxStepSize;
    }

    public Vector2D getDrawingCoords(){
        return drawing.getCoords();
    }

    public double getBias(){
        return bias;
    }

    public double getCost(Node node){
        double cost = 0;
        Node temp = new Node(node);

        while (temp.getParent() != null){
            cost += temp.distanceTo(temp.getParent());
            temp = temp.getParent();
        }
        return cost;
    }
    
    public record Field(double x, double y, double width, double height){};
}
