import java.awt.Color;
import java.io.File;
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
import Canvas.RoboticsUtil.ClickableButton;
import Canvas.RoboticsUtil.ObstacleSquare;
import Canvas.RoboticsUtil.UIRobotGenerator;
import Canvas.RoboticsUtil.ClickableButton.Side;
import Canvas.RoboticsUtil.ClickableButton.Action;
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
    public static VisualJ vis = null;
    public static MouseInput mouse = null;
    public static KeyInput keyboard = null;

    public static Profile profile = new Profile();
    public static void main(String[] args) {
        
        vis = new VisualJ("Simulation",1700,900,new Color(20,20,20));
        mouse = new MouseInput(vis);
        keyboard = new KeyInput(vis);
        ObstacleSquare.createBackground(vis, "C:/GitHub/VisualProject/Canvas - Copy/src/Canvas/FileUtil", "background");
        UIRobotGenerator.generateObstacleFiles(vis,"C:/1561Examples/2024-MainRobot/src/main/deploy/pathplanner");
        
        mouse.addEvent(()->{
            int[] coords = mouse.getMouseCoords();
            ClickableButton.processAction(Action.Dragged, Side.Left, new Vector2D(coords[0],coords[1]));
        }, MouseInputs.MOUSE_DRAGGED, MouseSide.LEFT);

        mouse.addEvent(()->{
            int[] coords = mouse.getMouseCoords();
            ClickableButton.processAction(Action.Dragged, Side.Right, new Vector2D(coords[0],coords[1]));
        }, MouseInputs.MOUSE_DRAGGED, MouseSide.RIGHT);

        mouse.addEvent(()->{
            int[] coords = mouse.getMouseCoords();
            ClickableButton.processAction(Action.Pressed, Side.Left, new Vector2D(coords[0],coords[1]));
        }, MouseInputs.MOUSE_PRESSED, MouseSide.LEFT);

        mouse.addEvent(()->{
            int[] coords = mouse.getMouseCoords();
            ClickableButton.processAction(Action.Pressed, Side.Right, new Vector2D(coords[0],coords[1]));
        }, MouseInputs.MOUSE_PRESSED, MouseSide.RIGHT);

        vis.startThread();
        defineShapes(vis);
       
        CommandBase runner = Commands.timed(()->runAll(vis), 10);
        runner.schedule();

    }

    public static void runAll(VisualJ vis){
        profile.start();
        
        ArrayList<Obj> shapes = vis.getObjArray();
        for (int i=0;i<shapes.size();i++){
            if (shapes.get(i)!=null){
                // shapes.get(i).rotate(1+shapes.get(i).getDegree());
            }
        }
        profile.stop();
    }
    public static void defineShapes(VisualJ vis){
        
    }
}