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
import Canvas.Shapes.Circle;
import Canvas.Shapes.Line;
import Canvas.Shapes.Obj;
import Canvas.Shapes.Oval;
import Canvas.Shapes.Polygon;
import Canvas.Shapes.Rectangle;
import Canvas.Shapes.Square;
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
                shapes.get(i).rotate(1+shapes.get(i).getDegree());
            }
        }
        profile.stop();
    }
    public static void defineShapes(VisualJ vis){
        for (int i=0;i<10;i++){
            int x = Random.randInt(0, vis.WIDTH);
            int y = Random.randInt(0, vis.HEIGHT);
            int fx = Random.randInt(-300, 300);
            int fy = Random.randInt(-300, 300);
            int nx = Random.randInt(-300, 300);
            int ny = Random.randInt(-300, 300);
            vis.add(new Polygon(x, y, new double[]{0,fx,nx}, new double[]{0,fy,ny}, Color.blue, true));
            vis.add(new Circle(x+fx, y+fy, 5, Color.RED, true));
            vis.add(new Circle(x+nx, y+ny, 5, Color.RED, true));
            // vis.add(new Oval(x,y, 100,200,ColorEXT.getRandomColor(),true));
        }
    }
}