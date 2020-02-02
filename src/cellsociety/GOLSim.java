package cellsociety;

import java.util.ArrayList;

public class GOLSim extends Simulation{

    public GOLSim(int rows, int cols, int width, int height)
    {
        super(rows, cols, width,height);
        createGrid(rows,cols);
    }

    public void createGrid(int numRows, int numCols) {
        grid = new String[numRows][numCols];
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                grid[i][j] = "dead";
            }
        }
        grid[25][25] = "alive";
        grid[26][25] = "alive";
        grid[27][25] = "alive";
        grid[25][26] = "alive";
        grid[26][24] = "alive";
    }


    public void updateGrid() {
        //System.out.println("GOT HERE");
        String[][] gridCopy = new String[simRows][simCols];
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                gridCopy[i][j] = grid[i][j];
            }
        }
        //System.out.println("FINISHED THIS");
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                updateCell(i,j,gridCopy);
            }
        }
    }

    public void updateCell(int x, int y, String[][]gridCopy) {
//        System.out.println(x);
//        System.out.println(y);
        String[] neighbors = get8Neighbors(x,y, gridCopy);
        int sum = 0;
        for(int i = 0; i<neighbors.length;i++)
        {
            if(neighbors[i].equals("alive"))
            {
                sum++;
            }
        }
//        if(x==26 && y ==26)
//        {
//            System.out.println(x);
//            System.out.println(y);
//            System.out.println(sum);
//        }
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
}
