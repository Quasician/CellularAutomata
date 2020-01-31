package cellsociety;

public class SegregationSim extends Simulation{

    private SegCell[][] grid;
    public SegregationSim(int row, int col, int width, int height)
    {
        super(row, col, width,height);
    }

    public void createGrid(int numRows, int numCols) {
        grid = new SegCell[numRows][numCols];
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {
                grid[i][j] = new SegCell(i, j, grid);
            }
        }
    }

}
