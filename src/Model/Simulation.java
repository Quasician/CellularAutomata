package Model;


import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public abstract class Simulation {

    private int simRows, simCols;
    private int simWidth, simHeight;
    private HashMap<String, String> colorMap;
    private HashMap<String, Double> params;
    private HashMap<String, Double> agentNumbers;

    private String[][] grid;

    public Simulation(int rows, int cols, int width, int height, HashMap<String, Double> params){
        this.simRows = rows;
        this.simCols = cols;
        this.simWidth = width;
        this.simHeight = height;
        this.params = params;
        agentNumbers = new HashMap<>();
    }

    public abstract void initParams();
    public int getRows(){return simRows;}
    public int getCols(){return simCols;}
    public int getSimWidth(){return simWidth;}
    public int getSimHeight(){return simHeight;}
    public HashMap<String, Double> getParams(){return params;}
    public void createColorMap(HashMap<String, String> colorMap){
        this.colorMap = colorMap;
    }
    public void initAddToAgentNumberMap(String type)
    {
        agentNumbers.putIfAbsent(type, 0.0);
    }
    public void updateAgentNumberMap(String type, Double num)
    {
        agentNumbers.put(type, num);
    }
    public HashMap<String, Double> getAgentNumberMap(){return agentNumbers;}

    public void countAgentNumbers() {
        for(int i = 0; i<getRows();i++)
        {
            for(int j = 0; j<getCols();j++)
            {
                updateAgentNumberMap(getCell(i,j),getAgentNumberMap().get(getCell(i,j))+1);
            }
        }
    }

    public void resetAgentNumbers()
    {
        for(Map.Entry<String,Double> entry : getAgentNumberMap().entrySet())
        {
            updateAgentNumberMap(entry.getKey(),0.0);
        }
    }

    public void addToColorMap(String type, String color)
    {
        colorMap.putIfAbsent(type, color);
    }
    public void createGrid(String[][] grid)
    {
        this.grid = grid;
    }

    public String getCell(int x, int y)
    {
        return grid[x][y];
    }

    public void setCell(int x, int y, String value)
    {
        grid[x][y] = value;
    }


    public abstract void createGrid(int numRows, int numCols);

    public abstract void updateGrid();

    public abstract void setUpHashMap();


    public HashMap<String, String> getColorMap() {return colorMap;}

    public boolean inGrid(int rows, int cols)
    {
        if(rows>=0 && rows <simRows && cols>=0 && cols<simCols)
        {
            return true;
        }
        return false;
    }

    public String[] get4Neighbors(int x, int y, String[][] gridCopy)
    {
        String[] neighbors = new String[4];
        int count = 0;
        for(int i = x-1; i<=x+1;i++)
        {
            for(int j = y-1; j<=y+1;j++)
            {
                int temp1 = i - x + 1;
                int temp2 = j - y + 1;
                if((i - x + 1 + j - y + 1) % 2 == 0)
                {
                    continue;
                }
                else {
                    if(inGrid(i,j))
                    {
                        neighbors[count] = gridCopy[i][j];

                    } else {
                        neighbors[count] = "outOfBounds";
                    }
                    count++;
                }
            }
        }
        return neighbors;
    }

    public String[] get8Neighbors(int x, int y, String[][] gridCopy)
    {
        String[] neighbors = new String[8];
        int count = 0;
        for(int i = x-1; i<=x+1;i++)
        {
            for(int j = y-1; j<=y+1;j++)
            {
                if(i == x && j == y)
                {
                    continue;
                }
                else {
                    if(inGrid(i,j))
                    {
                        neighbors[count] = gridCopy[i][j];

                    } else {
                        neighbors[count] = "outOfBounds";
                    }
                    count++;
                }
            }
        }
        return neighbors;
    }


}
