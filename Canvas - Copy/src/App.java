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
import Canvas.Shapes.Line;
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
    public static void main(String[] args) {
        vis.startThread();
        defineShapes(vis);
        Command runner = new Command(()->runAll(vis), 10);
        runner.start();
    }

    public static void runAll(VisualJ vis){
        profile.start();
        
        ArrayList<Obj> shapes = vis.getObjArray();
        for (int i=0;i<shapes.size();i++){
            if (shapes.get(i)!=null){
                //TODO whatever function
            }
        }
        profile.stop();
    }
    public static void defineShapes(VisualJ vis){
        for (int i=0;i<1000;i++){
            int x = Random.randInt(0, vis.WIDTH);
            int y = Random.randInt(0, vis.HEIGHT);
            int randLengthX = Random.randInt(0, 20);
            int randLengthY = Random.randInt(0, 20);
            int randLengthX2 = Random.randInt(0, 20);
            int randLengthY2 = Random.randInt(0, 20);
            vis.add(new Line(x, y, new double[]{randLengthX,randLengthX2}, new double[]{randLengthY,randLengthY2}, Color.blue, 10));
        }
    }
}