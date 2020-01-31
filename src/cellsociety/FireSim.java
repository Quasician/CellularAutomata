package cellsociety;

public class FireSim extends Simulation{

    private FireCell[][] grid;
    public FireSim(int row, int col, int width, int height)
    {
        super(row, col, width,height);
    }

    public void createGrid(int numRows, int numCols) {
        grid = new FireCell[numRows][numCols];
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {
                grid[i][j] = new FireCell(i, j, grid);
            }
        }
    }

}
