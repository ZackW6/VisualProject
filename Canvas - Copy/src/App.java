import java.awt.Color;
import Canvas.*;
public class App {
    public static final VisualJ vis = new VisualJ("Simulation",1700,900,Color.black);

    public static void main(String[] args) {
        vis.setArrSize(10000);
        defineShapes(vis);
        vis.startThread();
        // defineShapes(vis);
       
        Command command=new Command(()->runAll(vis),10);

        command.start();

    }
    public static void runAll(VisualJ vis){
        
        Obj[] shapes = vis.getObjArray();
        for (int i=0;i<shapes.length;i++){
            if (shapes[i]!=null){
                int rand=Random.randInt(0, 5);
                shapes[i].rotate(shapes[i].getRotPointDegree()+rand);
                shapes[i].rotPoint(vis.WIDTH/2,vis.HEIGHT/2,shapes[i].getRotPointDegree()+rand);
                Polygoni sqr=(Polygoni)shapes[i];
                sqr.changeFill(false);
                shapes[i]=sqr;
                // UserInput.mouseClicked(vis);
                // shapes[i].setPosition(Random.randInt(0,WIDTH),Random.randInt(0,HEIGHT));
                // shapes[i].setColor(ColorEXT.getColorBasedXY(shapes[i].xcoord, shapes[i].ycoord, WIDTH, HEIGHT));
               
            }
        }
        // repaint(0,0,WIDTH,HEIGHT);
    }
    public static void defineShapes(VisualJ vis){
        Obj[] shapes=vis.getObjArray();
        for (int i=0;i<shapes.length-1;i++){
            int rand1=Random.randInt(0, vis.getBackgroundWidth());
            int radn2=Random.randInt(0,vis.getBackgroundHeight());
            int dist=(int)((Math.sqrt((Math.pow(vis.getBackgroundWidth()/2-rand1,2))+(Math.pow(vis.getBackgroundHeight()/2-radn2,2))))/50);
            //vis.add(new Square(rand1,radn2,1+dist,ColorEXT.getRandomColor(),true));
            
            //shapes[i]=new Circle(rand1,radn2,1+dist,ColorEXT.getRandomColor(),true);
            //shapes[i]=new Polygon(rand1,radn2,new int[]{0,2+dist,(int)(1+dist/2)},new int[]{0,0,3+dist},ColorEXT.getRandomColor(), false);
            shapes[i]=new Line(rand1,radn2,new int[]{30+dist,0},new int[]{0,10+dist},ColorEXT.getColorBasedXY(rand1,radn2,vis.getBackgroundWidth(),vis.getBackgroundHeight()), 1+dist);
            // Line line2=new Line(10,0,new int[]{30,0},new int[]{0,30},Color.cyan,100);
            // vis.add(line2);
            // shapes[i]=new Text(rand1,radn2,1+dist*10,ColorEXT.getRandomColor(),"Hi");//TEXT BROKEN
            // Text txt=(Text)shapes[i];
            // txt.setFont("Times New Roman",Font.ITALIC,1+dist*10);
            //shapes[i].rotPoint(vis.WIDTH/2,vis.HEIGHT/2,10);
        }
    }
}