package cellsociety;


import java.util.ArrayList;
import java.util.List;

public abstract class Simulation {

    protected int simRow, simCol;
    protected int simWidth, simHeight;

    protected ArrayList<ArrayList<Cell>> grid;

    public Simulation(int row, int col, int width, int height){
        this.simRow = row;
        this.simCol = col;
        this.simWidth = width;
        this.simHeight = height;

    }

    public int getWidth(){return simWidth;}
    public int getHeight(){return simHeight;}
    public int getColPos(){return simCol;}
    public int getRowPos(){return simRow;}


    public void updateCell(int x, int y){
    }

    public void createGrid(int numRows, int numCols) {};

    public void updateGrid(){

    }

}
