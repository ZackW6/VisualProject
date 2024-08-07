package Canvas.Shapes.UserButtons;

import java.awt.Color;

import Canvas.Shapes.Rectangle;
import Canvas.Shapes.VisualJ;
import Canvas.Util.Vector2D;

public interface ClickableButton{

    public abstract void runOnClick();

    public abstract Vector2D getCoords();

    public abstract Vector2D getDimensions();

    public default boolean isClicked(Vector2D point, VisualJ vis){
        double zoom = vis.getZoom();
        Vector2D newPoint = (point.subtract(vis.getFrameMove())).multiply(zoom);
        Vector2D newPose = (this.getCoords()).multiply(zoom);
        Vector2D newDimensions = this.getDimensions();
        if (newPoint.x > newPose.x && newPoint.x < newPose.x + newDimensions.x && newPoint.y > newPose.y && newPoint.y < newPose.y + newDimensions.y){
            runOnClick();
            return true;
        }
        return false;
    }
    
}
