package Model;

/**
 * @author Rodrigo Araujo
 *
 * Purpose: A class that extends the abstract cell
 * class and implements the necessary variables and
 * methods to create the Sugar simulation.
 *
 * Assumptions: Inputting the wrong values would cause the cell
 * class to fail.
 *
 * Dependencies: This subclass is dependent on the abstract
 * cell class.
 *
 * Example:
 *
 *          SugarCell exCell = new SugarCell(1, 1, "agent", 3, 3, 1);
 *
 */

public class SugarCell extends Cell{

    private SugarCell nextState;
    protected int capacity;
    protected int sugar;
    protected int metabolism;

    /**
     * Purpose: SugarCell constructor that defines variables
     * to be used in the cell class.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail.
     *
     * Return: N/A
     */

    public SugarCell(int x, int y, String name, int capacity, int sugar, int metabolism) {
        super(x, y, name);
        this.capacity = capacity;
        this.sugar = sugar;
        this.metabolism = metabolism;
    }

    /**
     * Purpose: Method to return the future state of the cell.
     * This makes it easier to update the cell in the simulation.
     *
     * Assumptions: Calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: SugarCell
     */

    public SugarCell getNextState() {return nextState;}

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

    public void setNextState(SugarCell input) {nextState = input;}

    /**
     * Purpose: Method to increase the number of sugar of an agent
     * by a determined input in the simulation.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail or calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: N/A
     */

    public void increaseSugar(int input) {sugar += input;}

    /**
     * Purpose: Method to decrease the number of sugar of an agent
     * by a determined input in the simulation.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail or calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: N/A
     */

    public void decreaseSugar(int input) {sugar -= input;}

}
