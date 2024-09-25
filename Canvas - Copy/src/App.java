import java.awt.Color;
import java.awt.Font;
import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition;
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
import Canvas.Shapes.UserButtons.TypeButton;
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

    
    public static void main(String[] args) {
        
        vis = new VisualJ("Simulation",1700,900,Color.black);
        mouse = new MouseInput(vis);
        keyboard = new KeyInput(vis);
        
        vis.startThread();
        
        CommandBase runner = Commands.timed(()->runAll(vis), 10);
        runner.schedule();
        
        keyboard.keyPressed("Up").whileTrue(Commands.runOnce(()->{
            vis.moveFrame(0,-5/vis.getZoom());
        }),10);
        keyboard.keyPressed("Left").whileTrue(Commands.runOnce(()->{
            vis.moveFrame(5/vis.getZoom(),0);
        }),10);
        keyboard.keyPressed("Right").whileTrue(Commands.runOnce(()->{
            vis.moveFrame(-5/vis.getZoom(),0);
        }),10);
        keyboard.keyPressed("Down").whileTrue(Commands.runOnce(()->{
            vis.moveFrame(0,5/vis.getZoom());
        }),10);

        mouse.addEvent(()->{
            if (mouse.getMouseWheelPosition() < 0){
                vis.setZoom(vis.getZoom()/1.05);
                return;
            }
            vis.setZoom(vis.getZoom()*1.05);
        }, MouseInputs.MOUSE_WHEEL_MOVED, MouseSide.LEFT);

        defineShapes(vis);
    }

    public static void runAll(VisualJ vis){
        profile.start();
        ArrayList<Obj> shapes = vis.getObjArray();
        for (int i=0;i<shapes.size();i++){
            if (shapes.get(i)!=null){
                
            }
        }
        profile.stop();
    }

    public static void defineShapes(VisualJ vis){
        
    }
}