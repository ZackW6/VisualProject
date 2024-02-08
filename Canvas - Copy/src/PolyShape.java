public class PolyShape extends Obj{
    private Obj[] shapes;
    public PolyShape(Obj[] shapes){
        super("PolyShape",0,0,0,0,null,true);
        this.shapes=shapes;
        
    }
    public void addShape(Obj shape){

    }
    public void removeShape(){

    }
    public void removeShape(int index){

    }
    private void resetShapes(){
        int lowx=shapes[0].xcoord;
        int highxW=shapes[0].xcoord;
        int lowy=shapes[0].ycoord;
        int highyL=shapes[0].ycoord;
        for (int i=0;i<shapes.length;i++){
            if (shapes[i].xcoord<lowx){
                lowx=shapes[i].xcoord;
            }else if (shapes[i].xcoord+shapes[i].width>highxW){
                highxW=shapes[i].xcoord+shapes[i].width;
            }
            if (shapes[i].ycoord<lowy){
                lowy=shapes[i].ycoord;
            }else if (shapes[i].ycoord+shapes[i].length>highyL){
                highyL=shapes[i].ycoord+shapes[i].length;
            }
        }
        //this.xcoord=
    }
    
}
