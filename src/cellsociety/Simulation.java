package cellsociety;


import java.util.List;

public abstract class Simulation {

    private int simRow, simCol;
    private int simWidth, simHeight;
    private Cell[][] grid;

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

    public void createGrid(int numCols, int numRows) {
        grid = new Cell[numCols][numRows];
        for(int i = 0; i < numCols; i++) {
            for(int j = 0; j < numRows; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    public void updateGrid(){

    }

}
