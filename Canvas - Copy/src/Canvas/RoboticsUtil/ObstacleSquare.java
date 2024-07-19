package Canvas.RoboticsUtil;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Canvas.FileUtil.FileWriter;
import Canvas.Shapes.Obj;
import Canvas.Shapes.Square;
import Canvas.Shapes.VisualJ;

public class ObstacleSquare extends ClickableButton{

    public static String currentFile = "";
    public static String pathName;
    private static ArrayList<ObstacleSquare> obstacles = new ArrayList<ObstacleSquare>();


    public ObstacleSquare(double x, double y, double sideLength, Color color, boolean tf){
        super(x, y, sideLength, sideLength, color, tf);
        this.whenAction(Action.Dragged, Side.Left, ()->{
            if (!currentFile.equals("navgrid")){
                this.setSide(true);
            }
        });
        this.whenAction(Action.Dragged, Side.Right, ()->{
            if (!currentFile.equals("navgrid")){
                this.setSide(false);
            }
        });
        this.whenAction(Action.Pressed, Side.Left, ()->{
            if (!currentFile.equals("navgrid")){
                this.setSide(true);
            }
        });
        this.whenAction(Action.Pressed, Side.Right, ()->{
            if (!currentFile.equals("navgrid")){
                this.setSide(false);
            }
        });
    }

    public void setSide(boolean bool){

        String find = "";
        if (bool){
            find = "true";
            this.setColor(new Color(250,0,0));
        }else{
            find = "false";
            this.setColor(new Color(0,0,250));
        }

        FileWriter fileWriter = new FileWriter(pathName);
        
        String line = fileWriter.readFile(currentFile+".json").get(0);

        int foundIndex = findNthCombinedOccurrence(line, "true","false", obstacles.indexOf(this)+1);

        if (line.substring(foundIndex, foundIndex+1).equals("f")){
            line = line.substring(0,foundIndex) + find + line.substring(foundIndex + 5);
        }else{
            line = line.substring(0,foundIndex) + find + line.substring(foundIndex + 4);
        }
        

        fileWriter.writeFile(currentFile+".json", List.of(line));

    }

