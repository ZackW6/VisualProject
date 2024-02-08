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
}