package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class FireSim extends Simulation{

    private double probCatch;
    private double percentBurning;

    public FireSim(double rows, double cols, int width, int height, HashMap<String,Double> params)
    {
        super((int)rows, (int)cols, width,height, params);
        initParams();
        createGrid((int)rows,(int)cols);
        setUpHashMap();
    }

    @Override
    public void initParams() {
        probCatch = getParams().get("probCatch");
        percentBurning = getParams().get("percentBurning");
    }

    public void createGrid(int numRows, int numCols) {
       createGrid(new String[numRows][numCols]);
        for(int i = 0; i<getRows();i++)
        {
            for(int j = 0; j<getCols();j++)
            {
                double choice = Math.random();
                if (choice<=percentBurning) {
                    setCell(i,j,"burning");
                }
                else{
                    setCell(i,j,"tree");
                }
            }
        }
    }
    public void updateGrid() {
        String[][] gridCopy = new String[getRows()][getCols()];
        for(int i = 0; i<getRows();i++)
        {
            for(int j = 0; j<getCols();j++)
            {
                gridCopy[i][j] = getCell(i,j);
            }
        }
        for(int i = 0; i<getRows();i++)
        {
            for(int j = 0; j<getCols();j++)
            {
                updateCell(i,j,gridCopy);
            }
        }
    }

    public void updateCell(int x, int y, String[][]gridCopy) {
        String[] neighbors = get4Neighbors(x,y, gridCopy);
        int sum = 0;
        for(int i = 0; i<neighbors.length;i++)
        {
            if(neighbors[i].equals("burning"))
            {
                sum++;
            }
        }
        if (gridCopy[x][y].equals("burning")) {
            setCell(x,y,"empty");
        }
        if (gridCopy[x][y].equals("tree") && sum > 0) {
           if(Math.random() < probCatch) {
               setCell(x,y,"burning");
           }
        }

    }
    public void setUpHashMap()
    {
        createColorMap(new HashMap<>());
        addToColorMap("empty", "yellow");
        addToColorMap("tree", "green");
        addToColorMap("burning", "red");
    }

}
