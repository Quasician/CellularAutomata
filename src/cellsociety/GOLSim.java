package cellsociety;

import java.util.ArrayList;

public class GOLSim extends Simulation{

    public GOLSim(int row, int col, int width, int height)
    {
        super(row, col, width,height);
    }

    public void createGrid(int numRows, int numCols) {
        grid = new ArrayList<>();
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {
                grid.get(i).add(new GOLCell(i, j, numRows, numCols,State.Dead));
            }
        }

        //updating GOLCell neighbors
        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {
                grid.get(i).get(j).get4Neighbors(grid);
            }
        }
    }

}
