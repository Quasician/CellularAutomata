package cellsociety;

import java.util.ArrayList;

public class SegCell extends Cell{
    ArrayList<SegCell> neighbors = new ArrayList<SegCell>();

    public SegCell(int row, int col,SegCell[][] grid) {
        super(row, col);
        getNeighbors(grid);
    }

    public void getNeighbors(SegCell[][] grid)
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
