package Canvas.Pathing.RRT;

import Canvas.Pathing.RRT.RRTHelperBase.Field;
import Canvas.Shapes.PolyShape;
import Canvas.Shapes.VisualJ;
import Canvas.Util.KDTree;
import Canvas.Util.Vector2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BetterPreloadRRT implements RRTBase{


    ArrayList<Obstacle> staticObstacles = new ArrayList<>();
    HashMap<Obstacle, ArrayList<Node>[]> dynamicObstacles = new HashMap<>();

    KDTree<Obstacle> obstacles = new KDTree<>();

    HashMap<Node, ArrayList<Node>> nodes = new HashMap<>();
    
    PolyShape drawing = new PolyShape(0, 0);
    Field field;
    PolyShape path = null;

    Node start = new Node(850,450);
    Node goal = new Node(1100,650);

    VisualJ vis;

    /**
     * this list of obstacles should only be used for either loaded from a file, or something else, ask zack
     * @param vis
     * @param field
     * @param obstacles
     */
    public BetterPreloadRRT(VisualJ vis, Field field, List<Obstacle> obstacles){
        this.field = field;
        vis.add(drawing);
        this.vis = vis;
        setObstacles(obstacles);
    }

    private void compute() {
        if (!nodes.containsKey(goal) || !nodes.containsKey(start)){
            return;
        }

        for (Node node : nodes.get(start)){
            node.setParent(start);
        }

        for (int i = 0; i < 10; i++){
            nodes.forEach((node, visibleNodes) ->{
                double nodeCost = Double.POSITIVE_INFINITY;
                for (Node visable : visibleNodes){
                    if (!visable.isDescendedOf(start) || visable.equals(start)){
                        continue;
                    }
                    double visCost = visable.getCost();

                    if (node.isDescendedOf(this.start)){
                        nodeCost = node.getCost();
                    }

                    if (visCost + node.distanceTo(visable) > nodeCost){
                        continue;
                    }
                    
                    if (visable.isDescendedOf(node)){
                        continue;
                    }

                    node.setParent(visable);
                }
            });

                
        }

        if (path != null){
            drawing.remove(path);
        }

        if (!goal.isDescendedOf(start) || collidesObstacle(start,start)){
            return;
        }

        path = getPath(goal);
        drawing.add(path);
    }

    @Override
    public  void setStart(Vector2D start) {
        this.start.setPosition(start.x, start.y);
        nodes.get(this.start).clear();
        nodes.forEach((node, visibleNodes)->{
            if (!node.equals(this.start) && !collidesObstacle(node, this.start)){
                nodes.get(this.start).add(node);
                if (!visibleNodes.contains(this.start)){
                    visibleNodes.add(this.start);
                }
            }else{
                if (visibleNodes.contains(this.start)){
                    visibleNodes.remove(this.start);
                    node.setParent(null);
                }
            }
        });
        compute();
    }

    @Override
    public  void setGoal(Vector2D goal) {
        this.goal.setPosition(goal.x, goal.y);
        nodes.get(this.goal).clear();
        this.goal.setParent(null);
        nodes.forEach((node, visibleNodes)->{
            if (!node.equals(this.goal) && !collidesObstacle(node, this.goal)){
                if (!visibleNodes.contains(this.goal)){
                    visibleNodes.add(this.goal);
                    nodes.get(this.goal).add(node);
                }else{
                    nodes.get(this.goal).add(node);
                }
            }else{
                if (visibleNodes.contains(this.goal)){
                    visibleNodes.remove(this.goal);
                }
            }
        });
        compute();
    }

    /**
     * in this case it is a complete reset, but it is fast enough that if you are using a grid based obstacle system you should use this
     */
    @Override
    public  void setObstacles(List<Obstacle> obstacles) {
        staticObstacles.clear();
        nodes.clear();
        this.obstacles.clear();
        dynamicObstacles.clear();
        drawing.getArray().clear();
        
        path = null;
        staticObstacles = new ArrayList<>(simplifyObstacles(obstacles));
        drawing.getArray().addAll(staticObstacles);

        if (staticObstacles.isEmpty()){
            return;
        }

        Obstacle closest = staticObstacles.get(0);
        for (Obstacle obstacle : staticObstacles){
            if (obstacle.distanceTo(Vector2D.of(field.x()/2,field.y()/2)) < closest.distanceTo(Vector2D.of(field.x()/2,field.y()/2))){
                closest = obstacle;
            }
        }

        ArrayList<Obstacle> mixedObstacles = new ArrayList<>(staticObstacles);
        Collections.shuffle(mixedObstacles);

        mixedObstacles.remove(closest);
        this.obstacles.add(closest);
        this.obstacles.addAll(mixedObstacles);

        nodes.put(start, new ArrayList<>());

        nodes.put(goal, new ArrayList<>());
        goal.setParent(null);

        for (Obstacle obstacle : staticObstacles){
            List<Node> workableNodes = findCornerNodes(obstacle);
            for (Node node : workableNodes){
                nodes.put(node, new ArrayList<>());
            }
        }
        
        nodes.forEach((node, visibleNodes) ->{
            drawing.add(node.getCircle());
            nodes.forEach((visible, dontCare) ->{
                if (!node.equals(visible) && !collidesObstacle(node, visible)){
                    visibleNodes.add(visible);
                }
            });
        });

        compute();
    }



    @Override
    public void process() {
        // TODO Auto-generated method stub
    }

    @Override
    public void scheduleObstacles(List<Obstacle> obstacles) {
        setObstacles(obstacles);
    }

    @Override
    public void scheduleStart(Vector2D start) {
        setStart(start);
    }

    @Override
    public void scheduleGoal(Vector2D goal) {
        setGoal(goal);
    }

    @Override
    public void addObstacles(List<Obstacle> obstacles) {
        for (Obstacle obstacle : obstacles){
            addObstacle(obstacle);
        }
    }

    @SuppressWarnings("unchecked")
    private void addObstacle(Obstacle obstacle){
        obstacles.add(obstacle);
        ArrayList<Node>[] killedBrokenNodes = new ArrayList[2];
        ArrayList<Node> killedNodes = killedBrokenNodes[0];
        ArrayList<Node> brokenNodes = killedBrokenNodes[1];
        dynamicObstacles.put(obstacle, killedBrokenNodes);

        // for (Node node : nodes.keySet()){
        //     List<Node> toRemove = new ArrayList<>();

        //     if (obstacle.didCollide(node, node)){
        //         if (!node.equals(goal) && !node.equals(start)){
        //             nodes.remove(node);
        //             drawing.remove(node.getObj());
        //             dynamicObstacles.get(obstacle)[0].add(node);
        //         }
                
        //         for (Node nodeR : node.visibleNodes){
        //             nodeR.removeVisibleNode(node);
        //         }
        //         continue;
        //     }
        //     for (Node visible : node.visibleNodes){
        //         if (obstacle.didCollide(node, visible)){
        //             visible.visibleNodes.remove(node);
        //             toRemove.add(visible);
        //             node.setParent(null);
        //             visible.setParent(null);
        //         }
        //     }
        //     node.visibleNodes.removeAll(toRemove);
        // }
        // wholeObstacles.add(obstacle);
        // obstacles.add(obstacle);
        // drawing.add(obstacle);

        // List<Node> workingNodes = findCornerNodes(obstacle);

        // allNodes.addAll(workingNodes);
        // for (Node node : workingNodes){
        //     drawing.add(node.getCircle());
        //     node.getCircle().setColor(Color.GREEN);
        //     for (Node potentialVis : allNodes.toList()){
        //         if (!node.equals(potentialVis) && !collidesObstacle(node, potentialVis)){
        //             node.addVisibleNode(potentialVis);
        //             if (!workingNodes.contains(potentialVis)){
        //                 potentialVis.addVisibleNode(node);
        //             }
        //         }
        //     }
        // }

        // compute();
    }

    @Override
    public void removeObstacles(List<Obstacle> obstacles) {
        for (Obstacle obstacle : obstacles){
            removeObstacle((Obstacle)obstacle);
        }
    }

    private void removeObstacle(Obstacle obstacle){

        // drawing.remove(obstacle);
        // obstacles.remove(obstacle);
        // wholeObstacles.remove(obstacle);
        // allNodes.removeAll(obstacle.childNodes);
        

        // for (Node node : obstacle.childNodes){
        //     drawing.remove(node.getObj());
        //     for (Node nodeR : node.visibleNodes){
        //         nodeR.removeVisibleNode(node);
        //     }
        // }

        // ArrayList<Node> reAdd = new ArrayList<>();
        // for (Obstacle obstacle2 : obstacles.findKNearest(obstacle, 10)){
        //     for (Node node : obstacle2.childNodes){
        //         if (obstacles.search(node.parentObstacle) != null && !collidesObstacle(node, node)){
        //             reAdd.add(node);
        //             allNodes.add(node);
        //         }
        //     }
            
        // }

        // for (Node node : reAdd){
        //     drawing.add(node.getCircle());
        //     node.getCircle().setColor(Color.GREEN);
        //     for (Node potentialVis : allNodes.toList()){
        //         if (!node.equals(potentialVis) && !collidesObstacle(node, potentialVis)){
        //             node.addVisibleNode(potentialVis);
        //             if (!reAdd.contains(potentialVis)){
        //                 potentialVis.addVisibleNode(node);
        //             }
        //         }
        //     }
        // }

        // compute();
    }

    /**
     * useless here
     */
    @Override
    public void setMinimumDistance(double dist) {
        
    }

    /**
     * useless here
     */
    @Override
    public void setMaxStepDist(double dist) {
        
    }

    @Override
    public void setDrawingCoords(double x, double y) {
        drawing.setPosition(x,y);
    }

    /**
     * useless here
     */
    @Override
    public void setBias(double bias) {
        
    }

    @Override
    public Field getField() {
        return this.field;
    }

    @Override
    public List<Obstacle> getObstacles() {
        return obstacles.toList();
    }

    @Override
    public KDTree<Obstacle> getKDTreeObstacles(){
        return obstacles;
    }

    @Override
    public List<Node> getNodes() {
        return List.copyOf(nodes.keySet());
    }

    @Override
    public Vector2D getStart() {
        return start;
    }

    @Override
    public Vector2D getGoal() {
        return goal;
    }

    /**
     * useless in this
     */
    @Override
    public double getMinimumDist() {
        return 0;
    }

    /**
     * useless in this
     */
    @Override
    public double getMaxStepSize() {
        return 0;
    }

    @Override
    public Vector2D getDrawingCoords() {
        return drawing.getCoords();
    }

    /**
     * useless in this
     */
    @Override
    public double getBias() {
        return 0;
    }

    @Override
    public void delete() {
        this.drawing.getArray().clear();
        this.nodes.clear();
        obstacles.clear();
        dynamicObstacles.clear();
        staticObstacles.clear();
        path = null;
        start = new Node(850,450);
        goal = new Node(1100,650);
        vis.remove(drawing);
    }

    @Override
    public void prune(int max) {
        
    }
    
}
