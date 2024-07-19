package Canvas.RoboticsUtil;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Canvas.FileUtil.FileWriter;
import Canvas.Inputs.UserInput;
import Canvas.RoboticsUtil.ClickableButton.Action;
import Canvas.RoboticsUtil.ClickableButton.Side;
import Canvas.Shapes.VisualJ;

public class UIRobotGenerator {

    private static ArrayList<LabeledButton> fileButtons = new ArrayList<>();

    private static int currentAmountDown;

    public static void generateObstacleFiles(VisualJ vis, String obstacleFolderPath){

        ObstacleSquare.setPath(obstacleFolderPath);

        String path = obstacleFolderPath;

        File directory = new File(path);
        File[] files = directory.listFiles();
        int num = 0;
        // Print names of all files
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    num++;
                    LabeledButton button = new LabeledButton(30, num*40, 150, 25, Color.WHITE, false, file.getName().replace(".json",""),vis);
                    vis.add(button);
                    fileButtons.add(button);
                    button.whenAction(Action.Pressed, Side.Left, ()->{
                        ObstacleSquare.loadSquares(vis, file.getName().replace(".json",""));
                    });
                }
            }
        }

        currentAmountDown = num;
        LabeledButton fileMaker = new LabeledButton(200, 40, 150, 25, new Color(50,200,100), false, "Make New File",vis);
        vis.add(fileMaker);
        fileMaker.whenAction(Action.Pressed, Side.Left, ()->{
            currentAmountDown++;
            System.out.print("What would you like the new file to be called? ");
            String nextLine = UserInput.getNextLine();
            
            ObstacleSquare.newBaseFile(vis, nextLine);
            LabeledButton button = new LabeledButton(30, 40*currentAmountDown, 150, 25, Color.WHITE, false, nextLine.replace(".json",""),vis);
            vis.add(button);
            fileButtons.add(button);
            button.whenAction(Action.Pressed, Side.Left, ()->{
                ObstacleSquare.loadSquares(vis, nextLine.replace(".json",""));
            });
        });

        LabeledButton nodeChanger = new LabeledButton(200, 80, 150, 25, new Color(50,200,100), false, "Change node size",vis);
        vis.add(nodeChanger);
        nodeChanger.whenAction(Action.Pressed, Side.Left, ()->{
            if (!ObstacleSquare.currentFile.equals("navgrid")){
                System.out.print("What is the new node size? ");
                double nextLine = UserInput.getNextDouble();

                ObstacleSquare.changeNodeSize(vis, nextLine);
            }
        });
        LabeledButton deleteFile = new LabeledButton(370, 40, 150, 25, new Color(50,200,100), false, "Delete this file",vis);
        vis.add(deleteFile);
        deleteFile.whenAction(Action.Pressed, Side.Left, ()->{
            currentAmountDown--;
            if (!ObstacleSquare.currentFile.equals("navgrid")){
                boolean broke = false;
                for (int i = 0; i < fileButtons.size(); i++){
                    if (broke){
                        fileButtons.get(i).move(0, -40);
                    }
                    if (fileButtons.get(i).getName().equals(ObstacleSquare.currentFile)){
                        fileButtons.get(i).deleteSelf(vis);
                        broke = true;
                    }
                }
                ObstacleSquare.deleteThisFile(vis);
                
            }
        });
        ObstacleSquare.loadSquares(vis,"navgrid");
    }
}
