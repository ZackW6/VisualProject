package Canvas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Scanner;

import javax.swing.SwingUtilities;
/**
 * Class for all user input actions
 */
public class UserInput {    
    public static String getNextLine() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        scanner.close();
        return line;
    }

    public static int getNextInt() {
        Scanner scanner = new Scanner(System.in);
        int line = scanner.nextInt();
        scanner.close();
        return line;
    }

    public static double getNextDouble() {
        Scanner scanner = new Scanner(System.in);
        double line = scanner.nextDouble();
        scanner.close();
        return line;
    }

    public void setupMouseInputs(VisualJ canvas){
        
    }
}


class MouseInputs{

    private VisualJ canvas;

    public int[] mouseCoords = new int[2];
    public double mouseWheel = 0;

    public MouseData mouseEntered;

    public MouseData mouseRightPressed;
    public MouseData mouseRightClicked;
    public MouseData mouseRightDragging;

    public MouseData mouseLeftPressed;
    public MouseData mouseLeftClicked;
    public MouseData mouseLeftDragging;


    public MouseInputs(VisualJ canvas){
        this.canvas = canvas;
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    mouseLeftPressed.setCoords(new int[]{e.getX(),e.getY()});
                    mouseLeftPressed.setIsTrue(true);
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    mouseRightPressed.setCoords(new int[]{e.getX(),e.getY()});
                    mouseRightPressed.setIsTrue(true);
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    mouseLeftPressed.setSecondCoords(new int[]{e.getX(),e.getY()});
                    mouseLeftPressed.setIsTrue(false);
                    mouseLeftClicked.setSecondCoords(new int[]{e.getX(),e.getY()});
                    mouseLeftClicked.setIsTrue(false);
                    mouseLeftDragging.setSecondCoords(new int[]{e.getX(),e.getY()});
                    mouseLeftDragging.setIsTrue(false);
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    mouseRightPressed.setSecondCoords(new int[]{e.getX(),e.getY()});
                    mouseRightPressed.setIsTrue(false);
                    mouseRightClicked.setSecondCoords(new int[]{e.getX(),e.getY()});
                    mouseRightClicked.setIsTrue(false);
                    mouseRightDragging.setSecondCoords(new int[]{e.getX(),e.getY()});
                    mouseRightDragging.setIsTrue(false);
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    mouseLeftClicked.setCoords(new int[]{e.getX(),e.getY()});
                    mouseLeftClicked.setIsTrue(true);
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    mouseRightPressed.setCoords(new int[]{e.getX(),e.getY()});
                    mouseRightPressed.setIsTrue(true);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseEntered.setCoords(new int[]{e.getX(),e.getY()});
                mouseEntered.setIsTrue(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                mouseEntered.setSecondCoords(new int[]{e.getX(),e.getY()});
                mouseEntered.setIsTrue(false);
            }
            @Override
            public void mouseWheelMoved(MouseWheelEvent e){
                mouseWheel=e.getPreciseWheelRotation();
            }
            @Override
            public void mouseDragged(MouseEvent e){
                if (SwingUtilities.isLeftMouseButton(e)) {
                    mouseLeftDragging.setCoords(new int[]{e.getX(),e.getY()});
                    mouseLeftDragging.setIsTrue(true);
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    mouseRightDragging.setCoords(new int[]{e.getX(),e.getY()});
                    mouseRightDragging.setIsTrue(true);
                }
            }
            @Override
            public void mouseMoved(MouseEvent e){
                mouseCoords[0] = e.getX();
                mouseCoords[1] = e.getY();
            }
        });
        this.canvas=canvas;
    }

    public int[] getMouseCoords() {
        return mouseCoords;
    }

    public MouseData getEntered(){
        return mouseEntered;
    }

    public MouseData getLeftPressed(){
        return mouseLeftPressed;
    }

    public MouseData getLeftClicked(){
        return mouseLeftClicked;
    }

    public MouseData getLeftDragging(){
        return mouseLeftDragging;
    }

    public MouseData getRightPressed(){
        return mouseRightPressed;
    }

    public MouseData getRightClicked(){
        return mouseRightClicked;
    }

    public MouseData getRightDragging(){
        return mouseRightDragging;
    }

    public double getMouseWheelPosition(){
        return mouseWheel;
    }
}
/**
 * Contains generalized data about where a last 
 * mouse event occured contained in Coords,
 * then where the event ended in SecondCoords,
 * finally if the event is happening or not in IsTrue.
 * 
 * @see UserInput
 */
class MouseData{
    private int[] coords = {-1,-1};
    private int[] coords2 = {-1,-1};
    private boolean isTrue = false;
    public void setCoords(int[] coords){
        this.coords = coords;
    }
    public void setSecondCoords(int[] coords){
        this.coords2 = coords;
    }
    public void setIsTrue(boolean isTrue){
        this.isTrue = isTrue;
    }
    public int[] getCoords(){
        return coords;
    }
    public int[] getSecondCoords(){
        return coords2;
    }
    public boolean getIsTrue(){
        return isTrue;
    }
}