import java.awt.event.MouseAdapter;
import java.util.Scanner;

import org.w3c.dom.events.MouseEvent;

import Canvas.VisualJ;


public class UserInput {
    public UserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Hello, " + name + "!");
        scanner.close();
    }
    public static String getNextLine(){
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        scanner.close();
        return line;
    }
    public static double getNextInt(){
        Scanner scanner = new Scanner(System.in);
        int line = scanner.nextInt();
        scanner.close();
        return line;
    }
    public static double getNextDouble(){
        Scanner scanner = new Scanner(System.in);
        double line = scanner.nextDouble();
        scanner.close();
        return line;
    }
    public static void mouseClicked(VisualJ vis){
        vis.addMouseListener(new MouseAdapter() {
            // @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("HI");
            }
        });
    }

}