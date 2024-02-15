import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class App {
    public static final VisualJ vis = new VisualJ("Simulation",900,900,Color.black);
    public static final VisualJ vis2 = new VisualJ("Simulation",900,900,Color.black);
    public static final VisualJ vis3 = new VisualJ("Simulation",900,900,Color.black);
    public static void main(String[] args) {
        vis.startGraphics();
        vis2.startGraphics();
        vis3.startGraphics();
        // VisualJava vis2 = new VisualJava();
        // vis2.startGraphics();
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep((long) 10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                runAll(vis);
                runAll(vis2);
                runAll(vis3);
            }
        });
        thread.start();
    }
    public static void runAll(VisualJ vis){
        Obj[] shapes = vis.getObjArray();
        for (int i=0;i<shapes.length;i++){
            if (shapes[i]!=null){
                int rand=Random.randInt(0, 5);
                shapes[i].rotate(shapes[i].degrees2+rand);
                shapes[i].rotPoint(vis.WIDTH/2,vis.HEIGHT/2,shapes[i].degrees2+rand);
               
                //shapes[i].setPosition(Random.randInt(0,WIDTH),Random.randInt(0,HEIGHT));
                //shapes[i].setColor(ColorEXT.getColorBasedXY(shapes[i].xcoord, shapes[i].ycoord, WIDTH, HEIGHT));
               
            }
        }
        //repaint(0,0,WIDTH,HEIGHT);
    }
}
