import java.awt.*;

class Text extends Obj{
    private int fontSize=15;
    private String fontType="Arial";
    private int styleType=Font.BOLD;
    private String str="";
    private Font font = new Font(fontType, styleType, fontSize);
    public Text(int X,int Y,int Size, Color Color,String text){   
        super("Text",X,Y,0,0,Color,false);
        this.fontSize=Size;
        this.str=text;
        this.styleType=Font.BOLD;
        setFont(fontType, styleType, fontSize);
        recheck();
    }
    /**
     * set the font, size and style of the text graphic
     * @param fontType
     * @param styleType use already defined Font.BOLD or others
     * @param fontSize
     */
    public void setFont(String fontType, int styleType, int fontSize){
        font=new Font(fontType, styleType, fontSize);
    }
    public void setText(String newText){
        this.str=newText;
        recheck();
    }
    @Override
    protected Object[] objectInformation(){
        return new Object[]{this.str,this.getFont()};
    }
    private void recheck(){
        Canvas c = new Canvas();
        FontMetrics fontMetrics = c.getFontMetrics(font);
        this.width=fontMetrics.stringWidth(this.str);
        this.length=fontMetrics.getHeight();
    }
    public Font getFont(){
        return font;
    }

    @Override
    public void show(Graphics2D g2dBuffer){
        int xp=xcoord+xxcoord;
        //System.out.println(shapes[i].xxcoord);
        int yp=ycoord+xycoord;
        double radians=(Math.toRadians(degree));
        int rotX=xp+width/2;
        int rotY=yp+length/2;
        int xtra=-width/2;
        int ytra=-length/2;
        Object[] stuff=objectInformation();
                        
                                  
        g2dBuffer.rotate(-radians);
        g2dBuffer.translate(-rotX,-rotY);
        g2dBuffer.translate(rotX,yp+length/2);
        g2dBuffer.rotate(radians);
        
        g2dBuffer.setFont((Font) stuff[1]);
        g2dBuffer.drawString(stuff[0].toString(),xtra,ytra);
        
        g2dBuffer.rotate(-radians);
        g2dBuffer.translate(-rotX,-yp-length/2);
        g2dBuffer.translate(rotX,rotY);
        g2dBuffer.rotate(radians);
    }
}