package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class FireSim extends Simulation{

    private double probCatch = .6;

    public FireSim(int rows, int cols, int width, int height, HashMap<String,Double> params)
    {
        super(rows, cols, width,height, params);
        createGrid(rows,cols);
        setUpHashMap();
    }

    @Override
    public void initParams() {

    }

    public void createGrid(int numRows, int numCols) {
        grid = new String[numRows][numCols];
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                ArrayList<String> list = new ArrayList<>();
                list.add("tree");
                list.add("burning");
                String choice = list.get((int)Math.round(Math.random()));
                grid[i][j] = choice;
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
