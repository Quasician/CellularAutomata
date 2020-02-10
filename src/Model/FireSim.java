package Model;


import Model.Simulation;

import java.util.HashMap;

public class FireSim extends Simulation {

    private double probCatch;
    private double percentBurning;


    public FireSim(int width, int height, HashMap<String,Double> params)
    {
        super((int)(params.get("grid_height")*10)/10,(int)(params.get("grid_width")*10/10), width,height, params);
        initParams();
        createGrid(getRows(),getCols());
        setUpHashMap();
        setName("fire");
    }

    public FireSim(int width, int height, HashMap<String,Double> params, Simulation sim)
    {
        super((int)(params.get("grid_height")*10)/10,(int)(params.get("grid_width")*10/10), width,height, params);
        initParams();
        createGridFromAnotherSim(sim);
        setUpHashMap();
        setName("fire");
    }

    @Override
    public void initParams() {
        probCatch = getParams().get("probCatch");
        percentBurning = getParams().get("percentBurning");
        initAddToAgentNumberMap("empty");
        initAddToAgentNumberMap("burning");
        initAddToAgentNumberMap("tree");
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
        resetAgentNumbers();

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
        countAgentNumbers();
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
