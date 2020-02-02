package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class PercSim extends Simulation{

    public PercSim(int rows, int cols, int width, int height)
    {
        super(rows, cols, width,height);
        createGrid(rows,cols);
        setUpHashMap();
    }

    public void createGrid(int numRows, int numCols) {
        grid = new String[numRows][numCols];
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                ArrayList<String> list = new ArrayList<>();
                list.add("empty");
                list.add("blocked");
                String choice = list.get((int)Math.round(Math.random()));
                grid[i][j] = choice;
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
