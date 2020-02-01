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

}