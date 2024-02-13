package GraphicsJ;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MyPanel extends JPanel implements ActionListener{
    Runnable[] runners=new Runnable[4000];
    final int PANEL_WIDTH = 1000;
    final int PANEL_HEIGHT = 700;
    Timer timer;
    int x = 0;
    int y = 0;
    int xVelocity = 1;
    int yVelocity = 1;
    Particle[] p = new Particle[1000];
    MyPanel(){
       // System.out.println(p.length);
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.black);
        timer = new Timer(1000/200, this);
        timer.start();


        for(int i = 0; i < p.length; i++){
            double xPosition = ((PANEL_WIDTH)/2) + ((Math.random()*2-1)*100);
            double yPosition = ((PANEL_HEIGHT)/2) + ((Math.random()*2-1)*100);
           
            double xVelocity = (xPosition - (PANEL_WIDTH/2));
            double yVelocity = (yPosition - PANEL_HEIGHT/2);

            double velLength = Math.sqrt(Math.pow(xVelocity, 2) + Math.pow(yVelocity, 2));

          p[i] = new Particle(xPosition, yPosition, (xVelocity/velLength)*10, (yVelocity/velLength)*10, 10);
          
        }

    }

    public void paint(Graphics g){
        super.paint(g); // paint black

        Graphics g2D = (Graphics2D) g;
        for(int i = 0; i < p.length; i++){
          p[i].show(g2D);
        }

    }
    /**
     * sup
     * 
     */
    @Override
    public void actionPerformed(ActionEvent e){
        for(int i = 0; i < p.length; i++){
          // double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
          // double mouseY = MouseInfo.getPointerInfo().getLocation().getY();
          
        //  p[i].applyforce(newX, newY);
          p[i].update();
          p[i].edges(PANEL_WIDTH,PANEL_HEIGHT);
        //  p[i].gravity(1);
          p[i].gravitate(p);
          if(p[i].isDead(PANEL_WIDTH, PANEL_HEIGHT)){
          
          }
        }
        // x += xVelocity;
        // y += yVelocity;
        repaint();
    }
    
}
