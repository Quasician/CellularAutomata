package cellsociety;


import java.util.ArrayList;
import java.util.HashMap;

public class GOLSim extends Simulation{

    private double percentAlive;

    public GOLSim(double rows, double cols, int width, int height, HashMap<String,Double> params)
    {
        super((int)rows, (int)cols, width,height, params);
        initParams();
        createGrid((int)rows,(int)cols);
        setUpHashMap();
    }

    public void createGrid(int numRows, int numCols) {
        grid = new String[numRows][numCols];
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                double choice = Math.random();
                if (choice<=percentAlive) {
                    grid[i][j] = "alive";
                }
                else{
                    grid[i][j] = "dead";
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

    @Override
    public void initParams() {
        percentAlive = params.get("percentAlive");
    }

    public void updateCell(int x, int y, String[][]gridCopy) {
        String[] neighbors = get8Neighbors(x,y, gridCopy);
        int sum = 0;
        for(int i = 0; i<neighbors.length;i++)
        {
            if(neighbors[i].equals("alive"))
            {
                sum++;
            }
        }

        if(gridCopy[x][y].equals("alive") && (sum == 2 || sum == 3))
        {
            grid[x][y] = "alive";
        }
        else if(gridCopy[x][y].equals("dead") && sum == 3)
        {
            grid[x][y] = "alive";
        }else {
            grid[x][y] = "dead";
        }
    }

    public void setUpHashMap()
    {
        colorMap = new HashMap<>();
        colorMap.putIfAbsent("alive", "black");
        colorMap.putIfAbsent("dead", "blue");
    }
}
