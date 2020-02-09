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
        double cellWidth = width / (double) visCol;
        double cellHeight = height / (double) visRow;


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
        hexList = new Polygon[visRow][visCol];
        double cellWidth = (width / (double) visCol)/2.0;
        double cellHeight = (height / (double) visRow)/2.0;


        double x = cellWidth;
        double y = cellHeight;
        for (int i = 0; i < visRow; i++) {
            for (int j = 0; j < visCol; j++) {
                double[] xVals = new double[6];
                double[] yVals = new double[6];
                for(int k = 0; k<6;k++)
                {
                    xVals[k] = cellWidth * Math.cos(2*Math.PI*k/6 ) + x;
                    yVals[k] = cellHeight * Math.sin(2*Math.PI*k/6 ) + y;
                }
                double[] combined = new double [12];
                for(int k = 0; k<6;k++)
                {
                    combined[2*k] = xVals[k];
                    combined[2*k+1] = yVals[k];
                }
                Polygon hex = new Polygon(combined);
                root.getChildren().add(hex);
                hexList[i][j] = hex;
                x += 2*cellWidth;

                if(j!=visCol-1) {
                    if (j % 2 == 0) {
                        y += cellHeight;
                    } else {
                        y -= cellHeight;
                    }
                }
            }
            x = cellWidth;
            y += 2*cellHeight;
        }
    }

    public void initialize() {
        createRecGrid();
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
