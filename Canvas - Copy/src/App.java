import java.awt.Color;
import java.util.ArrayList;
import java.util.function.DoubleSupplier;

import Canvas.Commands.Command;
import Canvas.Inputs.KeyInput;
import Canvas.Inputs.MouseInput;
import Canvas.Inputs.MouseInput.MouseInputs;
import Canvas.Inputs.MouseInput.MouseSide;
import Canvas.PhysicsObjects.Particle;
import Canvas.Shapes.Obj;
import Canvas.Shapes.Polygon;
import Canvas.Shapes.Rectangle;
import Canvas.Shapes.Text;
import Canvas.Shapes.VisualJ;
import Canvas.Util.ColorEXT;
import Canvas.Util.Profile;
import Canvas.Util.Random;
public class App {
    public static final VisualJ vis = new VisualJ("Simulation",1700,900,Color.black);
    public static final MouseInput mouse = new MouseInput(vis);
    public static final KeyInput keyboard = new KeyInput(vis);
    public static Profile profile = new Profile();
    public static Text txt = new Text(100, 120, 20, Color.RED, "");
    public static void main(String[] args) {
        defineShapes(vis);
        vis.startThread();
        vis.add(txt);
        
        Command command = new Command(()->runAll(vis),20);
        Runnable run = ()->{
            vis.add(new Rectangle(mouse.getMouseCoords()[0]-vis.getFrameMove()[0],mouse.getMouseCoords()[1]-vis.getFrameMove()[1],100,100,ColorEXT.getRandomColor(), false));
        };
        Runnable moveAdder = ()->{
            int x = 0;
            int y = 0;
            if (keyboard.isKeyPressed("Up").getAsBoolean()){
                y+=10;
            }
            if (keyboard.isKeyPressed("Down").getAsBoolean()){
                y-=10;
            }
            if (keyboard.isKeyPressed("Right").getAsBoolean()){
                x-=10;
            }
            if (keyboard.isKeyPressed("Left").getAsBoolean()){
                x+=10;
            }
            vis.moveFrame(x, y);
        };
        Command movement = new Command(moveAdder, 10);
        movement.start();
        keyboard.keyPressed("a").onTrue(run).onFalse(run);
        // mouse.addEvent(run, MouseInputs.MOUSE_PRESSED, MouseSide.LEFT);
        Runnable run2 = ()->{
            int rand = Random.randInt(5,20);
            Color col = new Color(0,rand*10,255);
            vis.add(new Particle(mouse.getMouseCoords()[0]-vis.getFrameMove()[0],mouse.getMouseCoords()[1]-vis.getFrameMove()[1], 10, col, true, rand, new double[]{1,1}, new double[]{0,.5}));
        };
        mouse.leftPressed().whileTrue(run2,20);
        mouse.rightPressed().onTrue(()->vis.add(new Particle(mouse.getMouseCoords()[0]-vis.getFrameMove()[0],mouse.getMouseCoords()[1]-vis.getFrameMove()[1], 400, new Color(175,0,175), true, 1000, new double[]{.5,.5}, new double[]{0,.05})));
        command.start();
    }
    
    public static void runAll(VisualJ vis){
        profile.timeStart();
        
        ArrayList<Obj> shapes = vis.getObjArray();
        for (int i=0;i<shapes.size();i++){
            if (shapes.get(i)!=null){
                if (shapes.get(i).getClass().isAssignableFrom(Particle.class)){
                    ((Particle) shapes.get(i)).handleBorderCollision(0,vis.WIDTH,0,vis.HEIGHT,.7);
                    ((Particle) shapes.get(i)).handleCircleCollision(1);
                    ((Particle) shapes.get(i)).applyMovement();
                    // System.out.println(((Particle) shapes.get(i)).getIndex());
                    // ((Particle) shapes.get(i)).setColor(new Color(0,((Particle) shapes.get(i)).getIndex(),0));
                    // ((Particle) shapes.get(i)).sortOne(i);
                    
                }
                // int rand=Random.randInt(0, 5);
                // shapes.get(i).rotate(shapes.get(i).getRotPointDegree()+rand);
                // shapes.get(i).rotPoint(vis.WIDTH/2,vis.HEIGHT/2,shapes.get(i).getRotPointDegree()+rand);
                // shapes.get(i).changeFill(true);


                // shapes[i].setPosition(Random.randInt(0,WIDTH),Random.randInt(0,HEIGHT));
                // shapes[i].setColor(ColorEXT.getColorBasedXY(shapes[i].xcoord, shapes[i].ycoord, WIDTH, HEIGHT));
                
            }
        }
        Particle.insertionSort();
        profile.timeEnd();
        txt.setText(profile.getTime()+"");
        
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