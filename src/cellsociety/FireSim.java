package cellsociety;

import java.util.ArrayList;

public class FireSim extends Simulation{

    public FireSim(int row, int col, int width, int height)
    {
        super(row, col, width,height);
    }

    public void createGrid(int numRows, int numCols) {

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
        String[] neighbors = get8Neighbors(x,y);
        int sum = 0;
        for(int i = 0; i<neighbors.length;i++)
        {
            if(neighbors[i].equals("alive"))
            {
                sum++;
            }
        }
        if(gridCopy[x][y].equals("alive") && (sum == 2 || sum ==3))
        {
            grid[x][y] = "alive";
        }
        else if(gridCopy[x][y].equals("dead") && sum ==3)
        {
            grid[x][y] = "alive";
        }else {
            grid[x][y] = "dead";
        }
    }

}
