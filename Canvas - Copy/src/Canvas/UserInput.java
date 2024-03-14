package Canvas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class UserInput {
    private double[] mouseCoords = new double[2];

    private static boolean isMousePressed;
    private double[] lastMouseClickedCoords = new double[2];

    private double[] lastMousePressedCoords = new double[2];
    private double whenLastMousePressed;

    private double[] lastMouseReleasedCoords = new double[2];
    private double whenLastMouseReleased;

    private boolean mouseIsEntered = false;
    private double[] lastMouseExitedCoords = new double[2];
    private double whenLastMouseExited;

    private double[] lastMouseEnteredCoords = new double[2];
    private double whenLastMouseEntered;

    private boolean isMouseDragging = false;
    private double[] mouseDragCurrentCoords = new double[2];
    private double whenLastMouseDrag;

    private VisualJ canvas;
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
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                lastMouseEnteredCoords = new double[]{e.getX(), e.getY()};
                whenLastMouseEntered = canvas.getTimeStep();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lastMouseExitedCoords = new double[]{e.getX(), e.getY()};
                whenLastMouseExited = canvas.getTimeStep();
            }
            @Override
            public void mouseWheelMoved(MouseWheelEvent e){
                // e.();
            }
            @Override
            public void mouseDragged(MouseEvent e){
                if (SwingUtilities.isLeftMouseButton(e)) {
                    
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    
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

    public double getMouseX() {
        return mouseCoords[0];
    }

    public double getMouseY() {
        return mouseCoords[1];
    }

    
}