    public static int findNthCombinedOccurrence(String str, String word1, String word2, int n) {
        int combinedOccurrence = 0;
        int index = -1;

        // Loop through the string to find the nth combined occurrence
        for (int i = 0; i < str.length() - Math.max(word1.length(), word2.length()) + 1; i++) {
            if (str.substring(i, i + word1.length()).equals(word1) || str.substring(i, i + word2.length()).equals(word2)) {
                combinedOccurrence++;
                if (combinedOccurrence == n) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    public static void setPath(String path){
        pathName = path;
    }

    public static void clearObstacles(VisualJ vis){
        for (int i = 0; i < obstacles.size(); i++){
            vis.remove(obstacles.get(i));
            ClickableButton.remove(obstacles.get(i));
        }
        obstacles.clear();
    }

    public static void loadSquares(VisualJ vis, String fileName){

        clearObstacles(vis);

        currentFile = fileName;

        String path = pathName;

        FileWriter fileWriter = new FileWriter(path);

        List<String> contents = fileWriter.readFile(fileName+".json");
        if (contents.size()!=0){
            String content = contents.get(0);

            int xSizeIndex = content.indexOf("x\":");
            int ySizeIndex = content.indexOf("y\":");
            int nodeSizeIndex = content.indexOf("s\":");
            double xSize = Double.parseDouble(content.substring(xSizeIndex, ySizeIndex).replaceAll("[^0-9.]", ""));
            double ySize = Double.parseDouble(content.substring(ySizeIndex, nodeSizeIndex).replaceAll("[^0-9.]", ""));
            double nodeSize = Double.parseDouble(content.substring(nodeSizeIndex,nodeSizeIndex+10).replaceAll("[^0-9.]", ""));
            int xNum = (int)(xSize/nodeSize);
            int yNum = (int)(ySize/nodeSize);
            double actualSize = ((double)vis.WIDTH-vis.WIDTH/6)/(double)xNum;

            int row = 0;
            int col = 0;
            
            while(content.contains("true") || content.contains("false")){
                int trueIndex = content.indexOf("true");
                int falseIndex = content.indexOf("false");
                int usedIndex = -1;
                if (trueIndex<0){
                    trueIndex = content.length()-1;
                }
                if (falseIndex<0){
                    falseIndex = content.length()-1;
                }
                if (trueIndex<falseIndex){
                    usedIndex = trueIndex;
                    ObstacleSquare obstacleSquare = new ObstacleSquare((vis.WIDTH/9+col*actualSize)+1, vis.HEIGHT-(vis.WIDTH/24+row*actualSize+actualSize)+1, actualSize-2, new Color(250, 0, 0), false);
                    vis.add(obstacleSquare);
                    obstacles.add(obstacleSquare);
                }else{
                    usedIndex = falseIndex;
                    ObstacleSquare obstacleSquare = new ObstacleSquare((vis.WIDTH/9+col*actualSize)+1, vis.HEIGHT-(vis.WIDTH/24+row*actualSize+actualSize)+1, actualSize-2, new Color(0, 0, 250), false);
                    vis.add(obstacleSquare);
                    obstacles.add(obstacleSquare);
                }
                

                col++;
                if (col == xNum+1){
                    col = 0;
                    row++;
                }
                content = content.substring(0, usedIndex) + content.substring(usedIndex+2);
            }
        }
        
    }

    public static void newBaseFile(VisualJ vis, String fileName){

        clearObstacles(vis);

        currentFile = fileName;

        String path = pathName;

        FileWriter fileWriter = new FileWriter(path);

        List<String> contents = fileWriter.readFile("navgrid"+".json");
        fileWriter.writeFile(fileName+".json",contents);

        String content = contents.get(0);

        int xSizeIndex = content.indexOf("x\":");
        int ySizeIndex = content.indexOf("y\":");
        int nodeSizeIndex = content.indexOf("s\":");
        double xSize = Double.parseDouble(content.substring(xSizeIndex, ySizeIndex).replaceAll("[^0-9.]", ""));
        double ySize = Double.parseDouble(content.substring(ySizeIndex, nodeSizeIndex).replaceAll("[^0-9.]", ""));
        double nodeSize = Double.parseDouble(content.substring(nodeSizeIndex,nodeSizeIndex+10).replaceAll("[^0-9.]", ""));
        int xNum = (int)(xSize/nodeSize);
        int yNum = (int)(ySize/nodeSize);
        double actualSize = ((double)vis.WIDTH-vis.WIDTH/6)/(double)xNum;

        int row = 0;
        int col = 0;
        
        while(content.contains("true") || content.contains("false")){
            int trueIndex = content.indexOf("true");
            int falseIndex = content.indexOf("false");
            int usedIndex = -1;
            if (trueIndex<0){
                trueIndex = content.length()-1;
            }
            if (falseIndex<0){
                falseIndex = content.length()-1;
            }
            if (trueIndex<falseIndex){
                usedIndex = trueIndex;
                ObstacleSquare obstacleSquare = new ObstacleSquare((vis.WIDTH/9+col*actualSize)+1, vis.HEIGHT-(vis.WIDTH/24+row*actualSize+actualSize)+1, actualSize-2, new Color(250, 0, 0), false);
                vis.add(obstacleSquare);
                obstacles.add(obstacleSquare);
            }else{
                usedIndex = falseIndex;
                ObstacleSquare obstacleSquare = new ObstacleSquare((vis.WIDTH/9+col*actualSize)+1, vis.HEIGHT-(vis.WIDTH/24+row*actualSize+actualSize)+1, actualSize-2, new Color(0, 0, 250), false);
                vis.add(obstacleSquare);
                obstacles.add(obstacleSquare);
            }
            

            col++;
            if (col == xNum+1){
                col = 0;
                row++;
            }
            content = content.substring(0, usedIndex) + content.substring(usedIndex+2);
        }
        
    }
    
    public static void changeNodeSize(VisualJ vis, double nodeSize){

        clearObstacles(vis);

        String path = pathName;

        FileWriter fileWriter = new FileWriter(path);

        List<String> contents = fileWriter.readFile(currentFile+".json");

        String content = contents.get(0);

        int xSizeIndex = content.indexOf("x\":");
        int ySizeIndex = content.indexOf("y\":");
        int nodeSizeIndex = content.indexOf("s\":");
        double xSize = Double.parseDouble(content.substring(xSizeIndex, ySizeIndex).replaceAll("[^0-9.]", ""));
        double ySize = Double.parseDouble(content.substring(ySizeIndex, nodeSizeIndex).replaceAll("[^0-9.]", ""));
        int xNum = (int)(xSize/nodeSize);
        int yNum = (int)(ySize/nodeSize);

        String falseLines = "[";
        for (int i = 0; i < xNum-1; i++){
            falseLines+="false,";
        }
        falseLines+="false,false]";
        String wholeLine = "";
        for(int i = 0; i < yNum; i++){
            wholeLine += falseLines+",";
        }
        wholeLine+=falseLines;
        String line = "{\"field_size\":{\"x\":16.54,\"y\":8.21},\"nodeSizeMeters\":"+nodeSize+",\"grid\":["+wholeLine+"]}";

        content = line;

        fileWriter.writeFile(currentFile+".json", List.of(line));

        double actualSize = ((double)vis.WIDTH-vis.WIDTH/6)/(double)xNum;

        int row = 0;
        int col = 0;
        
        while(content.contains("true") || content.contains("false")){
            int trueIndex = content.indexOf("true");
            int falseIndex = content.indexOf("false");
            int usedIndex = -1;
            if (trueIndex<0){
                trueIndex = content.length()-1;
            }
            if (falseIndex<0){
                falseIndex = content.length()-1;
            }
            if (trueIndex<falseIndex){
                usedIndex = trueIndex;
                ObstacleSquare obstacleSquare = new ObstacleSquare((vis.WIDTH/9+col*actualSize)+1, vis.HEIGHT-(vis.WIDTH/24+row*actualSize+actualSize)+1, actualSize-2, new Color(250, 0, 0), false);
                vis.add(obstacleSquare);
                obstacles.add(obstacleSquare);
            }else{
                usedIndex = falseIndex;
                ObstacleSquare obstacleSquare = new ObstacleSquare((vis.WIDTH/9+col*actualSize)+1, vis.HEIGHT-(vis.WIDTH/24+row*actualSize+actualSize)+1, actualSize-2, new Color(0, 0, 250), false);
                vis.add(obstacleSquare);
                obstacles.add(obstacleSquare);
            }
            

            col++;
            if (col == xNum+1){
                col = 0;
                row++;
            }
            content = content.substring(0, usedIndex) + content.substring(usedIndex+2);
        }
    }

    public static void deleteThisFile(VisualJ vis){

        clearObstacles(vis);

        FileWriter fileWriter = new FileWriter(pathName);
        fileWriter.deleteFile(currentFile+".json");
        loadSquares(vis, "navgrid");

    }


    public static void createBackground(VisualJ vis, String path, String fileName){

        FileWriter fileWriter = new FileWriter(path);

        List<String> contents = fileWriter.readFile(fileName+".json");
        if (contents.size()!=0){
            String content = contents.get(0);

            int xSizeIndex = content.indexOf("x\":");
            int ySizeIndex = content.indexOf("y\":");
            int nodeSizeIndex = content.indexOf("s\":");
            double xSize = Double.parseDouble(content.substring(xSizeIndex, ySizeIndex).replaceAll("[^0-9.]", ""));
            double ySize = Double.parseDouble(content.substring(ySizeIndex, nodeSizeIndex).replaceAll("[^0-9.]", ""));
            double nodeSize = Double.parseDouble(content.substring(nodeSizeIndex,nodeSizeIndex+10).replaceAll("[^0-9.]", ""));
            int xNum = (int)(xSize/nodeSize);
            int yNum = (int)(ySize/nodeSize);
            double actualSize = ((double)vis.WIDTH-vis.WIDTH/6)/(double)xNum;

            int row = 0;
            int col = 0;
            
            while(content.contains("true") || content.contains("false")){
                int trueIndex = content.indexOf("true");
                int falseIndex = content.indexOf("false");
                int usedIndex = -1;
                if (trueIndex<0){
                    trueIndex = content.length()-1;
                }
                if (falseIndex<0){
                    falseIndex = content.length()-1;
                }
                if (trueIndex<falseIndex){
                    usedIndex = trueIndex;
                    Square obstacleSquare = new Square((vis.WIDTH/9+col*actualSize), vis.HEIGHT-(vis.WIDTH/24+row*actualSize+actualSize), actualSize, new Color(50, 75, 75), true);
                    vis.add(obstacleSquare);
                }else{
                    usedIndex = falseIndex;
                    // Square obstacleSquare = new Square((vis.WIDTH/9+col*actualSize)+1, vis.HEIGHT-(vis.WIDTH/24+row*actualSize)+1, actualSize-2, new Color(0, 0, 250), true);
                    // vis.add(obstacleSquare);
                }
                

                col++;
                if (col == xNum+1){
                    col = 0;
                    row++;
                }
                content = content.substring(0, usedIndex) + content.substring(usedIndex+2);
            }
        }
        
    }

}