//BASE APP
/*import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends VisualJ{
    final static int WIDTH=1700;
    final static int HEIGHT=900;
    static int count=0;
    public App(){
        VisualJ.setArrSize(10000);
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runAll();
                count++;
            }
        });
        timer.start();
        createWorld("Simulation", WIDTH, HEIGHT, Color.black);
    }
    
    private void runAll(){
        Obj[] arr=VisualJ.getObjArray();

        repaint(0,0,WIDTH,HEIGHT);
        if (count%10==0 && count>5){
            System.out.println(Profile.getAverage()+" (cur avg)");
        }
    }
    public static void main(String[] args) {
        run();
    }
}*/

//STARS FLYING BY
/*import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class App extends VisualJ{
    final static int WIDTH=1700;
    final static int HEIGHT=850;
    int addin=0;
    public App(){
        setArrSize(10000);
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runAll();
            }
        });
        timer.start();
        createWorld("Simulation", WIDTH, HEIGHT, Color.black);
    }
    double[] time=new double[1000000];
    private void runAll(){
        if (true){
            for (int y=0;y<15;y++){
                for (int i=0;i<shapes.length;i++){
                    if (shapes[i]==null){
                        int rand1=Random.randInt(WIDTH/2-10, WIDTH/2+10);
                        int radn2=Random.randInt(HEIGHT/2-10,HEIGHT/2+10);
                        int dist=(int)((Math.sqrt((Math.pow(WIDTH/2-rand1,2))+(Math.pow(HEIGHT/2-radn2,2))))/50);
                        shapes[i]=new Circle(rand1,radn2,1+dist,Color.white,true);
                        shapes[i].rotPoint(WIDTH/2,HEIGHT/2,Random.randInt(0,360));
                        shapes[i].move(Random.randInt(0,getWidth()),0);
                        i+=shapes.length;
                        time[i]=0;
                    }
                }
            }
        }
        for (int i=0;i<shapes.length;i++){
            if (shapes[i]!=null){
                time[i]+=.1;
                int dist=(int)((Math.sqrt((Math.pow(WIDTH/2-shapes[i].xcoord,2))+(Math.pow(HEIGHT/2-shapes[i].ycoord,2))))/50);
                int rand=Random.randInt(1,5);
                shapes[i].move(2+(int)(time[i]/2),0);
                shapes[i].setSize(2+(int)time[i]);
                Color col;
                if (time[i]>245/20){
                    col=new Color(255,255,255);
                }else{
                    col=new Color((int)time[i]*20+10,(int)time[i]*20+10,(int)time[i]*20+10);
                }
               
                shapes[i].setColor(col);
                shapes[i].rotPoint(WIDTH/2,HEIGHT/2,shapes[i].degrees2);
                if (shapes[i].xcoord>WIDTH+100){
                    shapes[i]=null;
                    time[i]=0;
                } 
            }
        }


       
        repaint(0,0,WIDTH,HEIGHT);
       
        addin++;
        if (addin%10==0){
            System.out.println(Profile.getAverage()+" Cur Average");
        }
    }
    public static void main(String[] args) {
        run();
    }
}*/

//SHAPES SPINNING
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadFactory;


public class VisualJava extends VisualJ {
    final int WIDTH=1700;
    final int HEIGHT=900;
    int addin=0;
    public VisualJava(){
        SwingUtilities.invokeLater(() -> setVisible(true));
        setArrSize(10001);
        Timer timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runAll();
            }
        });
        timer.start();


        // Perform the operation you want to measure




        // Stop the timer
        createWorld("Simulation", WIDTH, HEIGHT, Color.black);
        for (int i=0;i<shapes.length-1;i++){
            int rand1=Random.randInt(0, WIDTH);
            int radn2=Random.randInt(0,HEIGHT);
            int dist=(int)((Math.sqrt((Math.pow(WIDTH/2-rand1,2))+(Math.pow(HEIGHT/2-radn2,2))))/50);
            shapes[i]=new Square(rand1,radn2,1+dist,ColorEXT.getRandomColor(),true);
            //shapes[i]=new Circle(rand1,radn2,1+dist,ColorEXT.getRandomColor(),true);
            //shapes[i]=new Polygon(rand1,radn2,new int[]{0,2+dist,(int)(1+dist/2)},new int[]{0,0,3+dist},ColorEXT.getRandomColor(), false);
            //shapes[i]=new Line(rand1,radn2,new int[]{0,2+dist},new int[]{0,3+dist},ColorEXT.getColorBasedXY(rand1,radn2,WIDTH,HEIGHT), 1+dist);
            //shapes[i]=new Text(rand1,radn2,1+dist*10,ColorEXT.getRandomColor(),"HI");//TEXT BROKEN
            //shapes[i].customRotationCenter(WIDTH/2,HEIGHT/2);
        }
        // shapes[9999]=new Circle(100,100,100,ColorEXT.getRandomColor(),true);
        // Text txt=new Text(100, 100, 200, Color.blue, 20.0+"");
        // shapes[10000]=txt;


    }
    private void runAll(){
        for (int i=0;i<shapes.length;i++){
            if (shapes[i]!=null){
                int rand=Random.randInt(0, 5);
                shapes[i].rotate(shapes[i].degrees2+rand);
                shapes[i].rotPoint(WIDTH/2,HEIGHT/2,shapes[i].degrees2+rand);
               
                //shapes[i].setPosition(Random.randInt(0,WIDTH),Random.randInt(0,HEIGHT));
                //shapes[i].setColor(ColorEXT.getColorBasedXY(shapes[i].xcoord, shapes[i].ycoord, WIDTH, HEIGHT));
               
            }
        }
        //repaint(0,0,WIDTH,HEIGHT);
       
        addin++;
        if (addin%10==0){
            System.out.println(Profile.getAverage()+" Cur Average");
        }
    }
}