package Model;

import configuration.GetPropertyValues;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Rodrigo Araujo
 *
 * Purpose: A class that extends the abstract simulation
 * class and implements the rules to create the Rock,
 * Paper, Scissors simulation.
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
 *          RPSSIM exSim = new RPSSIM(30, 30, params);
 *
 */

public class RPSSim extends Simulation{
    private GetPropertyValues properties = new GetPropertyValues();
    private double percentRock;
    private double percentScissors;
    private int defaultThreshold;
//    private String boundary = properties.getPropValues("boundary");
    private RPSCell[][] rpsGrid;

    /**
     * Purpose: RPSSim constructor that defines variables
     * to be used.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail.
     *
     * Return: N/A
     */

    public RPSSim(int width, int height, HashMap<String, Double> params) {
        super((int)(params.get("grid_height")*10)/10,(int)(params.get("grid_width")*10/10), width,height, params);
        initParams();
        createGrid(getRows(), getCols());
        setUpHashMap();
        setName("rps");
    }

    /**
     * Purpose: RPSSim constructor that defines variables
     * to be used and allows user to load a past simulation.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail.
     *
     * Return: N/A
     */

    public RPSSim(int width, int height, HashMap<String,Double> params, Simulation sim){
        super((int)(params.get("grid_height")*10)/10,(int)(params.get("grid_width")*10/10), width,height, params);
        initParams();
        createGridFromAnotherSim(sim);
        initRPSGridFromFile(getRows(),getCols());
        setUpHashMap();
        setName("rps");
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

    public void initParams() {
        defaultThreshold = (int) (getParams().get("threshold") * 10) / 10;
        percentRock = getParams().get("percentRock");
        percentScissors = getParams().get("percentScissors");
        initAddToAgentNumberMap("rock");
        initAddToAgentNumberMap("paper");
        initAddToAgentNumberMap("scissor");
    }

    /**
     * Purpose: Method to create 2D array of cell objects grid by using
     * setCell to set the values by cell location.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail.
     *
     * Return: N/A
     */

    public void createGrid(int rows, int cols) {
        rpsGrid = new RPSCell[rows][cols];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                double choice = Math.random();
                if (choice <= percentRock) {
                    rpsGrid[i][j] = new RPSCell(i, j, "rock", defaultThreshold);
                    rpsGrid[i][j].setNextState(rpsGrid[i][j]);
                    setCell(i, j, "rock");
                }
                else if (choice <= percentRock + percentScissors) {
                    rpsGrid[i][j] = new RPSCell(i, j, "scissor", defaultThreshold);
                    rpsGrid[i][j].setNextState(rpsGrid[i][j]);
                    setCell(i, j, "scissor");
                }
                else {
                    rpsGrid[i][j] = new RPSCell(i, j, "paper", defaultThreshold);
                    rpsGrid[i][j].setNextState(rpsGrid[i][j]);
                    setCell(i, j, "paper");
                }
            }
        }
    }

    private void initRPSGridFromFile(int rows, int cols) {
        rpsGrid = new RPSCell[rows][cols];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                if (getCell(i,j).equals("rock")) {
                    rpsGrid[i][j] = new RPSCell(i, j, "rock", defaultThreshold);
                    rpsGrid[i][j].setNextState(rpsGrid[i][j]);
                }
                else if (getCell(i,j).equals("scissor")) {
                    rpsGrid[i][j] = new RPSCell(i, j, "scissor", defaultThreshold);
                    rpsGrid[i][j].setNextState(rpsGrid[i][j]);
                }
                else {
                    rpsGrid[i][j] = new RPSCell(i, j, "paper", defaultThreshold);
                    rpsGrid[i][j].setNextState(rpsGrid[i][j]);
                }
            }
        }
    }

    /**
     * Purpose: Method to update the individual cells in the
     * 2D array of cell objects grid by using updateCell, which contains
     * the game rules, to set the string value by cell location.
     *
     * Assumptions: Calling this method on an object that is not
     * of the subclass simulation would cause it to fail.
     *
     * Return: N/A
     */

    public void updateGrid() {
        resetAgentNumbers();
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                rpsGrid[i][j] = rpsGrid[i][j].getNextState();
                rpsGrid[i][j].setNextState(null);
            }
        }
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                updateCell(rpsGrid[i][j]);
            }
        }
        updateStringArray();
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

    public void updateCell(RPSCell input) {
        if (input.getName().equals("rock")) {
            updateRock(input);
        }
        else if (input.getName().equals("scissor")) {
            updateScissor(input);
        }
        else {
            updatePaper(input);
        }
    }

    private void updateRock(RPSCell input) {
        int paper = neighborFilter(input, "paper");
        if (paper >= defaultThreshold) {
            input.setNextState(new RPSCell(input.x, input.y, "paper", defaultThreshold));
        }
        else {
            input.setNextState(new RPSCell(input.x, input.y, "rock", defaultThreshold));
        }
    }

    private void updateScissor(RPSCell input) {
        int rock = neighborFilter(input, "rock");
        if (rock >= defaultThreshold) {
            input.setNextState(new RPSCell(input.x, input.y, "rock", defaultThreshold));
        }
        else {
            input.setNextState(new RPSCell(input.x, input.y, "scissor", defaultThreshold));
        }
    }

    private void updatePaper(RPSCell input) {
        int scissor = neighborFilter(input, "scissor");
        if (scissor >= defaultThreshold) {
            input.setNextState(new RPSCell(input.x, input.y, "scissor", defaultThreshold));
        }
        else {
            input.setNextState(new RPSCell(input.x, input.y, "paper", defaultThreshold));
        }
    }

    private int neighborFilter(RPSCell input, String name) {
        int result = 0;
        ArrayList<RPSCell> neighbors = new ArrayList<>();
        neighbors = input.get8NeighborsFinite(input.x, input.y, rpsGrid, neighbors);
        for (RPSCell n : neighbors) {
            if (n.getName().equals(name)) {
                result++;
            }
        }
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
        addToColorMap("rock", "red");
        addToColorMap("paper", "blue");
        addToColorMap("scissor", "yellow");
    }

    private void updateStringArray() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                setCell(i, j, rpsGrid[i][j].getName());
            }
        }
    }
}
