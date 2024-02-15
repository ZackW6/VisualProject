import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class App {
    public static void main(String[] args) {
        VisualJ vis = new VisualJ("Simulation",900,900,Color.black);
        vis.startGraphics();

        // VisualJava vis2 = new VisualJava();
        // vis2.startGraphics();
        Thread thread = new Thread(() -> {
            while (true) {
                sleep();
                runAll();
            }
        });
        thread.start();
    }
    public static void runAll(){
        System.out.println("HI");
    }
}
