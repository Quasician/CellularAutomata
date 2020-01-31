package cellsociety;

import java.util.ArrayList;

public abstract class Cell {

    protected int cellCol, cellRow;
    protected boolean state;

    ArrayList<Cell> neighbors;

    public Cell(int row, int col) {
        this.cellCol = col;
        this.cellRow = row;
        this.state = false;
    }

    public boolean getState() {return state;}
    public void setState(boolean state) {this.state = state;}
    public int getX() {return cellCol;}
    public void setX(int x) {this.cellCol = x;}
    public int getY() {return cellRow;}
    public void setY(int y) {this.cellRow = y;}


    public boolean equals(Cell c) {
        return cellCol == c.cellCol && cellRow == c.cellRow;
    }

    public void getNeighbors(Cell[][] grid)
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