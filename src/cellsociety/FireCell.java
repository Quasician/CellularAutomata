package cellsociety;

import java.util.ArrayList;

public class FireCell extends Cell{
    ArrayList<FireCell> neighbors = new ArrayList<FireCell>();

    public FireCell(int row, int col, FireCell[][] grid) {
        super(row, col);
        getNeighbors(grid);
    }

    public void getNeighbors(FireCell[][] grid)
    {
        if(cellRow >= 1)
        {
            neighbors.add(grid[cellRow-1][cellCol]);
        }
        if(cellRow <= grid.length-2)
        {
            neighbors.add(grid[cellRow+1][cellCol]);
        }
        if(cellCol >= 1)
        {
            neighbors.add(grid[cellRow][cellCol-1]);
        }
        if(cellCol <= grid[0].length-2)
        {
            neighbors.add(grid[cellRow][cellCol+1]);
        }

    }


}
