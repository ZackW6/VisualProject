import java.awt.Color;
import java.util.ArrayList;

import javax.swing.plaf.nimbus.State;

import Canvas.Commands.Command;
import Canvas.Inputs.KeyInput;
import Canvas.Inputs.MouseInput;
import Canvas.Shapes.Circle;
import Canvas.Shapes.Line;
import Canvas.Shapes.Obj;
import Canvas.Shapes.Oval;
import Canvas.Shapes.Polygon;
import Canvas.Shapes.Rectangle;
import Canvas.Shapes.Square;
import Canvas.Shapes.Text;
import Canvas.Shapes.VisualJ;
import Canvas.Shapes.PhysicsObjects.Particle;
import Canvas.Shapes.PhysicsObjects.StaticSquare;
import Canvas.Util.ColorEXT;
import Canvas.Util.Profile;
import Canvas.Util.Random;
import Canvas.Commands.InstantCommand;
import Canvas.Commands.Trigger;


public class StateMachine {
    private final VisualJ vis;
    private final MouseInput mouse;
    private final KeyInput keyboard;

    private final Profile profile = new Profile();

    private Text graphicsFR = new Text(100, 120, 15, Color.RED, "");
    private Text physicsFR = new Text(100, 140, 15, Color.RED, "");
    private Text numObjects = new Text(100, 160, 15, Color.RED, "");
    private double physicsIterationTime = 0;

    private Command command;
    private Runnable run;
    private Runnable addFrameRateText;
    private Runnable moveAdder;
    private Command movement;

    private Trigger keyboardA;
    private Trigger keyboardSpace;
    private Trigger mouseLeft;
    private Trigger mouseRight;


    private int index = 0;

