package GraphicsJ;
import java.awt.*;
import java.util.*;
public class Particle {
    private double x;
    private double y;
    private int w;
    private double xVel;
    private double yVel;
    private double xAcc;
    private double yAcc;
    private Color type;


    Runnable run=()->{

    };
    Particle(double xPosition, double yPosition, double xVelocity, double yVelocity, int width){
        
        x = xPosition;
        y = yPosition;
        xVel = 1;//xVelocity;
        yVel = 0;//yVelocity;
        xAcc = 0;
        yAcc = 0;
        w = width;
        double t = (Math.random()*4);
        if(t < 1){
            type = Color.red;
        }else if(t < 2){
            type = Color.blue;
        }else if( t < 3){
            type = Color.green;
        }else if( t < 4){
            type = Color.cyan;
        }
    }
    public double[] getPosition(){
        double [] a = new double[2];
        a[0] = x;
        a[1] = y;
        return a;
    }
    public void edges(int panelWidth, int panelHeight){
        if(x < 0 ){
            x = 0;
            xVel*= -0.9;
          //  xVel *= -0.1;
        }
        if(x > panelWidth-w){
            x = panelWidth-w;
            xVel*= -0.9;
          //  xVel *= -0.1;
        }
        if(y < 0 ){
            y = 0;
            yVel *= -0.9;
         //   yAcc *= -0.1;
        }
        if(y > panelHeight-w){
            y = panelHeight-w;
            yVel *= -0.9;
           // yAcc *= -0.1;
        }
    }
    
    public void update(){
        xVel += xAcc;
        yVel += yAcc;
        x+= xVel;
        y+= yVel;
        xAcc *= 0;
        yAcc *= 0;
    }
    public void applyforce(double x, double y){
        xAcc += x/10;
        yAcc += y/10;
    }
    public double mag(double x, double y){
         double dist =  Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
         if(dist == 0){
            dist = 0.01;
         }
        return dist;
    }
    public void gravity(double amount){
        this.applyforce(0, amount);
    }
    public void gravitate(Particle[] hub){
        //System.out.println(hub[0].getPosition()[0]);
        for(int i = 0; i < hub.length; i++){
            
            if(hub[i] != this){
              double otherPosX = hub[i].x;
              double otherPosY = hub[i].y;
              double mass1;
              double mass2;
              if(this.type == Color.red){
                mass1 = 10;
              }else if(this.type == Color.green){
                mass1 = 1;
              }else{
                mass1 = 10;
              }
              if(hub[i].type == Color.red){
                mass2 = 10;
              }else if(hub[i].type == Color.green){
                mass2 = 1;
              }else{
                mass2 = 10;
              }
              double d = Math.pow(mag(otherPosX-x, otherPosY-y), 2);
              double strength = 1*((mass1*mass2)/d);
              double dirX = ((otherPosX - x )/mag(otherPosX, otherPosY))*(strength/2);
              double dirY = ((otherPosY - y)/mag(otherPosX, otherPosY))*(strength/2);
              
              applyforce(dirX, dirY);


            }
        }
    }
    public boolean isDead(int pW, int pH){
        if(x > pW || x < 0 || y > pH || y < 0){
            return true;
            
        }else{
            return false;
        }
    }
    public 

     void show(Graphics g){
        g.setColor(type);
        g.fillOval((int)x, (int)y, w, w);
    }

}
