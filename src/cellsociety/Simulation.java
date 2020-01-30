package cellsociety;


import java.util.List;

public abstract class Simulation {

    private int simRow, simCol;
    private int simWidth, simHeight;

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

    public void createGrid(){

    }

    public void updateGrid(){

    }

}
