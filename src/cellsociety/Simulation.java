package cellsociety;


import javafx.scene.paint.Color;

import java.util.HashMap;

public abstract class Simulation {

    protected int simRows, simCols;
    protected int simWidth, simHeight;
    protected HashMap<String, Color> colorMap;
    protected HashMap<String, Double> params;

    protected String[][] grid;

    public Simulation(int rows, int cols, int width, int height, HashMap<String, Double> params){
        this.simRows = rows;
        this.simCols = cols;
        this.simWidth = width;
        this.simHeight = height;
        this.params = params;
    }

    public abstract void initParams();
    public int getWidth(){return simWidth;}
    public int getHeight(){return simHeight;}
    public int getColPos(){return simCols;}
    public int getRowPos(){return simRows;}


    public abstract void updateCell(int x, int y,String[][]gridCopy);

    public abstract void createGrid(int numRows, int numCols);

    public abstract void updateGrid();

    public abstract void setUpHashMap();

    public HashMap<String, Color> getColorMap() {return colorMap;}

    public String[][] getGrid()
    {
        return grid;
    }

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
