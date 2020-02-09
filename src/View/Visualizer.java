package View;

import Model.Simulation;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashMap;

public class Visualizer {

    private int visRow, visCol;
    private int width, height;
    private BorderPane root;
    private Rectangle[][] recList;
    private Polygon[][] hexList;
    private HashMap<String, String> colorMap;
    private Simulation sim;

    public Visualizer(BorderPane root, Simulation sim) {
        this.visCol = sim.getCols();
        this.visRow = sim.getRows();
        this.root = root;
        this.colorMap = sim.getColorMap();
        width = sim.getSimWidth();
        height = sim.getSimHeight() - 100;
        this.sim = sim;
        initialize();
    }

    public void createRecGrid()
    {
        recList = new Rectangle[visRow][visCol];
        double cellWidth = width / (double) visRow;
        double cellHeight = height / (double) visCol;


        double x = 0;
        double y = 0;
        for (int i = 0; i < visRow; i++) {
            for (int j = 0; j < visCol; j++) {
                Rectangle rec = new Rectangle(x, y, cellWidth, cellHeight);
                root.getChildren().add(rec);
                recList[i][j] = rec;
                x += cellWidth;
            }
            x = 0;
            y += cellHeight;
        }
    }

    public void createHexGrid()
    {
        recList = new Rectangle[visRow][visCol];
        double cellWidth = width / (double) visRow;
        double cellHeight = height / (double) visCol;


        double x = 0;
        double y = 0;
        for (int i = 0; i < visRow; i++) {
            for (int j = 0; j < visCol; j++) {
                Rectangle rec = new Rectangle(x, y, cellWidth, cellHeight);
                root.getChildren().add(rec);
                recList[i][j] = rec;
                x += cellWidth;
            }
            x = 0;
            y += cellHeight;
        }
    }

    public void initialize() {
        createHexGrid();
        colorGrid();
    }

    public void colorGrid() {
        //System.out.println("YEET!");
        for (int i = 0; i < visRow; i++) {
            for (int j = 0; j < visCol; j++) {
                //System.out.println("I: " + i + "J: "+ j);
                recList[i][j].setFill(Color.web(colorMap.get(sim.getCell(i,j))));
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
