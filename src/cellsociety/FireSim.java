package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class FireSim extends Simulation{

    private double probCatch;
    private double percentBurning;

    public FireSim(double rows, double cols, int width, int height, HashMap<String,Double> params)
    {
        super((int)rows, (int)cols, width,height, params);
        initParams();
        createGrid((int)rows,(int)cols);
        setUpHashMap();
    }

    @Override
    public void initParams() {
        probCatch = params.get("probCatch");
        percentBurning = params.get("percentBurning");
    }

    public void createGrid(int numRows, int numCols) {
        grid = new String[numRows][numCols];
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                double choice = Math.random();
                if (choice<=percentBurning) {
                    grid[i][j] = "burning";
                }
                else{
                    grid[i][j] = "tree";
                }
            }
        }
    }
    public void updateGrid() {
        String[][] gridCopy = new String[simRows][simCols];
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                gridCopy[i][j] = grid[i][j];
            }
        }
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                updateCell(i,j,gridCopy);
            }
        }
    }

    public void updateCell(int x, int y, String[][]gridCopy) {
        String[] neighbors = get4Neighbors(x,y, gridCopy);
        int sum = 0;
        for(int i = 0; i<neighbors.length;i++)
        {
            if(neighbors[i].equals("burning"))
            {
                sum++;
            }
        }
        if (gridCopy[x][y].equals("burning")) {
            grid[x][y] = "empty";
        }
        if (gridCopy[x][y].equals("tree") && sum > 0) {
           if(Math.random() < probCatch) {
               grid[x][y] = "burning";
           }
        }

    }
    public void setUpHashMap()
    {
        colorMap = new HashMap<>();
        colorMap.putIfAbsent("empty", Color.YELLOW);
        colorMap.putIfAbsent("tree", Color.GREEN);
        colorMap.putIfAbsent("burning", Color.RED);
    }

}
