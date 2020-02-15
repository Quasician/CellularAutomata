package View;

import Model.Simulation;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;


/**
 * Purpose: Creates a Visualizer instance that creates the grid of cells and colors them
 * Assumptions: Assumes that the simulation has a full color hashMap of type <String,String>
 * Dependencies: Dependent on access to the current simulation's abstract methods (getCell, getColorMap. getRows, etc...)
 * an example of how to use it
 * Visualizer vis1 = new Visualizer(curr_root, currentSim);
 * This creates the grid and automatically takes care of auto-scaling and coloring
 */
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

    private void createRecGrid()
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

    private void createHexGrid()
    {
        hexList = new Polygon[visRow][visCol];
        double cellWidth = (width / (double) visCol)/2.0;
        double cellHeight = (height / (double) visRow)/2.0;
        double x = cellWidth;
        double y = cellHeight;
        for (int i = 0; i < visRow; i++) {
            for (int j = 0; j < visCol; j++) {
                createHexagon(x,y,i,j,cellWidth,cellHeight);
                x += 2*cellWidth;
                y = updateY(j,y,cellHeight);
            }
            x = cellWidth;
            y += 2*cellHeight;
        }
    }

    private void initialize() {
        createRecGrid();
        colorGrid();
    }

    /**
     * Purpose: This method sets the shape list to be whatever color specified by the simulation
     * Assumptions:
     * Assumes that the simulation provided the method with a full hashMap of colors
     * Assumes that the hashMap provided is ot type <String,String>
     * return value: void
     */
    public void colorGrid() {
        for (int i = 0; i < visRow; i++) {
            for (int j = 0; j < visCol; j++) {
                recList[i][j].setFill(Color.web(colorMap.get(sim.getCell(i, j))));
            }
        }
    }

    private void createHexagon(double x, double y, int i, int j, double cellWidth, double cellHeight)
    {
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
    }

    private double updateY(int j, double y, double cellHeight)
    {
        if (j % 2 == 0) {
            y += cellHeight;
            if(j == visCol-1) {
                y -= cellHeight;
            }
        } else {
            y -= cellHeight;
        }
        return y;
    }
}
