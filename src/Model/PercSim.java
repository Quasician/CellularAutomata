package Model;

import java.util.HashMap;

/**
 * @author Rodrigo Araujo
 *
 * Purpose: A class that extends the abstract simulation
 * class and implements the rules to create the Percolate
 * simulation.
 *
 * Assumptions: Typically, negative values would cause
 * the simulation method to fail; however, we catch
 * negative values and print out an error. Besides that,
 * inputting the wrong values would cause the simulation
 * class to fail.
 *
 * Dependencies: This subclass is dependent on the abstract
 * simulation class.
 *
 * Example:
 *
 *          PercSim exSim = new PercSim(30, 30, params);
 *
 */

public class PercSim extends Simulation {

    private double percentEmpty;
    private double percentBlocked;

    /**
     * Purpose: PercSim constructor that defines variables
     * to be used.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail.
     *
     * Return: N/A
     */

    public PercSim(int width, int height, HashMap<String,Double> params) {
        super((int)(params.get("grid_height")*10)/10,(int)(params.get("grid_width")*10/10), width,height, params);
        initParams();
        createGrid(getRows(),getCols());
        setUpHashMap();
        setName("percolate");
    }

    /**
     * Purpose: PercSim constructor that defines variables
     * to be used and allows user to load a past simulation.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail.
     *
     * Return: N/A
     */

    public PercSim(int width, int height, HashMap<String,Double> params, Simulation sim)
    {
        super((int)(params.get("grid_height")*10)/10,(int)(params.get("grid_width")*10/10), width,height, params);
        initParams();
        createGridFromAnotherSim(sim);
        setUpHashMap();
        setName("percolate");
    }

    /**
     * Purpose: Method to set the starting configuration values
     * for the simulation.
     *
     * Assumptions: Calling this method on an object that is not
     * of the subclass simulation would cause it to fail.
     *
     * Return: N/A
     */

    @Override
    public void initParams() {
        percentEmpty =getParams().get("percentEmpty");
        percentBlocked = getParams().get("percentBlocked");
        initAddToAgentNumberMap("empty");
        initAddToAgentNumberMap("blocked");
        initAddToAgentNumberMap("full");
    }

    /**
     * Purpose: Method to create 2D string array grid by using
     * setCell to set the string value by cell location.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail.
     *
     * Return: N/A
     */

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

    /**
     * Purpose: Method to update the individual cells in the
     * 2D string array grid by using updateCell, which contains
     * the game rules, to set the string value by cell location.
     *
     * Assumptions: Calling this method on an object that is not
     * of the subclass simulation would cause it to fail.
     *
     * Return: N/A
     */

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

    /**
     * Purpose: Method that contains the rules to
     * update the cells by.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail or calling this method on an object that is not
     * of the subclass simulation would cause it to fail.
     *
     * Return: N/A
     */

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

    /**
     * Purpose: Method that updates the updates the color scheme
     * for different cell names.
     *
     * Assumptions: Calling this method on an object that is not
     * of the subclass simulation would cause it to fail.
     *
     * Return: N/A
     */

    public void setUpHashMap() {
        createColorMap(new HashMap<>());
        addToColorMap("full", "deepskyblue");
        addToColorMap("empty", "white");
        addToColorMap("blocked", "black");

    }
}
