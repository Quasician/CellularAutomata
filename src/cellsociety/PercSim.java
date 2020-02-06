package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class PercSim extends Simulation{

    private double percentEmpty;
    private double percentBlocked;

    public PercSim(double rows, double cols, int width, int height, HashMap<String,Double> params)
    {
        super((int)rows, (int)cols, width,height, params);
        initParams();
        createGrid((int)rows,(int)cols);
        setUpHashMap();
    }

    @Override
    public void initParams() {
        percentEmpty = params.get("percentEmpty");
        percentBlocked = params.get("percentBlocked");
    }

    public void createGrid(int numRows, int numCols) {
        grid = new String[numRows][numCols];
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                double choice = Math.random();
                if (choice<=percentEmpty) {
                    grid[i][j] = "empty";
                }
                else{
                    grid[i][j] = "blocked";
                }
            }
        }
        grid[25][25] = "full";
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
        String[] neighbors = get8Neighbors(x,y, gridCopy);
        int sum = 0;
        for(int i = 0; i<neighbors.length;i++)
        {
            if(neighbors[i].equals("full"))
            {
                sum++;
            }
        }

        if(gridCopy[x][y].equals("empty") && sum > 0)
        {
            grid[x][y] = "full";
        }
    }
    public void setUpHashMap()
    {
        colorMap = new HashMap<>();
        colorMap.putIfAbsent("full", Color.DEEPSKYBLUE);
        colorMap.putIfAbsent("empty", Color.WHITE);
        colorMap.putIfAbsent("blocked", Color.BLACK);
    }
}