    public StateMachine(VisualJ vis, MouseInput mouse, KeyInput keyboard){
        mouseLeft = mouse.leftPressed().whileTrue(()->vis.add(new Square(0,0,200,Color.blue,true)),3);
        mouseRight = mouse.rightPressed().onTrue(()->{});
        keyboardA = keyboard.keyPressed("a").onTrue(()->{});
        this.vis = vis;
        this.mouse = mouse;
        this.keyboard = keyboard;
        keyboardSpace = keyboard.keyPressed("Space").onTrue(new InstantCommand(()->{
            moveState(1);
        }));
        keyboardSpace = keyboard.keyPressed("r").onTrue(new InstantCommand(()->{
            moveState(0);
        }));
        keyboardSpace = keyboard.keyPressed("b").onTrue(new InstantCommand(()->{
            moveState(-1);
        }));
        keyboardSpace = keyboard.keyPressed("Escape").onTrue(new InstantCommand(()->{
            System.exit(0);

        }));
        
        moveAdder = ()->{};
        run = ()->{};
        
        addFrameRateText = ()->{};
        command = new Command(()->runAllNone(vis),20);
        command.start();
        movement = new Command(moveAdder, 10);
        movement.start();
        setState(0);
    }
    public enum State{
        Stars(9),
        ArrowPhysics(8),
        Physics(7),
        BadPhysics(6),
        Input(5),
        SpinningTriangles(4),
        SpinningWords(3),
        SpinningCircles(2),
        SpinningSquares(1),
        Object(0);
        private int num;
        State(int num){
            this.num = num;
        }
        public int getIndex(){
            return num;
        }
        public static State getStateFromIndex(int index){
            for (int i = 0; i<State.values().length;i++){
                if (State.values()[i].getIndex() == index){
                    return State.values()[i];
                }
            }
            return Object;
        }
    }
    public void setState(State state){
        index = state.getIndex();
        System.out.println(state.getIndex());
        runState(State.getStateFromIndex(index));
    }
    public void setState(int index){
        this.index = index;
        runState(State.getStateFromIndex(index));
    }
    public void moveState(int stateMove){
        index += stateMove;
        index = index % State.values().length;
        if (index<0){
            index+=State.values().length;
        }
        runState(State.getStateFromIndex(index));
    }
    public State getState(){
        return State.getStateFromIndex(index);
    }
    public int getIndex(){
        return index;
    }
    private void runState(State state){
        switch (state) {
            case Object:
                objectCase();
                break;
            case SpinningSquares:
                spinningCase();
                break;
            case SpinningCircles:
                spinningCase();
                break;   
            case SpinningWords:
                spinningCase();
                break;
            case SpinningTriangles:
                spinningCase();
                break;
            case Input:
                inputCase();
                break;
            case Stars:
                starsCase();
                break;
            case BadPhysics:
                physicsCase();
                break;
            case Physics:
                physicsCase();
                break;
            case ArrowPhysics:
                physicsCase();
                break;
            default:
                break;
        }
    }
    private void objectCase(){
        moveAdder = ()->{};
        
        movement.setRunnable(moveAdder);
        command.setCommand(()->runAllNone(vis),20);
            Runnable andThen = ()->{

                vis.shapes.clear();
                StaticSquare.arr.clear();
                Particle.particleList.clear();
                
                command.start();
                mouseLeft.endAll();
                mouseRight.endAll();
                keyboardA.endAll();
                mouseLeft = mouse.leftPressed().whileTrue(()->{},3);
                addFrameRateText = ()->{
                    vis.add(physicsFR);
                    vis.add(graphicsFR);
                    vis.add(numObjects);
                };
                // addFrameRateText.run();
                defineShapesObject(vis);
                
            };
            command.whenCommandFinished(andThen);
            command.stop();

    }
    private void spinningCase(){
        moveAdder = ()->{};
        
        movement.setRunnable(moveAdder);
        command.setCommand(()->runAllSpin(vis),20);
            Runnable andThen = ()->{

                vis.shapes.clear();
                StaticSquare.arr.clear();
                Particle.particleList.clear();
                
                command.start();
                mouseLeft.endAll();
                mouseRight.endAll();
                keyboardA.endAll();
                mouseLeft = mouse.leftPressed().whileTrue(()->{},3);
                addFrameRateText = ()->{
                    vis.add(physicsFR);
                    vis.add(graphicsFR);
                    vis.add(numObjects);
                };
                addFrameRateText.run();
                defineShapesSpin(vis);
                
            };
            command.whenCommandFinished(andThen);
            command.stop();
            
    }
    private void inputCase(){
        moveAdder = ()->{
            int x = 0;
            int y = 0;
            if (keyboard.isKeyPressed("Up").getAsBoolean()){
                y+=10;
            }
            if (keyboard.isKeyPressed("Down").getAsBoolean()){
                y-=10;
            }
            if (keyboard.isKeyPressed("Right").getAsBoolean()){
                x-=10;
            }
            if (keyboard.isKeyPressed("Left").getAsBoolean()){
                x+=10;
            }
            vis.moveFrame(x, y);
        };
        
        movement.setRunnable(moveAdder);
        command.setCommand(()->runAllNone(vis),5);
            Runnable andThen = ()->{

                vis.shapes.clear();
                StaticSquare.arr.clear();
                Particle.particleList.clear();
                
                command.start();
                movement.start();
                mouseLeft.endAll();
                Runnable run2 = ()->{
                    Color col = ColorEXT.getRandomColor();
                    vis.add(new Circle(mouse.getMouseCoords()[0]-vis.getFrameMove()[0]-25,mouse.getMouseCoords()[1]-vis.getFrameMove()[1]-25, 50, col, true));
                };
                mouseLeft = mouse.leftPressed().whileTrue(run2,3);
                mouseRight.endAll();
                mouseRight = mouse.rightPressed().onTrue(()->vis.add(new Square(mouse.getMouseCoords()[0]-vis.getFrameMove()[0]-25,mouse.getMouseCoords()[1]-vis.getFrameMove()[1]-25, 50, ColorEXT.getRandomColor(), true)));
                keyboardA.endAll();
                keyboardA = keyboard.keyPressed("a").onTrue(()->vis.add(new Circle(mouse.getMouseCoords()[0]-vis.getFrameMove()[0]-50,mouse.getMouseCoords()[1]-vis.getFrameMove()[1]-50,100,ColorEXT.getRandomColor(), false)));
            };
            movement.stop();
            command.whenCommandFinished(andThen);
            command.stop();
    }
    private void physicsCase(){
        vis.setFrame(0,0);
            moveAdder = ()->{
                int x = 0;
                int y = 0;
                if (keyboard.isKeyPressed("Up").getAsBoolean()){
                    y+=10;
                }
                if (keyboard.isKeyPressed("Down").getAsBoolean()){
                    y-=10;
                }
                if (keyboard.isKeyPressed("Right").getAsBoolean()){
                    x-=10;
                }
                if (keyboard.isKeyPressed("Left").getAsBoolean()){
                    x+=10;
                }
                vis.moveFrame(x, y);
            };
            
            movement.setRunnable(moveAdder);
            command.setCommand(()->runAllPhysics(vis),5);
                Runnable andThen = ()->{

                    vis.shapes.clear();
                    StaticSquare.arr.clear();
                    Particle.particleList.clear();
                    
                    command.start();
                    mouseLeft.endAll();
                    Runnable run2 = ()->{
                        int rand = Random.randInt(5,20);
                        Color col = new Color(0,rand*10,255);
                        Particle part = new Particle(mouse.getMouseCoords()[0]-vis.getFrameMove()[0]-5,mouse.getMouseCoords()[1]-vis.getFrameMove()[1]-5, 10, col, true, rand, new double[]{1,1}, new double[]{0,.01},false);
                        part.add(vis);
                    };
                    if (State.getStateFromIndex(index)==State.ArrowPhysics){
                        run2 = ()->{
                            int rand = Random.randInt(5,20);
                            Color col = new Color(0,rand*10,255);
                            Particle part = new Particle(mouse.getMouseCoords()[0]-vis.getFrameMove()[0]-5,mouse.getMouseCoords()[1]-vis.getFrameMove()[1]-5, 10, col, true, rand, new double[]{1,1}, new double[]{0,.01},true);
                            part.add(vis);
                        };
                    }
                    mouseLeft = mouse.leftPressed().whileTrue(run2,3);
                    mouseRight.endAll();
                    Runnable mouseRightAction = ()->{
                        Particle part = new Particle(mouse.getMouseCoords()[0]-vis.getFrameMove()[0]-10,mouse.getMouseCoords()[1]-vis.getFrameMove()[1]-10, 20, new Color(175,0,175), true, 1000, new double[]{5,5}, new double[]{0,.01},false);
                        part.add(vis);
                    };
                    if (State.getStateFromIndex(index)==State.ArrowPhysics){
                        mouseRightAction = ()->{
                            Particle part = new Particle(mouse.getMouseCoords()[0]-vis.getFrameMove()[0]-10,mouse.getMouseCoords()[1]-vis.getFrameMove()[1]-10, 20, new Color(175,0,175), true, 1000, new double[]{5,5}, new double[]{0,.01},true);
                            part.add(vis);
                        };
                    }
                    mouseRight = mouse.rightPressed().onTrue(mouseRightAction);

                    keyboardA.endAll();
                    keyboardA = keyboard.keyPressed("a").onTrue(()->vis.add(new StaticSquare(mouse.getMouseCoords()[0]-vis.getFrameMove()[0]-50,mouse.getMouseCoords()[1]-vis.getFrameMove()[1]-50,100,ColorEXT.getRandomColor(), false)));
                    addFrameRateText = ()->{
                        vis.add(physicsFR);
                        vis.add(graphicsFR);
                        vis.add(numObjects);
                    };
                    
                    addFrameRateText.run();
                };
                command.whenCommandFinished(andThen);
                command.stop();
    }
    private void defineShapesObject(VisualJ vis){
        Color customColor = new Color(255, 0, 128);
        Rectangle rect=new Rectangle(50,50,100,200,customColor,true);
        Oval oval=new Oval(0,100,200,100,Color.blue,true);
        Square square=new Square(700,50,100,Color.green,true);  
        Circle circle=new Circle(500,300,100,Color.orange,false);
        Polygon polygon=new Polygon(500,50,new double[]{100,0,0,100},new double[]{0,0,300,300},Color.yellow,true);
        Line line=new Line(400,100,new double[]{0,200},new double[]{0,200},Color.cyan,1);
        Line line2=new Line(300,100,new double[]{30,0},new double[]{0,300},Color.cyan,10);
        Text text=new Text(100,450,30,Color.white,"Text");
        rect.move(100,100);
        vis.add(rect);
        oval.rotate(50);
        vis.add(oval);
        oval.setWidth(150);
        vis.add(square);
        vis.add(circle);
        circle.setSize(300);
        vis.add(polygon);
        polygon.addVertex(4,0,150);
        vis.add(line);
        vis.add(line2);
        text.setText("New Text");
        vis.add(text);
        text.setFont("Castellar", 0, 30);
    }
    
    
    private void runAllPhysics(VisualJ vis){
        profile.start();
        ArrayList<Obj> shapes = vis.getObjArray();
        for (int i=0;i<shapes.size();i++){
            if (shapes.get(i)!=null){

                if (shapes.get(i) instanceof Particle){
                    Particle particle = ((Particle) shapes.get(i));
                    particle.handleBorderCollision(0,vis.WIDTH,0,vis.HEIGHT,.7);
                    switch (State.getStateFromIndex(index)) {
                        case Physics:
                            particle.handleCircleCollision(1);
                            break;
                        case ArrowPhysics:
                             particle.handleCircleCollision(1);
                             break;
                        case BadPhysics:
                            particle.handleCircleCollisionOld(1);
                            break;
                        default:
                            break;
                    }
                    
                    particle.applyMovement();
                    ArrayList<StaticSquare> arr = StaticSquare.getArrayList();
                    for (int y = 0; y<arr.size();y++){
                        ((Particle) shapes.get(i)).handleSquareCollision(arr.get(y));
                    }
                    // System.out.println(((Particle) shapes.get(i)).getIndex());
                    // ((Particle) shapes.get(i)).setColor(new Color(0,((Particle) shapes.get(i)).getIndex(),0));
                    // ((Particle) shapes.get(i)).sortOne(i);
                    
                }
                // int rand=Random.randInt(0, 5);
                // shapes.get(i).rotate(shapes.get(i).getRotPointDegree()+rand);
                // shapes.get(i).rotPoint(vis.WIDTH/2,vis.HEIGHT/2,shapes.get(i).getRotPointDegree()+rand);
                // shapes.get(i).changeFill(true);


                // shapes[i].setPosition(Random.randInt(0,WIDTH),Random.randInt(0,HEIGHT));
                // shapes[i].setColor(ColorEXT.getColorBasedXY(shapes[i].xcoord, shapes[i].ycoord, WIDTH, HEIGHT));
                
            }
        }
        Particle.insertionSort();
        physicsIterationTime = profile.getTime();
        physicsFR.setText("Physics iteration time: "+physicsIterationTime);
        graphicsFR.setText("Graphics iteration time: "+vis.getIterationTime());
        numObjects.setText("Number of objects: "+vis.shapes.size());
        vis.moveIndex(graphicsFR,vis.shapes.size()-2);
        vis.moveIndex(physicsFR,vis.shapes.size()-1);
        vis.moveIndex(numObjects,vis.shapes.size()-3);
        profile.stop();
    }
    private void runAllSpin(VisualJ vis){
        profile.start();
        ArrayList<Obj> shapes = vis.getObjArray();
        for (int i=0;i<shapes.size();i++){
            
            if (shapes.get(i)!=null){
                
                
                // System.out.println("HERE");
                if (shapes.get(i) instanceof Text && ((Text)shapes.get(i)).getText().length()>6){

                }else{
                    int rand=Random.randInt(0, 5);
                    shapes.get(i).rotate(shapes.get(i).getRotPointDegree()+rand);
                    shapes.get(i).rotPoint(vis.WIDTH/2,vis.HEIGHT/2,shapes.get(i).getRotPointDegree()+rand);
                    shapes.get(i).changeFill(true);

                    // shapes.get(i).setPosition(Random.randInt(0,vis.WIDTH),Random.randInt(0,vis.HEIGHT));
                }
                
                
            }
        }
        physicsIterationTime = profile.getTime();
        physicsFR.setText("Physics iteration time: "+physicsIterationTime);
        graphicsFR.setText("Graphics iteration time: "+vis.getIterationTime());
        numObjects.setText("Number of objects: "+vis.shapes.size());
        vis.moveIndex(graphicsFR,vis.shapes.size()-2);
        vis.moveIndex(physicsFR,vis.shapes.size()-1);
        vis.moveIndex(numObjects,vis.shapes.size()-3);
        profile.stop();
    }
    private void runAllNone(VisualJ vis){

    }

