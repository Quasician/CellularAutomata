package cellsociety;

import java.util.ArrayList;

enum State
{
    Empty, Tree, Burning,
    Alive, Dead ;
}


public class Cell {

    protected int cellCol, cellRow;
    protected State currentState;
    protected State futureState;
    protected int gridRows;
    protected int gridCols;

    ArrayList<Cell> neighbors;

    public Cell(int row, int col, int gridX, int gridY, State state) {
        this.cellCol = col;
        this.cellRow = row;
        currentState = state;
        this.gridRows = gridX;
        this.gridCols = gridY;
    }

    public State getCurrentState() {return currentState;}
    public State getFutureState() {return futureState;}
    public void setCurrentState(State state) {this.currentState = state;}
    public void setFutureState(State state) {this.futureState = state;}
    public int getX() {return cellCol;}
    public void setX(int x) {this.cellCol = x;}
    public int getY() {return cellRow;}
    public void setY(int y) {this.cellRow = y;}


    public boolean equals(Cell c) {
        return cellCol == c.cellCol && cellRow == c.cellRow;
    }

    public boolean inGrid(int rows, int cols)
    {
        if(rows>=0 && rows< gridRows && cols>=0 && cols<gridCols)
        {
            return true;
        }
        return false;
    }

    public void get4Neighbors(ArrayList<ArrayList<Cell>> grid)
    {
        if(cellRow >= 1)
        {
            neighbors.add(grid.get(cellRow-1).get(cellCol));
        }
        if(cellRow <= grid.size()-2)
        {
            neighbors.add(grid.get(cellRow+1).get(cellCol));
        }
        if(cellCol >= 1)
        {
            neighbors.add(grid.get(cellRow).get(cellCol-1));
        }
        if(cellCol <= grid.get(0).size()-2)
        {
            neighbors.add(grid.get(cellRow).get(cellCol+1));
        }
    }

    public void get8Neighbors(ArrayList<ArrayList<Cell>> grid)
    {
        for(int i = cellRow -1; i<=cellRow+1;i++)
        {
            for(int j = cellCol -1; i<=cellCol+1;i++)
            {
                if(i == cellRow && j == cellCol)
                {
                    continue;
                }
                else {
                    if(inGrid(i,j))
                    {
                        neighbors.add(grid.get(i).get(j));
                    }
                }
            }
        }
    }

}