import java.awt.*;

class Text extends Obj{
    private int fontSize=15;
    private String fontType="Arial";
    private String str="";
    private Font font;
    public Text(int X,int Y,int Size, Color Color,String text){   
        super("Text",X,Y,0,0,Color,false);
        this.fontSize=Size;
        this.str=text;
        recheck();
    }
    public void changeText(String newText){
        this.str=newText;
        recheck();
        
    }
    public void changeSize(int fontSize){
        this.fontSize=fontSize;
        recheck();
    }
    public void changeFont(String fontType){
        this.fontType=fontType;
        recheck();
    }
    @Override
    protected Object[] objectInformation(){
        return new Object[]{this.str,this.fontType,this.fontSize};
    }
    private void recheck(){
        Font font = new Font(fontType,Font.PLAIN,fontSize);
        Canvas c = new Canvas();
        FontMetrics fontMetrics = c.getFontMetrics(font);
        this.width=fontMetrics.stringWidth(this.str);
        this.length=fontMetrics.getHeight();
        this.font=font;
    }
    public Font getFont(){
        return font;
    }
}