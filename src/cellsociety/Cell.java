package cellsociety;

import java.util.ArrayList;

public abstract class Cell {

    protected int cellCol, cellRow;
    protected boolean state;

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


}