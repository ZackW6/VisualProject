package Canvas.RoboticsUtil;

import java.awt.Color;

import Canvas.Shapes.Rectangle;
import Canvas.Shapes.Text;
import Canvas.Shapes.VisualJ;

public class LabeledButton extends ClickableButton{

    private final Text label;

    public LabeledButton(double X, double Y, double Width, double Height, Color Color, boolean tf, String label, VisualJ vis) {
        super(X, Y, Width, Height, Color, tf);
        this.label = new Text((int)X+10, (int)Y+(int)Height/2+(int)Height/5, (int)Height/2, Color, label);
        vis.add(this.label);
        //TODO Auto-generated constructor stub
    }
    public String getName(){
        return label.getText();
    }

    @Override
    public void deleteSelf(VisualJ vis) {
        vis.remove(label);
        super.deleteSelf(vis);
    }

    @Override
    public void move(double moveX, double moveY) {
        // TODO Auto-generated method stub
        label.move(moveX, moveY);
        super.move(moveX, moveY);
    }
}
