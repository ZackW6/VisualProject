package Canvas.Pathing.RRT;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import Canvas.Pathing.RRT.PreloadRRT.ModifiedNode;
import Canvas.Pathing.RRT.PreloadRRT.ModifiedObstacle;
import Canvas.Pathing.RRT.RRTHelperBase.Field;
import Canvas.Shapes.Line;
import Canvas.Shapes.PolyShape;
import Canvas.Shapes.VisualJ;
import Canvas.Util.KDTree;
import Canvas.Util.Vector2D;

public class BetterPreloadRRT implements RRTBase{


    ArrayList<Obstacle> staticObstacles = new ArrayList<>();
    ArrayList<Obstacle> dynamicObstacles = new ArrayList<>();

    KDTree<Obstacle> obstacles = new KDTree<>();

    HashMap<Node, ArrayList<Node>> nodes = new HashMap<>();
    HashMap<Obstacle, ArrayList<Node[]>> brokenNodes = new HashMap<>();

    PolyShape drawing = new PolyShape(0, 0);
    Field field;
    PolyShape path = null;

    double bestCost = Double.POSITIVE_INFINITY;
    Node start = new Node(850,450);
    Node goal = new Node(1100,650);

    /**
     * this list of obstacles should only be used for either loaded from a file, or something else, ask zack
     * @param vis
     * @param field
     * @param obstacles
     */
    public BetterPreloadRRT(VisualJ vis, Field field, List<Obstacle> obstacles){
        this.field = field;
        vis.add(drawing);
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
            bestCost = Double.POSITIVE_INFINITY;
            return;
        }

        path = getPath(goal);
        drawing.add(path);
        bestCost = goal.getCost();
    }

    public List<Obstacle> simplifyObstacles(List<Obstacle> obstacles){
        if (obstacles.size() == 0){
            return List.of();
        }
        ArrayList<ArrayList<Obstacle>> obstacleFiles = new ArrayList<>();
        obstacleFiles.add(new ArrayList<>());
        for (Obstacle obstacle : obstacles){
            System.out.println(obstacle.getX());
            if (!obstacle.isObstacle()){
                continue;
            }
            ArrayList<Obstacle> recentObstacleFile = obstacleFiles.get(obstacleFiles.size()-1);
            if (recentObstacleFile.size() == 0){
                recentObstacleFile.add(obstacle);
                continue;
            }
            
            Obstacle newestObstacle = recentObstacleFile.get(recentObstacleFile.size()-1);
            
            if (newestObstacle.getY() == obstacle.getY() && Math.abs(newestObstacle.getX() + newestObstacle.getWidth()-obstacle.getX()) <.1 ){
                recentObstacleFile.add(obstacle);
            }else{
                ArrayList<Obstacle> tempList = new ArrayList<>();
                tempList.add(obstacle);
                obstacleFiles.add(tempList);
            }
        }
        System.out.println(obstacleFiles.size());

        ArrayList<Obstacle> newObstacles = new ArrayList<>();
        for (ArrayList<Obstacle> fileObstacles: obstacleFiles){
            if (fileObstacles.size() == 0){
                continue;
            }
            Obstacle beginObstacle = fileObstacles.get(0);
            Obstacle endObstacle = fileObstacles.get(fileObstacles.size()-1);
            newObstacles.add(new Obstacle(beginObstacle.getX()
            , beginObstacle.getY()
            , endObstacle.getX()+endObstacle.getWidth()
             - beginObstacle.getX(),beginObstacle.getHeight(), true));
            // drawing.add(newObstacles.get(newObstacles.size()-1));
        }

        ArrayList<Obstacle> finalObstacles = new ArrayList<>();
        obstacleFiles = new ArrayList<>();
        obstacleFiles.add(new ArrayList<>());
        newObstacles.sort(new Comparator<Obstacle>() {
            @Override
            public int compare(Obstacle item1, Obstacle item2) {
                if (item1.getX() == item2.getX()){
                    return Double.compare(item1.getY(), item2.getY());
                }
                return Double.compare(item1.getX(), item2.getX());
            }
        });
        for (Obstacle obstacle : newObstacles){
            ArrayList<Obstacle> recentObstacleFile = obstacleFiles.get(obstacleFiles.size()-1);
            if (recentObstacleFile.size() == 0){
                recentObstacleFile.add(obstacle);
                continue;
            }
            Obstacle newestObstacle = recentObstacleFile.get(recentObstacleFile.size()-1);
            
            if (newestObstacle.getX() == obstacle.getX()
                    && Math.abs(newestObstacle.getY() + newestObstacle.getHeight()-obstacle.getY()) <.1
                    && newestObstacle.getWidth() == obstacle.getWidth()){

                recentObstacleFile.add(obstacle);
            }else{
                ArrayList<Obstacle> tempList = new ArrayList<>();
                tempList.add(obstacle);
                obstacleFiles.add(tempList);
            }
        }

        for (ArrayList<Obstacle> fileObstacles: obstacleFiles){
            if (fileObstacles.size() == 0){
                continue;
            }
            Obstacle beginObstacle = fileObstacles.get(0);
            Obstacle endObstacle = fileObstacles.get(fileObstacles.size()-1);
            finalObstacles.add(new Obstacle(beginObstacle.getX()
            , beginObstacle.getY()
            , endObstacle.getWidth()
            , endObstacle.getY() - beginObstacle.getY() + beginObstacle.getHeight(), true));
            // drawing.add(finalObstacles.get(finalObstacles.size()-1));
        }
        return finalObstacles;
    }

    @Override
    public void setStart(Vector2D start) {
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
    public void setGoal(Vector2D goal) {
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
     * in this case it is a complete reset, so only use when changing the map completely
     */
    @Override
    public void setObstacles(List<Obstacle> obstacles) {
        
        staticObstacles.clear();
        nodes.clear();
        this.obstacles.clear();
        dynamicObstacles.clear();
        drawing.getArray().clear();
        path = null;
        staticObstacles = new ArrayList<>(simplifyObstacles(obstacles));
        System.out.println(staticObstacles.size());
        drawing.getArray().addAll(staticObstacles);

        if (staticObstacles.size() == 0){
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

        // if (!collidesObstacle(start, start)){
            nodes.put(start, new ArrayList<>());
        // }

        // if (!collidesObstacle(goal, goal)){
            nodes.put(goal, new ArrayList<>());
        // }

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
        // System.out.println(nodes.containsKey(goal));
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
        // TODO Auto-generated method stub
    }

    @Override
    public void removeObstacles(List<Obstacle> obstacles) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setMinimumDistance(double dist) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setMaxStepDist(double dist) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setDrawingCoords(double x, double y) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setBias(double bias) {
        // TODO Auto-generated method stub
    }

    @Override
    public Field getField() {
        return this.field;
    }

    @Override
    public List<Obstacle> getObstacles() {
        // TODO Auto-generated method stub
        return List.of();
    }

    @Override
    public KDTree<Obstacle> getKDTreeObstacles(){
        return obstacles;
    }

    @Override
    public List<Node> getNodes() {
        // TODO Auto-generated method stub
        return List.of();
    }

    @Override
    public Vector2D getStart() {
        return start;
    }

    @Override
    public Vector2D getGoal() {
        return goal;
    }

    @Override
    public double getMinimumDist() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getMaxStepSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Vector2D getDrawingCoords() {
        // TODO Auto-generated method stub
        return new Vector2D();
    }

    @Override
    public double getBias() {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
