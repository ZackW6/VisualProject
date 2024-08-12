package Canvas.Pathing.RRT;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Canvas.Shapes.Circle;
import Canvas.Shapes.PolyShape;
import Canvas.Shapes.VisualJ;
import Canvas.Util.Profile;
import Canvas.Util.Vector2D;

public class RRTStar extends RRTBase {

    double bestCost = Double.POSITIVE_INFINITY;

    protected boolean isFinished = false;
    protected VisualJ vis;
    List<PolyShape> paths = new ArrayList<>();

    public RRTStar(VisualJ vis, Field field) {
        super(vis, field);
        this.vis = vis;
    }

    @Override
    protected void runAction() {
        Node randomPoint = getRandomPoint(getBias());
        Node nearestPoint = getNearestPoint(randomPoint);
        Node newPoint = getNewPoint(randomPoint, nearestPoint);

        if (!collidesObstacle(newPoint) && !isFinished()) {
            newPoint.setParent(nearestPoint);

            List<Node> nodes = getNodes();
            List<Node> nearbyNodes = getNearbyNodes(newPoint);
            double currentCost = Double.POSITIVE_INFINITY;
            // Check for better parents among nearby nodes (rewiring)
            for (Node nearbyNode : nearbyNodes) {
                
                double cost = 0;
                Node temp = new Node(nearbyNode);
                while (temp.getParent() != null){
                    cost += temp.distanceTo(temp.getParent());
                    temp = temp.getParent();
                }
                double potentialCost = cost + newPoint.distanceTo(nearbyNode);
                // double potentialCost = nearbyNode.getCost() + nearbyNode.distanceTo(newPoint);

                if (potentialCost < currentCost && !collidesObstacle(newPoint, nearbyNode)) {
                    newPoint.setParent(nearbyNode);
                    currentCost = potentialCost;
                }
            }

            drawing.add(newPoint.getCircle());
            nodes.add(newPoint);
            // Rewire nearby nodes to this new node if it results in a shorter path
            
            for (int i = 0; i < nearbyNodes.size(); i++) {
                if (getCost(newPoint) + newPoint.distanceTo(nearbyNodes.get(i)) < getCost(nearbyNodes.get(i)) && !collidesObstacle(newPoint, nearbyNodes.get(i))) {
                    nearbyNodes.get(i).setParent(newPoint);
                }
            }
            
            Node goalSight = new Node(goal, newPoint);

            if (!collidesObstacle(goalSight)){
                double cost = 0;
                Node temp = new Node(goalSight);

                while (temp.getParent() != null){
                    cost += temp.distanceTo(temp.getParent());
                    temp = temp.getParent();
                }

                if (cost < bestCost){
                    bestCost = cost;

                    drawing.remove(drawing.indexOf(goal.getCircle()));
                
                    goalSight.getCircle().setColor(Color.MAGENTA);
                    this.goal = goalSight;
                    drawing.add(goal.getCircle());
                    
                
                    if (paths.size()>0){
                        vis.remove(paths.get(0));
                        paths.clear();
                    }
                    paths.add(getPath(this.goal));
                    vis.add(paths.get(0));
                }
            }

            if (bestCost != Double.POSITIVE_INFINITY){
                double cost = 0;
                Node temp = new Node(goal);

                while (temp.getParent() != null){
                    cost += temp.distanceTo(temp.getParent());
                    temp = temp.getParent();
                }

                if (cost < bestCost){
                    
                    bestCost = cost;
                    
                    if (paths.size()>0){
                        vis.remove(paths.get(0));
                        paths.clear();
                    }
                    paths.add(getPath(this.goal));
                    vis.add(paths.get(0));
                }
                drawing.moveIndex(goal.getCircle(),drawing.getArray().size()-1);
                drawing.moveIndex(start.getCircle(),drawing.getArray().size()-2);
            }
        }
    }

    protected List<Node> getNearbyNodes(Node newPoint) {
        List<Node> nearbyNodes = new ArrayList<>();
        double radius = calculateRadius();
        for (Node node : getNodes()) {
            if (node.distanceTo(newPoint) < radius) {
                nearbyNodes.add(node);
            }
        }
        return nearbyNodes;
    }

    protected List<Node> getNearbyNodes(Node newPoint, List<Node> toLookThrough) {
        List<Node> nearbyNodes = new ArrayList<>();
        double radius = calculateRadius();
        for (Node node : toLookThrough) {
            if (node.distanceTo(newPoint) < radius) {
                nearbyNodes.add(node);
            }
        }
        return nearbyNodes;
    }

    private double calculateRadius() {
        return 100;
        // int n = getNodes().size();
        // double gammaRRTStar = 1.0; // This constant can be adjusted based on the environment
        // return gammaRRTStar * Math.sqrt(Math.log(n) / n);
    }

    public boolean isFinished() {
        return isFinished;
    }

    @Override
    protected synchronized void setGoal(Vector2D goal){
        bestCost = Double.POSITIVE_INFINITY;
        super.setGoal(goal);
    }

    @Override
    protected synchronized void setStart(Vector2D start){
        bestCost = Double.POSITIVE_INFINITY;
        if (getNodes().indexOf(this.start) != -1){
            getNodes().remove(this.start);
            drawing.remove(this.start.getCircle());
        }

        this.start = new Node(start.x, start.y);

        getNodes().add(this.start);
        this.start.getCircle().setColor(Color.ORANGE);
        drawing.add(this.start.getCircle());

        // ArrayList<Node> newNodes = new ArrayList<>(getNodes());

        // Profile profile  = new Profile();
        // profile.start();
        // for (Node node : getNodes()){
        //     if (!collidesObstacle(node, this.start) && !this.start.equals(node)){
        //         node.setParent(this.start);
        //     }else if (!this.start.equals(node)){
        //         newNodes.remove(node);
        //         // drawing.remove(node.getCircle());
        //     }
        // }
        // profile.stop();
        // System.out.println(profile.getTime());
        // profile.reset();


        
        


        rewireTree(getNodes());
    }

    public void rewireTree(List<Node> nodes) {
        
        for (Node currentNode : nodes) {
            double currentCost = Double.POSITIVE_INFINITY;
            for (Node potentialParent : nodes) {
                if (potentialParent.isDescendedOf(currentNode)) {
                    continue;
                }
                double newCost = getCost(potentialParent) + potentialParent.distanceTo(currentNode);

                if (newCost < currentCost && !collidesObstacle(potentialParent, currentNode)) {
                    currentNode.setParent(potentialParent);
                    currentCost = newCost;
                }
            }
        }
    }

    protected double getBestCost(){
        return bestCost;
    }
}