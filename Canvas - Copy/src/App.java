import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleSupplier;

import Canvas.Commands.Command;
import Canvas.Commands.CommandBase;
import Canvas.Commands.Commands;
import Canvas.Commands.ConditionalCommand;
import Canvas.Commands.InstantCommand;
import Canvas.Commands.TimedCommand;
import Canvas.Commands.Trigger;
import Canvas.FileUtil.FileWriter;
import Canvas.Inputs.KeyInput;
import Canvas.Inputs.MouseInput;
import Canvas.Inputs.MouseInput.MouseInputs;
import Canvas.Inputs.MouseInput.MouseSide;
import Canvas.Shapes.Circle;
import Canvas.Shapes.Line;
import Canvas.Shapes.Obj;
import Canvas.Shapes.Oval;
import Canvas.Shapes.PolyShape;
import Canvas.Shapes.Polygon;
import Canvas.Shapes.Rectangle;
import Canvas.Shapes.Square;
import Canvas.Shapes.Text;
import Canvas.Shapes.VisualJ;
import Canvas.Util.ColorEXT;
import Canvas.Util.Profile;
import Canvas.Util.Random;
import Canvas.Util.Vector2D;

public class App {
    public static VisualJ vis = null;
    public static MouseInput mouse = null;
    public static KeyInput keyboard = null;

    public static Profile profile = new Profile();
    public static Text graphicsFR = new Text(100, 120, 15, Color.RED, "");
    public static Text physicsFR = new Text(100, 140, 15, Color.RED, "");
    public static Text addingText = new Text(200, 200, 100, Color.RED, "");

    public static Vector2D mouseMarkCoords = Vector2D.of(0,0);
    public static Vector2D startMarkCoords = Vector2D.of(0,0);
    public static boolean hasReleased = true;

    public static void main(String[] args) {
        
        vis = new VisualJ("Simulation",1700,900,Color.black);
        mouse = new MouseInput(vis);
        keyboard = new KeyInput(vis);
        
        vis.startThread();
        defineShapes(vis);
        
        CommandBase runner = Commands.timed(()->runAll(vis), 10);
        runner.schedule();

        keyboard.keyPressed("Up").whileTrue(Commands.runOnce(()->{
            vis.moveFrame(0,5);
            System.out.println("HERE");
        }),10);
        keyboard.keyPressed("Left").whileTrue(Commands.runOnce(()->{
            vis.moveFrame(5,0);
            System.out.println("HERE");
        }),10);
        keyboard.keyPressed("Right").whileTrue(Commands.runOnce(()->{
            vis.moveFrame(-5,0);
            System.out.println("HERE");
        }),10);
        keyboard.keyPressed("Down").whileTrue(Commands.runOnce(()->{
            vis.moveFrame(0,-5);
            System.out.println("HERE");
        }),10);

        // Commands.timed(()->{
        //     vis.setFrame(mouse.getMouseCoords().x-vis.WIDTH/2, mouse.getMouseCoords().y-vis.HEIGHT/2);
        //     vis.setZoom(mouse.getMouseCoords().y/1000);
        // },10).schedule();

        mouse.addEvent(()->{
            hasReleased = true;
        },MouseInputs.MOUSE_RELEASED, MouseSide.LEFT);

        mouse.addEvent(()->{
            vis.setZoom(vis.getZoom()+mouse.getMouseWheelPosition()/50);
        }, MouseInputs.MOUSE_WHEEL_MOVED, MouseSide.LEFT);

        mouse.addEvent(()->{
            if (hasReleased){
                mouseMarkCoords = mouse.getMouseCoords();
                startMarkCoords = vis.getFrameMove();
                hasReleased = false;
            }
            vis.setFrame(startMarkCoords.x-(mouseMarkCoords.x-mouse.getMouseCoords().x)*1/vis.getZoom(),startMarkCoords.y-(mouseMarkCoords.y-mouse.getMouseCoords().y)*1/vis.getZoom());
            // vis.setZoom(mouse.getMouseCoords().y/1000);
        },MouseInputs.MOUSE_DRAGGED, MouseSide.LEFT);

        mouse.addEvent(()->keyboard.beginGatherAll(),MouseInputs.MOUSE_CLICKED,MouseSide.LEFT);
        mouse.addEvent(()->keyboard.endGatherAll(),MouseInputs.MOUSE_CLICKED,MouseSide.RIGHT);
    }

    public static void runAll(VisualJ vis){
        profile.start();
        ArrayList<Obj> shapes = vis.getObjArray();
        for (int i=0;i<shapes.size();i++){
            if (shapes.get(i)!=null){
                // shapes.get(i).rotate(shapes.get(i).getDegree()+1);
                addingText.setText(keyboard.getCurrentGather().get(0));
            }
        }
        profile.stop();
    }

    public static void defineShapes(VisualJ vis){
        
        for (int i=0;i<1;i++){
            int x = 0;//Random.randInt(0, vis.WIDTH);
            int y = 0;//Random.randInt(0, vis.HEIGHT);

            // List<Vector2D> list = List.of(Vector2D.of(-100,-100),Vector2D.of(100,100));
            // Line line = new Line(100, -0, list, Color.blue, 10);

            Circle circ1 = new Circle(100, 100, 5, Color.RED, true);
            Circle circ2 = new Circle(-100, -100, 5, Color.RED, true);
            Polygon polygon = new Polygon(0, 0, new double[]{0,0,50},new double[]{0,50,0}, Color.RED, false);

            PolyShape poly = new PolyShape(x, y, List.of(circ1,circ2,polygon));
            vis.add(poly);
            vis.add(new Circle(x, y, 500, Color.RED, false));
        }
        vis.add(addingText);
    }
}