    private void defineShapesSpin(VisualJ vis){
        int num = 10000;
        if (State.getStateFromIndex(index)==State.SpinningWords){
            num/=40;
        }
        for (int i=0;i<num;i++){

            int rand1=Random.randInt(0, vis.WIDTH);
            int radn2=Random.randInt(0,vis.HEIGHT);
            int dist=(int)((Math.sqrt((Math.pow(vis.getBackgroundWidth()/2-rand1,2))+(Math.pow(vis.getBackgroundHeight()/2-radn2,2))))/50);
            
            switch (State.getStateFromIndex(index)) {
                case SpinningCircles:
                    vis.add(new Circle(rand1,radn2,1+dist,ColorEXT.getColorBasedXY((int)vis.shapes.get(i).getCoords().x, (int)vis.shapes.get(i).getCoords().y, vis.WIDTH, vis.HEIGHT), false));
                    break;
                case SpinningSquares:
                    vis.add(new Square(rand1,radn2,1+dist,ColorEXT.getRandomColor(),true));
                    break;
                case SpinningTriangles:
                    vis.add(new Polygon(rand1,radn2,new int[]{0,2+dist,(int)(1+dist/2)},new int[]{0,0,3+dist},ColorEXT.getRandomColor(), false));
                    break;
                case SpinningWords:
                    vis.add(new Text(rand1,radn2,1+dist*10,ColorEXT.getRandomGrayScale(),"Hi"));
                    break;
                default:
                    break;
            }
        }
    }
    ArrayList<Double> time= new ArrayList<Double>();
    public void starsCase(){
        moveAdder = ()->{};
        
        movement.setRunnable(moveAdder);
        command.setCommand(()->runAllStars(vis),20);
            Runnable andThen = ()->{

                vis.shapes.clear();
                StaticSquare.arr.clear();
                Particle.particleList.clear();
                
                command.start();
                mouseLeft.endAll();
                mouseRight.endAll();
                keyboardA.endAll();
                mouseLeft = mouse.leftPressed().whileTrue(()->{},3);
                addFrameRateText = ()->{
                    vis.add(physicsFR);
                    vis.add(graphicsFR);
                    vis.add(numObjects);
                };
                // addFrameRateText.run();
                // defineShapesObject(vis);
                
            };
            command.whenCommandFinished(andThen);
            command.stop();
    }
    public void runAllStars(VisualJ vis){
        ArrayList<Obj> shapes = vis.shapes;
        for (int i=0;i<1000;i++){
            try {
                shapes.get(i);
            } catch (Exception e) {
                int rand1=Random.randInt(vis.WIDTH/2-10, vis.WIDTH/2+10);
                int radn2=Random.randInt(vis.HEIGHT/2-10,vis.HEIGHT/2+10);
                int dist=(int)((Math.sqrt((Math.pow(vis.WIDTH/2-rand1,2))+(Math.pow(vis.HEIGHT/2-radn2,2))))/50);
                shapes.add(new Circle(rand1,radn2,1+dist,Color.white,true));
                shapes.get(shapes.size()-1).rotPoint(vis.WIDTH/2,vis.HEIGHT/2,Random.randInt(0,360));
                shapes.get(shapes.size()-1).move(Random.randInt(0,vis.WIDTH),0);
                time.add((double)0);
            }
        }
        
        
        for (int i=0;i<1000;i++){
            if (shapes.get(i)!=null){
                time.set(i,.1+time.get(i));
                shapes.get(i).move(2+(int)(time.get(i)/2),0);
                ((Circle)shapes.get(i)).setSize(2+(int)(double)time.get(i));
                Color col;
                if (time.get(i)>245/20){
                    col=new Color(255,255,255);
                }else{
                    col=new Color((int)(double)time.get(i)*20+10,(int)(double)time.get(i)*20+10,(int)(double)time.get(i)*20+10);
                }
               
                shapes.get(i).setColor(col);
                shapes.get(i).rotPoint(vis.WIDTH/2,vis.HEIGHT/2,shapes.get(i).getRotPointDegree());
                if (shapes.get(i).getCoords().x>vis.WIDTH+100){
                    shapes.remove(i);
                    time.remove(i);
                } 
            }
        }
    }
}