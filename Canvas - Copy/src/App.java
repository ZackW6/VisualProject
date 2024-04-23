import java.awt.Color;
import java.util.ArrayList;

import Canvas.*;
import Canvas.MouseInput.MouseInputs;
import Canvas.MouseInput.MouseSide;
public class App {
    public static final VisualJ vis = new VisualJ("Simulation",1700,900,Color.black);
    public static final MouseInput mouse = new MouseInput(vis);
    public static void main(String[] args) {
        defineShapes(vis);
        vis.startThread();
       
        Command command=new Command(()->runAll(vis),7);
        
        Runnable run = ()->{
            vis.add(new Rectangle(mouse.getMouseCoords()[0],mouse.getMouseCoords()[1],100,100,ColorEXT.getRandomColor(), false));
        };
        mouse.addEvent(run, MouseInputs.MOUSE_PRESSED, MouseSide.LEFT);
        command.start();

    }
    public static void runAll(VisualJ vis){
        ArrayList<Obj> shapes = vis.getObjArray();
        for (int i=0;i<shapes.size();i++){
            if (shapes.get(i)!=null){
                int rand=Random.randInt(0, 5);
                shapes.get(i).rotate(shapes.get(i).getRotPointDegree()+rand);
                shapes.get(i).rotPoint(vis.WIDTH/2,vis.HEIGHT/2,shapes.get(i).getRotPointDegree()+rand);
                shapes.get(i).changeFill(false);
                // shapes[i].setPosition(Random.randInt(0,WIDTH),Random.randInt(0,HEIGHT));
                // shapes[i].setColor(ColorEXT.getColorBasedXY(shapes[i].xcoord, shapes[i].ycoord, WIDTH, HEIGHT));
               
            }
        }
        // repaint(0,0,WIDTH,HEIGHT);
    }
    public static void defineShapes(VisualJ vis){
        ArrayList<Obj> shapes = vis.getObjArray();
        for (int i=0;i<0;i++){
            int rand1=Random.randInt(0, vis.getBackgroundWidth());
            int radn2=Random.randInt(0,vis.getBackgroundHeight());
            int dist=(int)((Math.sqrt((Math.pow(vis.getBackgroundWidth()/2-rand1,2))+(Math.pow(vis.getBackgroundHeight()/2-radn2,2))))/50);
            //vis.add(new Square(rand1,radn2,1+dist,ColorEXT.getRandomColor(),true));
            
            //shapes[i]=new Circle(rand1,radn2,1+dist,ColorEXT.getRandomColor(),true);
            shapes.add(new Polygon(rand1,radn2,new int[]{0,2+dist,(int)(1+dist/2)},new int[]{0,0,3+dist},ColorEXT.getRandomColor(), false));
            // shapes.set(i,new Line(rand1,radn2,new int[]{30+dist,0},new int[]{0,10+dist},ColorEXT.getColorBasedXY(rand1,radn2,vis.getBackgroundWidth(),vis.getBackgroundHeight()), 1+dist));
            // Line line2=new Line(10,0,new int[]{30,0},new int[]{0,30},Color.cyan,100);
            // vis.add(line2);
            // shapes[i]=new Text(rand1,radn2,1+dist*10,ColorEXT.getRandomColor(),"Hi");//TEXT BROKEN
            // Text txt=(Text)shapes[i];
            // txt.setFont("Times New Roman",Font.ITALIC,1+dist*10);
            //shapes[i].rotPoint(vis.WIDTH/2,vis.HEIGHT/2,10);
        }
    }
}