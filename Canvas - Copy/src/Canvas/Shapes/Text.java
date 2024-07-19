package Canvas.Shapes;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import Canvas.Util.Vector2D;

public class Text extends Obj{

    private String str="";
    private Font font = new Font("Arial", Font.BOLD, (int)15);

    public Text(double X,double Y,double Height, Color Color,String text){

        super(X,Y,predictSize(text, fixFontHeight(Height,new Font("Arial", Font.BOLD, (int)Height))).x
        ,predictSize(text,fixFontHeight(Height,new Font("Arial", Font.BOLD, (int)Height))).y,Color,false);

        setFont(fixFontHeight(Height,new Font("Arial", Font.BOLD, (int)Height)));
    }

    /**
     * set the font, size and style of the text graphic
     * @param fontType
     * @param styleType use already defined Font.BOLD or others
     * @param fontSize
     */
    public void setFont(String fontType, int styleType, double fontSize){
        font = fixFontHeight(fontSize, new Font(fontType, styleType, (int)fontSize));

        recheck();
    }

    /**
     * 
     * @param font
     */
    private void setFont(Font font){
        this.font = font;

        recheck();
    }

    public String getText(){
        return str;
    }

    public void setText(String newText){
        this.str=newText;
        recheck();
    }

    private void recheck(){
        Canvas c = new Canvas();
        FontMetrics fontMetrics = c.getFontMetrics(font);
        this.width=fontMetrics.stringWidth(this.str);
        this.length=fontMetrics.getHeight();
    }
    
    /**
     * set the font, size and style of the text graphic
     * @param fontType
     * @param styleType use already defined Font.BOLD or others
     * @param fontSize
     */
    public static Vector2D predictSize(String str, Font font){
        Canvas c = new Canvas();
        FontMetrics fontMetrics = c.getFontMetrics(font);
        double width = fontMetrics.stringWidth(str);
        double length = fontMetrics.getHeight();
        return new Vector2D(width, length);

    }

    /**
     * set the font, size and style of the text graphic
     * @param fontType
     * @param styleType use already defined Font.BOLD or others
     * @param fontSize
     */
    private static double predictHeight(Font font){
        Canvas c = new Canvas();
        FontMetrics fontMetrics = c.getFontMetrics(font);
        double length = fontMetrics.getHeight();
        return length;

    }

    public Font getFont(){
        return font;
    }

    private static Font fixFontHeight(double desiredHeight, Font font){
        Font fixedFont = font;
        if ((int)desiredHeight < predictHeight(fixedFont)){
            while((int)desiredHeight < predictHeight(fixedFont)){
                fixedFont = new Font(fixedFont.getFontName(), fixedFont.getStyle(),fixedFont.getSize()+1);
            }
            if ((int)desiredHeight == predictHeight(fixedFont)){
                return fixedFont;
            }
            return new Font(fixedFont.getFontName(), fixedFont.getStyle(),fixedFont.getSize()-1);
        }else{
            while((int)desiredHeight > predictHeight(fixedFont)){
                fixedFont = new Font(fixedFont.getFontName(), fixedFont.getStyle(),fixedFont.getSize()-1);
            }
            if ((int)desiredHeight == predictHeight(fixedFont)){
                return fixedFont;
            }
            return new Font(fixedFont.getFontName(), fixedFont.getStyle(),fixedFont.getSize()+1);
        }
    }

    /**
     * The Text application of the show method, draws a Text String based on instance data
     */
    @Override
    public void show(Graphics2D g2dBuffer){
        int xp=(int)coords.x+(int)xxcoord;
        //System.out.println(shapes[i].xxcoord);
        int yp=(int)coords.y+(int)xycoord;
        double radians=(Math.toRadians(degree));
        int rotX=xp+(int)width/2;
        int rotY=yp+(int)length/2;
        int xtra=-(int)width/2;
        int ytra=-(int)length/2;                        
                                  
        g2dBuffer.rotate(-radians);
        g2dBuffer.translate(-rotX,-rotY);
        g2dBuffer.translate(rotX,yp+length/2);
        g2dBuffer.rotate(radians);
        
        g2dBuffer.setFont(this.getFont());
        g2dBuffer.drawString(this.str,xtra,ytra);
        
        g2dBuffer.rotate(-radians);
        g2dBuffer.translate(-rotX,-yp-length/2);
        g2dBuffer.translate(rotX,rotY);
        g2dBuffer.rotate(radians);
    }
}