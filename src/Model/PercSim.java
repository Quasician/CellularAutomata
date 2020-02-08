package Model;

import Model.Simulation;

import java.util.HashMap;

public class PercSim extends Simulation {

    private double percentEmpty;
    private double percentBlocked;

    public PercSim(double rows, double cols, int width, int height, HashMap<String,Double> params)
    {
        super((int)rows, (int)cols, width,height, params);
        initParams();
        createGrid((int)rows,(int)cols);
        setUpHashMap();
    }

    @Override
    public void initParams() {
        percentEmpty =getParams().get("percentEmpty");
        percentBlocked = getParams().get("percentBlocked");
    }

    public void createGrid(int numRows, int numCols) {
        createGrid(new String[numRows][numCols]);
        for(int i = 0; i<getRows();i++)
        {
            for(int j = 0; j<getCols();j++)
            {
                double choice = Math.random();
                if (choice<=percentEmpty) {
                    setCell(i,j,"empty");
                }
                else{
                    setCell(i,j,"blocked");
                }
            }
        }
        setCell(25,25,"full");
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
        String[] neighbors = get8Neighbors(x,y, gridCopy);
        int sum = 0;
        for(int i = 0; i<neighbors.length;i++)
        {
            if(neighbors[i].equals("full"))
            {
                sum++;
            }
        }

        if(gridCopy[x][y].equals("empty") && sum > 0)
        {
            setCell(x,y,"full");
        }
    }
    public void setUpHashMap()
    {
        createColorMap(new HashMap<>());
        addToColorMap("full", "deepskyblue");
        addToColorMap("empty", "white");
        addToColorMap("blocked", "black");

    }
}
