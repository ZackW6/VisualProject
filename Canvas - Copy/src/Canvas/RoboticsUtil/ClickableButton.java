package Canvas.RoboticsUtil;

import java.awt.Color;
import java.util.ArrayList;

import Canvas.Shapes.Obj;
import Canvas.Shapes.Rectangle;
import Canvas.Shapes.VisualJ;
import Canvas.Shapes.PhysicsObjects.Vector2D;

public class ClickableButton extends Rectangle{

    private static ArrayList<ClickableButton> buttons = new ArrayList<ClickableButton>();

    private final ArrayList<ActionHolder> actionList = new ArrayList<>();

    public ClickableButton(double X, double Y, double Width, double Height, Color Color, boolean tf) {
        super(X, Y, Width, Height, Color, tf);
        buttons.add(this);
        //TODO Auto-generated constructor stub
    }

    public void deleteSelf(VisualJ vis){
        buttons.remove(this);
        vis.remove(this);
    }

    private class ActionHolder{
        public Action action;
        public Side side;
        public Runnable run;
        public ActionHolder(Action action, Side side, Runnable run){
            this.action = action;
            this.side = side;
            this.run = run;
        }
    }

    public ClickableButton whenAction(Action action, Side side, Runnable run){
        ActionHolder actionHolder = new ActionHolder(action, side, run);
        actionList.add(actionHolder);
        return this;
    }

    public enum Action{
        Pressed,
        Clicked,
        Dragged
    }

    public enum Side{
        Left,
        Right
    }

    public static void remove(Object object){
        buttons.remove(object);
    }

    public static void processAction(Action action, Side side, Vector2D where){
        for (int i = 0; i < buttons.size(); i++){
            if (buttons.get(i).coords.x<where.x && buttons.get(i).coords.x+buttons.get(i).width>where.x && buttons.get(i).coords.y<where.y && buttons.get(i).coords.y+buttons.get(i).length>where.y){
                for (ActionHolder actionHolder : buttons.get(i).actionList){
                    if (actionHolder.action == action && actionHolder.side == side){
                        actionHolder.run.run();
                    }
                }
            }
        }
    }
    
}
