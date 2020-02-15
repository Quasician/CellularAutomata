package Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Rodrigo Araujo, Thomas Chemmanoor
 *
 * Purpose: A class that extends the abstract simulation
 * class and implements the rules to create the Segregation
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
 *          SegSim exSim = new SegSim(30, 30, params);
 *
 */

public class SegSim extends Simulation {

    private double probSatisfy;
    private double percentX;
    private double percentO;
    private ArrayList<Integer> x_empty_cells;
    private ArrayList<Integer> y_empty_cells;

    /**
     * Purpose: SegSim constructor that defines variables
     * to be used.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail.
     *
     * Return: N/A
     */

    public SegSim(int width, int height, HashMap<String,Double> params) {
        super((int)(params.get("grid_height")*10)/10,(int)(params.get("grid_width")*10/10), width,height, params);
        initParams();
        createGrid(getRows(),getCols());
        setUpHashMap();
        setName("segregation");
    }

    /**
     * Purpose: SegSim constructor that defines variables
     * to be used and allows user to load a past simulation.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail.
     *
     * Return: N/A
     */

    public SegSim(int width, int height, HashMap<String,Double> params, Simulation sim) {
        super((int)(params.get("grid_height")*10)/10,(int)(params.get("grid_width")*10/10), width,height, params);
        initParams();
        createGridFromAnotherSim(sim);
        setUpHashMap();
        setName("segregation");
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
        probSatisfy = getParams().get("probSatisfy");
        percentO = getParams().get("percentO");
        percentX = getParams().get("percentX");
        initAddToAgentNumberMap("x");
        initAddToAgentNumberMap("o");
        initAddToAgentNumberMap("empty");
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
                if (choice<=percentX) {
                    setCell(i,j, "x");
                }
                else if (choice<=percentX+percentO){
                    setCell(i,j, "o");
                }
                else
                {
                    setCell(i,j, "empty");
                }
            }
        }
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
        x_empty_cells = new ArrayList<>();
        y_empty_cells = new ArrayList<>();
        generateEmptyCells(gridCopy);
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
        double satisfaction;
        int sameCount = 0;
        int total = 0;
        if (gridCopy[x][y] == "empty") {
            return;
        }
        for(int i = 0; i<neighbors.length;i++)
        {
            if(neighbors[i].equals(gridCopy[x][y]))
            {
                sameCount++;
            }
            if(!neighbors[i].equals("empty")) {
                total++;
            }
        }
        if (total == 0) {
            return;
        }
        satisfaction = ((double) sameCount) / total;
        if (satisfaction < probSatisfy && x_empty_cells.size() > 0) {
            ArrayList<Integer> temp = chooseAnEmptyCell(gridCopy);
            setCell(temp.get(0),temp.get(1),gridCopy[x][y]);
            setCell(x,y,"empty");
        }
    }

    private void generateEmptyCells(String[][] gridCopy) {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                if (gridCopy[i][j].equals("empty")) {
                    x_empty_cells.add(i);
                    y_empty_cells.add(j);
                }
            }
        }
    }

    private ArrayList<Integer> chooseAnEmptyCell(String[][] gridCopy) {
        ArrayList<Integer> result = new ArrayList<>();
        int index = (int) (x_empty_cells.size() * Math.random());
        result.add(x_empty_cells.get(index));
        result.add(y_empty_cells.get(index));
        x_empty_cells.remove(index);
        y_empty_cells.remove(index);
        return result;
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
        addToColorMap("empty", "white");
        addToColorMap("x", "red");
        addToColorMap("o", "blue");

    }
}
