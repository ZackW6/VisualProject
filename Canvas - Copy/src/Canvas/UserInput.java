package Canvas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class UserInput {
    private static double mouseX;
    private static double mouseY;

    private static boolean mouseClicked;

    private double[] lastMouseExitedCoords = new double[2];
    private double whenLastMouseExited;
    private double[] lastMouseEnteredCoords = new double[2];
    private double whenLastMouseEntered;

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
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        this.canvas=canvas;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    
}