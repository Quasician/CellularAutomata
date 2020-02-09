package Model;

import Model.Simulation;

import java.util.HashMap;

public class GOLSim extends Simulation {

    private double percentAlive;
    private double aliveNum;
    private double deadNum;

    public GOLSim(double rows, double cols, int width, int height, HashMap<String,Double> params)
    {
        super((int)rows, (int)cols, width,height, params);
        initParams();
        createGrid((int)rows,(int)cols);
        setUpHashMap();
    }

    public void createGrid(int numRows, int numCols) {
        createGrid( new String[numRows][numCols]);
        for(int i = 0; i<getRows();i++)
        {
            for(int j = 0; j<getCols();j++)
            {
                double choice = Math.random();
                if (choice<=percentAlive) {
                    setCell(i,j,"alive");
                }
                else{
                    setCell(i,j,"dead");
                }
            }
        }
    }


    public void updateGrid() {
        aliveNum=0;
        deadNum =0;
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
        updateAgentNumberMap("alive",aliveNum);
        updateAgentNumberMap("dead",deadNum);
    }

    @Override
    public void initParams() {

        percentAlive = getParams().get("percentAlive");
        initAddToAgentNumberMap("alive");
        initAddToAgentNumberMap("dead");
    }

    public void updateCell(int x, int y, String[][]gridCopy) {
        String[] neighbors = get8Neighbors(x,y, gridCopy);
        int sum = 0;
        for(int i = 0; i<neighbors.length;i++)
        {
            if(neighbors[i].equals("alive"))
            {
                sum++;
            }
        }

        if((gridCopy[x][y].equals("alive") && (sum == 2 || sum == 3)) || (gridCopy[x][y].equals("dead") && sum == 3)) {
            setCell(x, y, "alive");
            aliveNum++;
        }
        else {
            setCell(x, y, "dead");
            deadNum++;
        }
    }

    public void setUpHashMap()
    {
        createColorMap(new HashMap<>());
        addToColorMap("alive", "black");
        addToColorMap("dead", "white");

    }
}
