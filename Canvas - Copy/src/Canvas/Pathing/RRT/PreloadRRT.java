package Canvas.Pathing.RRT;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Canvas.Pathing.RRT.RRTHelperBase.Field;
import Canvas.Shapes.Line;
import Canvas.Shapes.PolyShape;
import Canvas.Shapes.VisualJ;
import Canvas.Util.KDTree;
import Canvas.Util.Vector2D;

public class PreloadRRT implements RRTBase{

    KDTree<ModifiedNode> allNodes = new KDTree<ModifiedNode>();

    KDTree<Obstacle> obstacles = new KDTree<>();
    ArrayList<Obstacle> wholeObstacles = new ArrayList<>();

    ModifiedNode start = new ModifiedNode(850,450);
    ModifiedNode goal = new ModifiedNode(850,450);

    Field field;

    double bestCost = Double.POSITIVE_INFINITY;

    protected PolyShape drawing;

    protected PolyShape path = null;

    class ModifiedNode extends Node{

        ArrayList<ModifiedNode> visibleNodes = new ArrayList<>();
        Obstacle parentObstacle = null;
        public ModifiedNode(double x, double y) {
            super(x, y);
        }

        public ModifiedNode(Node node) {
            super(node.x, node.y);
        }

        public ModifiedNode(Vector2D vec) {
            super(vec.x, vec.y);
        }

        public void addVisibleNode(ModifiedNode node){
            visibleNodes.add(node);
        }

        public void removeVisibleNode(ModifiedNode node){
            visibleNodes.remove(node);
        }

    }

    public PreloadRRT(VisualJ vis, Field field, List<Obstacle> obstacles) {

        wholeObstacles = new ArrayList<>(obstacles);
        ArrayList<Obstacle> workingObstacles = new ArrayList<>();
        for (Obstacle obstacle : obstacles){
            if (obstacle.isObstacle()){
                workingObstacles.add(obstacle);
            }
        }
        this.field = field;
        this.drawing = new PolyShape(0, 0);
        vis.add(drawing);

        drawing.getArray().addAll(obstacles);
        this.obstacles.addAll(workingObstacles);

        if (!collidesObstacle(start, start)){
            allNodes.add(start);
        }

        if (!collidesObstacle(goal, goal)){
            allNodes.add(goal);
        }

        for (Obstacle obstacle : workingObstacles){
            List<ModifiedNode> workableNodes = findCornerNodes(obstacle);
            allNodes.addAll(workableNodes);
        }
        
        for (ModifiedNode node : allNodes.toList()){
            drawing.add(node.getCircle());
            for (ModifiedNode potentialVis : allNodes.toList()){
                if (!node.equals(potentialVis) && !collidesObstacle(node, potentialVis)){
                    node.addVisibleNode(potentialVis);
                }
            }
        }

        compute();
    }

    public PreloadRRT(VisualJ vis, Field field, List<Obstacle> obstacles, ArrayList<ModifiedNode> solvedNodes) {
        //TODO something here
    }

    public void process(){
        // if (paths.size()>0){
        //     drawing.remove(paths.get(0));
        //     paths.clear();
        // }

        // paths.add(getPath(goal));
        // drawing.add(paths.get(0));
    }

    public List<ModifiedNode> findCornerNodes(Obstacle obstacle){
        Vector2D corner1 = obstacle.getCoords();
        Vector2D corner2 = new Vector2D(corner1.x + obstacle.getWidth(), corner1.y);
        Vector2D corner3 = new Vector2D(corner1.x, corner1.y + obstacle.getHeight());
        Vector2D corner4 = new Vector2D(corner1.x + obstacle.getWidth(), corner1.y + obstacle.getHeight());
        ArrayList<ModifiedNode> workingNodes = new ArrayList<>();

        if (!collidesObstacle(new Node(corner1.add(Vector2D.of(-.001,-.001))), new Node(corner1.add(Vector2D.of(-.001,-.001))))){
            ModifiedNode node = new ModifiedNode(corner1.add(Vector2D.of(-.001,-.001)));
            node.parentObstacle = obstacle;
            workingNodes.add(node);
        }
        if (!collidesObstacle(new Node(corner2.add(Vector2D.of(.001,-.001))), new Node(corner2.add(Vector2D.of(.001,-.001))))){
            ModifiedNode node = new ModifiedNode(corner2.add(Vector2D.of(.001,-.001)));
            node.parentObstacle = obstacle;
            workingNodes.add(node);
        }
        if (!collidesObstacle(new Node(corner3.add(Vector2D.of(-.001,.001))), new Node(corner3.add(Vector2D.of(-.001,.001))))){
            ModifiedNode node = new ModifiedNode(corner3.add(Vector2D.of(-.001,.001)));
            node.parentObstacle = obstacle;
            workingNodes.add(node);
        }
        if (!collidesObstacle(new Node(corner4.add(Vector2D.of(.001,.001))), new Node(corner4.add(Vector2D.of(.001,.001))))){
            ModifiedNode node = new ModifiedNode(corner4.add(Vector2D.of(.001,.001)));
            node.parentObstacle = obstacle;
            workingNodes.add(node);
        }
        return workingNodes;

    }

