package cellsociety;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Visualizer {

    private int visRow, visCol;
    private int width, height;
    private BorderPane root;
    private Rectangle[][] recList;
    private HashMap<String, String> colorMap;

    public Visualizer(int row, int col, int windowWidth, int windowHeight, BorderPane root, HashMap<String, String> colorMap) {
        this.visCol = col;
        this.visRow = row;
        this.root = root;
        this.colorMap = colorMap;
        width = windowWidth;
        height = windowHeight-100;
    }

    public void initialize(String[][] grid) {
        recList = new Rectangle[visRow][visCol];
        double cellWidth = width/(double)visRow;
        double cellHeight= height/(double)visCol;



        double x = 0;
        double y = 0;
        for(int i = 0;i<visRow;i++)
        {
            for(int j = 0;j<visCol;j++)
            {
                Rectangle rec = new Rectangle(x,y,cellWidth,cellHeight);
                root.getChildren().add(rec);
                recList[i][j] = rec;
                x+=cellWidth;
            }
            x=0;
            y+=cellHeight;
        }
        colorGrid(grid);
    }

    public void colorGrid(String[][] grid)
    {
        //System.out.println("YEET!");
        for(int i = 0;i<visRow;i++)
        {
            for(int j = 0;j<visCol;j++)
            {
                //System.out.println("I: " + i + "J: "+ j);
                recList[i][j].setFill(Color.web(colorMap.get(grid[i][j])));
            }
        }
        //System.out.println("END YEET!");
    }


    public void newConfig(/** some action **/) {
        Parent root;
        Stage stage;

//        if(/** some action triggered **/) {
//            stage = /** new stage **/;
//            root = /** new root **/;
//
//            Scene scene = new Scene(root);
//            stage.setTitle("temp title");
//            stage.setScene(scene);
//            stage.show();
//        }
    }
}
