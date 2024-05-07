import java.awt.Color;
import java.util.ArrayList;
import java.util.function.DoubleSupplier;

import Canvas.Commands.Command;
import Canvas.Commands.CommandBase;
import Canvas.Commands.InstantCommand;
import Canvas.Commands.Trigger;
import Canvas.Inputs.KeyInput;
import Canvas.Inputs.MouseInput;
import Canvas.Inputs.MouseInput.MouseInputs;
import Canvas.Inputs.MouseInput.MouseSide;
import Canvas.Shapes.Obj;
import Canvas.Shapes.Polygon;
import Canvas.Shapes.Rectangle;
import Canvas.Shapes.Text;
import Canvas.Shapes.VisualJ;
import Canvas.Shapes.PhysicsObjects.Arrow;
import Canvas.Shapes.PhysicsObjects.Particle;
import Canvas.Shapes.PhysicsObjects.StaticSquare;
import Canvas.Shapes.PhysicsObjects.Vector2D;
import Canvas.Util.ColorEXT;
import Canvas.Util.Profile;
import Canvas.Util.Random;
public class App {
    public static final VisualJ vis = new VisualJ("Simulation",1700,900,Color.black);
    public static final MouseInput mouse = new MouseInput(vis);
    public static final KeyInput keyboard = new KeyInput(vis);
    public static Profile profile = new Profile();
    public static Text graphicsFR = new Text(100, 120, 15, Color.RED, "");
    public static Text physicsFR = new Text(100, 140, 15, Color.RED, "");
    private static double physicsIterationTime = 0;

    private static StateMachine stateMachine;
    public static void main(String[] args) {
        stateMachine = new StateMachine(vis, mouse, keyboard);
        vis.startThread();
        // defineShapes(vis);
        
        // vis.add(graphicsFR);
        // vis.add(physicsFR);
        
        // Command command = new Command(()->runAll(vis),5);
        // Runnable run = ()->{
        //     vis.add(new StaticSquare(mouse.getMouseCoords()[0]-vis.getFrameMove()[0]-50,mouse.getMouseCoords()[1]-vis.getFrameMove()[1]-50,100,ColorEXT.getRandomColor(), false));
        // };
        // Runnable addFrameRateText = ()->{
        //     vis.add(physicsFR);
        //     vis.add(graphicsFR);
            
        // };
        // Runnable moveAdder = ()->{
        //     int x = 0;
        //     int y = 0;
        //     if (keyboard.isKeyPressed("Up").getAsBoolean()){
        //         y+=10;
        //     }
        //     if (keyboard.isKeyPressed("Down").getAsBoolean()){
        //         y-=10;
        //     }
        //     if (keyboard.isKeyPressed("Right").getAsBoolean()){
        //         x-=10;
        //     }
        //     if (keyboard.isKeyPressed("Left").getAsBoolean()){
        //         x+=10;
        //     }
        //     vis.moveFrame(x, y);
        // };
        // Command movement = new Command(moveAdder, 10);
        // movement.start();
        // keyboard.keyPressed("Space").onTrue(new InstantCommand(()->{
        //     command.whenCommandFinished(()->{
        //         vis.shapes.clear();
        //         StaticSquare.arr.clear();
        //         Particle.particleList.clear();
        //         command.start();
        //         addFrameRateText.run();
        //     });
        //     command.stop();
            
            
        // }));
        // keyboard.keyPressed("a").onTrue(run);
        // Runnable run2 = ()->{
        //     int rand = Random.randInt(5,20);
        //     Color col = new Color(0,rand*10,255);
        //     vis.add(new Particle(mouse.getMouseCoords()[0]-vis.getFrameMove()[0]-5,mouse.getMouseCoords()[1]-vis.getFrameMove()[1]-5, 10, col, true, rand, new double[]{1,1}, new double[]{0,.005}));
        // };
        // mouse.leftPressed().whileTrue(run2,3);
        // mouse.rightPressed().onTrue(()->vis.add(new Particle(mouse.getMouseCoords()[0]-vis.getFrameMove()[0]-10,mouse.getMouseCoords()[1]-vis.getFrameMove()[1]-10, 20, new Color(175,0,175), true, 100000, new double[]{20,20}, new double[]{0,.005})));
        // command.start();
    }

    public static void runAll(VisualJ vis){
        profile.start();
        
        ArrayList<Obj> shapes = vis.getObjArray();
        for (int i=0;i<shapes.size();i++){
            if (shapes.get(i)!=null){

                int rand=Random.randInt(0, 5);
                shapes.get(i).rotate(shapes.get(i).getRotPointDegree()+rand);
                shapes.get(i).rotPoint(vis.WIDTH/2,vis.HEIGHT/2,shapes.get(i).getRotPointDegree()+rand);
                shapes.get(i).changeFill(true);

                // shapes[i].setPosition(Random.randInt(0,WIDTH),Random.randInt(0,HEIGHT));
                // shapes[i].setColor(ColorEXT.getColorBasedXY(shapes[i].xcoord, shapes[i].ycoord, WIDTH, HEIGHT));
                
            }
        }
        Particle.insertionSort();
        physicsIterationTime = profile.getTime();
        physicsFR.setText("Physics iteration time: "+physicsIterationTime);
        graphicsFR.setText("Graphics iteration time: "+vis.getIterationTime());
        vis.moveIndex(graphicsFR,vis.shapes.size()-2);
        vis.moveIndex(physicsFR,vis.shapes.size()-1);
        profile.stop();
        // repaint(0,0,WIDTH,HEIGHT);
    }
    public static void defineShapes(VisualJ vis){
        ArrayList<Obj> shapes = vis.getObjArray();
        for (int i=0;i<1000;i++){
            int rand1=Random.randInt(0, vis.getBackgroundWidth());
            int radn2=Random.randInt(0,vis.getBackgroundHeight());
            int dist=(int)((Math.sqrt((Math.pow(vis.getBackgroundWidth()/2-rand1,2))+(Math.pow(vis.getBackgroundHeight()/2-radn2,2))))/50);
            //vis.add(new Square(rand1,radn2,1+dist,ColorEXT.getRandomColor(),true));
            
            //shapes[i]=new Circle(rand1,radn2,1+dist,ColorEXT.getRandomColor(),true);
            // shapes.add(new Polygon(rand1,radn2,new int[]{0,2+dist,(int)(1+dist/2)},new int[]{0,0,3+dist},ColorEXT.getRandomColor(), false));
            shapes.add(new Arrow(rand1,radn2,20,10,ColorEXT.getRandomColor(), true));
            // shapes.set(i,new Line(rand1,radn2,new int[]{30+dist,0},new int[]{0,10+dist},ColorEXT.getColorBasedXY(rand1,radn2,vis.getBackgroundWidth(),vis.getBackgroundHeight()), 1+dist));
            // Line line2=new Line(10,0,new int[]{30,0},new int[]{0,30},Color.cyan,100);
            // vis.add(line2);
            // shapes[i]=new Text(rand1,radn2,1+dist*10,ColorEXT.getRandomColor(),"Hi");//TEXT BROKEN
            // Text txt=(Text)shapes[i];
            // txt.setFont("Times New Roman",Font.ITALIC,1+dist*10);
            //shapes[i].rotPoint(vis.WIDTH/2,vis.HEIGHT/2,10);
        }
    }
}