    private void compute(){
        if (!allNodes.toList().contains(goal) || !allNodes.toList().contains(start)){
            return;
        }

        for (ModifiedNode node : start.visibleNodes){
            node.setParent(start);
        }

        for (int i = 0; i < 10; i++){
            for (ModifiedNode node : allNodes.toList()){

                double nodeCost = Double.POSITIVE_INFINITY;
                for (ModifiedNode visable : node.visibleNodes){
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
                
            }
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

    protected boolean collidesObstacle(Node point) {
        if (point.getParent() == null){
            System.out.println("INCORRECT USE OF OBSTACLES, CHECK RRTHelperBASE 114");
            return true;
        }

        if (obstacles.getRoot() == null){
            return false;
        }

        double[] obstacleDimensions = new double[]{0,0};
        obstacles.traverseNodes(obstacle -> {
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
        
        for (Obstacle obstacle : obstacles.findInRange(
            Vector2D.of(lowerBound.x - obstacleDimensions[0]
            , lowerBound.y - obstacleDimensions[1]), Vector2D.of(upperBound.x+obstacleDimensions[0],upperBound.y+obstacleDimensions[1]))) {
            if (obstacle.didCollide(point, point.getParent())){
                return true;
            }
        }
        return false;
    }

    protected boolean collidesObstacle(Node one, Node two) {
        return collidesObstacle(new Node(one, two));
    }

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

    /**
     * in this case it is a complete reset, so only use when changing the map completely
     */
    @Override
    public void setObstacles(List<Obstacle> obstacles) {
        wholeObstacles = new ArrayList<>(obstacles);
        drawing.getArray().clear();
        allNodes.clear();
        this.obstacles.clear();
        ArrayList<Obstacle> workingObstacles = new ArrayList<>();
        for (Obstacle obstacle : obstacles){
            if (obstacle.isObstacle()){
                workingObstacles.add(obstacle);
            }
        }
        drawing.getArray().addAll(obstacles);
        this.obstacles.addAll(workingObstacles);

        // if (!collidesObstacle(start, start)){
            allNodes.add(start);
        // }

        // if (!collidesObstacle(goal, goal)){
            allNodes.add(goal);
        // }

        for (Obstacle obstacle : workingObstacles){
            List<ModifiedNode> workableNodes = findCornerNodes(obstacle);
            allNodes.addAll(workableNodes);
        }

        for (ModifiedNode node : allNodes.toList()){
            drawing.add(node.getCircle());
            for (ModifiedNode potentialVis : allNodes.toList()){
                if (!node.equals(potentialVis) && !collidesObstacle(node, potentialVis)){
                    node.addVisibleNode(potentialVis);
                }
            }
        }

        compute();
    }

    @Override
    public void setStart(Vector2D start) {
        this.start.setPosition(start.x, start.y);
        this.start.visibleNodes.clear();
        for (ModifiedNode node : allNodes.toList()){
            if (!node.equals(this.start) && !collidesObstacle(node, this.start)){
                if (!node.visibleNodes.contains(this.start)){
                    node.visibleNodes.add(this.start);
                    this.start.visibleNodes.add(node);
                }else{
                    this.start.visibleNodes.add(node);
                }
            }else{
                if (node.visibleNodes.contains(this.start)){
                    node.visibleNodes.remove(this.start);
                    node.setParent(null);
                }
            }
        }
        compute();
    }

    @Override
    public void addObstacles(List<Obstacle> obstacles) {
        for (Obstacle obstacle : obstacles){
            addObstacle(obstacle);
        }
    }

    private void addObstacle(Obstacle obstacle){
        for (ModifiedNode node : allNodes.toList()){
            List<ModifiedNode> toRemove = new ArrayList<>();

            if (obstacle.didCollide(node, node)){
                if (!node.equals(goal) && !node.equals(start)){
                    allNodes.remove(node);
                    drawing.remove(node.getObj());
                }
                
                for (ModifiedNode nodeR : node.visibleNodes){
                    nodeR.removeVisibleNode(node);
                }
                continue;
            }
            for (ModifiedNode visible : node.visibleNodes){
                if (obstacle.didCollide(node, visible)){
                    visible.visibleNodes.remove(node);
                    toRemove.add(visible);
                    node.setParent(null);
                    visible.setParent(null);
                }
            }
            node.visibleNodes.removeAll(toRemove);
            
        }
        wholeObstacles.add(obstacle);
        obstacles.add(obstacle);
        drawing.add(obstacle);

        List<ModifiedNode> workingNodes = findCornerNodes(obstacle);
        allNodes.addAll(workingNodes);
        for (ModifiedNode node : workingNodes){
            drawing.add(node.getCircle());
            node.getCircle().setColor(Color.GREEN);
            for (ModifiedNode potentialVis : allNodes.toList()){
                if (!node.equals(potentialVis) && !collidesObstacle(node, potentialVis)){
                    node.addVisibleNode(potentialVis);
                    if (!workingNodes.contains(potentialVis)){
                        potentialVis.addVisibleNode(node);
                    }
                }
            }
        }

        compute();
    }

    @Override
    public void removeObstacles(List<Obstacle> obstacles) {
        for (Obstacle obstacle : obstacles){
            removeObstacle(obstacle);
        }
    }

    private void removeObstacle(Obstacle obstacle){
        // for (ModifiedNode node : allNodes.toList()){
        //     List<ModifiedNode> toRemove = new ArrayList<>();

        //     if (obstacle.didCollide(node, node)){
        //         if (!node.equals(goal) && !node.equals(start)){
        //             allNodes.remove(node);
        //             drawing.remove(node.getObj());
        //         }
                
        //         for (ModifiedNode nodeR : node.visibleNodes){
        //             nodeR.removeVisibleNode(node);
        //         }
        //         continue;
        //     }
        //     for (ModifiedNode visible : node.visibleNodes){
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

        // List<ModifiedNode> workingNodes = findCornerNodes(obstacle);
        // allNodes.addAll(workingNodes);
        // for (ModifiedNode node : workingNodes){
        //     drawing.add(node.getCircle());
        //     node.getCircle().setColor(Color.GREEN);
        //     for (ModifiedNode potentialVis : allNodes.toList()){
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
    public void setGoal(Vector2D goal) {
        this.goal.setPosition(goal.x, goal.y);
        this.goal.visibleNodes.clear();
        this.goal.setParent(null);
        for (ModifiedNode node : allNodes.toList()){
            if (!node.equals(this.goal) && !collidesObstacle(node, this.goal)){
                if (!node.visibleNodes.contains(this.goal)){
                    node.visibleNodes.add(this.goal);
                    this.goal.visibleNodes.add(node);
                }else{
                    this.goal.visibleNodes.add(node);
                }
            }else{
                if (node.visibleNodes.contains(this.goal)){
                    node.visibleNodes.remove(this.goal);
                }
            }
        }
        compute();
    }

    /**
     * unused
     */
    @Override
    public void setMinimumDistance(double dist) {
        
    }

    /**
     * unused
     */
    @Override
    public void setMaxStepDist(double dist) {
        
    }

    @Override
    public void setDrawingCoords(double x, double y) {
        drawing.setPosition(x, y);
    }

    /**
     * unused
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

    /**
     * dont use until fixed
     */
    @Override
    public List<Node> getNodes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNodes'");
    }

    @Override
    public Vector2D getStart() {
        return this.start;
    }

    @Override
    public Vector2D getGoal() {
        return this.goal;
    }

    /**
     * unused
     */
    @Override
    public double getMinimumDist() {
        return 0;
    }

    /**
     * unused
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
     * unused
     */
    @Override
    public double getBias() {
        return 0;
    }
    
}
