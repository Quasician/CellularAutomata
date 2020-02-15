package Model;

/**
 * @author Rodrigo Araujo
 *
 * Purpose: A class that extends the abstract cell
 * class and implements the necessary variables and
 * methods to create the Ant simulation.
 *
 * Assumptions: Inputting the wrong values would cause the cell
 * class to fail.
 *
 * Dependencies: This subclass is dependent on the abstract
 * cell class.
 *
 * Example:
 *
 *          AntCell exCell = new AntCell(1, 1, "ant", 0, "food", 0);
 *
 */

public class AntCell extends Cell {

    protected int pheromone;
    protected int food;
    private String mission;
    private AntCell nextState;

    /**
     * Purpose: AntCell constructor that defines variables
     * to be used in the cell class.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail.
     *
     * Return: N/A
     */

    public AntCell(int x, int y, String name, int pheromone, String mission, int food) {
        super(x, y, name);
        this.pheromone = pheromone;
        this.mission = mission;
        this.food = food;
    }

    /**
     * Purpose: Method to return the mission state of the ant cell.
     * This determines the direction the ant moves.
     *
     * Assumptions: Calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: String
     */

    public String getMission() {return mission;}

    /**
     * Purpose: Method to update the future state of the cell.
     * This makes it easier to update the cell in the simulation.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail or calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: N/A
     */

    public void setNextState(AntCell input) {this.nextState = input;}

    /**
     * Purpose: Method to return the future state of the cell.
     * This makes it easier to update the cell in the simulation.
     *
     * Assumptions: Calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: AntCell
     */

    public AntCell getNextState() {return nextState;}

    /**
     * Purpose: Method to increase the number of phero in a cell
     * by a determined input in the simulation.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail or calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: N/A
     */

    public int increasePhero(int input) {return this.pheromone += input;}
}
