import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class App {
    //public static final VisualJ vis = new VisualJ("Simulation",1700,900,Color.black);
    //public static final VisualJ vis2 = new VisualJ("Simulation",1700,900,Color.black);
    public static final VisualJ vis3 = new VisualJ("Simulation",1700,900,Color.black);
    public static void main(String[] args) {
        //defineShapes(vis);
        //defineShapes(vis2);
        defineShapes(vis3);
        //vis.startGraphics();
        //vis2.startGraphics();
        vis3.startGraphics();
        // VisualJava vis2 = new VisualJava();
        // vis2.startGraphics();

        vis3.setArrSize(200);
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep((long) 10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //runAll(vis);
                //runAll(vis2);
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
                shapes[i].rotate(shapes[i].degrees2+1);
                shapes[i].rotPoint(vis.WIDTH/2,vis.HEIGHT/2,shapes[i].degrees2+1);
               
                //shapes[i].setPosition(Random.randInt(0,WIDTH),Random.randInt(0,HEIGHT));
                //shapes[i].setColor(ColorEXT.getColorBasedXY(shapes[i].xcoord, shapes[i].ycoord, WIDTH, HEIGHT));
               
            }
        }
        //repaint(0,0,WIDTH,HEIGHT);
    }
    public static void defineShapes(VisualJ vis){
        Obj[] shapes=vis.getObjArray();
        for (int i=0;i<shapes.length-1;i++){
            int rand1=Random.randInt(0, vis.WIDTH);
            int radn2=Random.randInt(0,vis.HEIGHT);
            int dist=(int)((Math.sqrt((Math.pow(vis.WIDTH/2-rand1,2))+(Math.pow(vis.HEIGHT/2-radn2,2))))/50);
            //shapes[i]=new Square(rand1,radn2,1+dist,ColorEXT.getRandomColor(),true);
            //shapes[i]=new Circle(rand1,radn2,1+dist,ColorEXT.getRandomColor(),true);
            //shapes[i]=new Polygon(rand1,radn2,new int[]{0,2+dist,(int)(1+dist/2)},new int[]{0,0,3+dist},ColorEXT.getRandomColor(), false);
            //shapes[i]=new Line(rand1,radn2,new int[]{0,2+dist},new int[]{0,3+dist},ColorEXT.getColorBasedXY(rand1,radn2,vis.WIDTH,vis.HEIGHT), 100+dist);
            
            shapes[i]=new Text(rand1,radn2,1+dist*10,ColorEXT.getRandomColor(),"Hi");//TEXT BROKEN
            Text txt=(Text)shapes[i];
            txt.setFont("Times New Roman",Font.ITALIC,1+dist*10);
            //shapes[i].customRotationCenter(WIDTH/2,HEIGHT/2);
        }
        // shapes[9999]=new Circle(100,100,100,ColorEXT.getRandomColor(),true);
        // Text txt=new Text(100, 100, 200, Color.blue, 20.0+"");
        // shapes[10000]=txt;
    }
}