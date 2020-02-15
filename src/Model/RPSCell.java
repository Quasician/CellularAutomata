package Model;

/**
 * @author Rodrigo Araujo
 *
 * Purpose: A class that extends the abstract cell
 * class and implements the necessary variables and
 * methods to create the Rock, Paper, Scissors simulation.
 *
 * Assumptions: Inputting the wrong values would cause the cell
 * class to fail.
 *
 * Dependencies: This subclass is dependent on the abstract
 * cell class.
 *
 * Example:
 *
 *          RPSCell exCell = new RPSCell(1, 1, "rock", 3);
 *
 */

public class RPSCell extends Cell{

    private RPSCell nextState;
    protected int threshold;

    /**
     * Purpose: RPSCell constructor that defines variables
     * to be used in the cell class.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail.
     *
     * Return: N/A
     */

    public RPSCell(int x, int y, String name, int threshold) {
        super(x, y, name);
        this.threshold = threshold;
    }

    /**
     * Purpose: Method to return the future state of the cell.
     * This makes it easier to update the cell in the simulation.
     *
     * Assumptions: Calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: RPSCell
     */

    public RPSCell getNextState() {return nextState;}

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

    public void setNextState(RPSCell input) {nextState = input;}
}
