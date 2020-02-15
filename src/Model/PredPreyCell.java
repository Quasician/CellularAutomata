package Model;

import java.util.ArrayList;

/**
 * @author Rodrigo Araujo
 *
 * Purpose: A class that extends the abstract cell
 * class and implements the necessary variables and
 * methods to create the Predator Prey simulation.
 *
 * Assumptions: Inputting the wrong values would cause the simulation
 * class to fail.
 *
 * Dependencies: This subclass is dependent on the abstract
 * cell class.
 *
 * Example:
 *
 *          PredPreyCell exCell = new PredPreyCell(1, 1, "shark", 3.0, 3.0);
 *
 */

public class PredPreyCell extends Cell{

    private PredPreyCell nextState;
    private double lives;
    private double energy;

    /**
     * Purpose: PredPreyCell constructor that defines variables
     * to be used in the cell class.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail.
     *
     * Return: N/A
     */

    public PredPreyCell(int x, int y, String name, double lives, double energy) {
        super(x, y, name);
        this.lives = lives;
        this.energy = energy;
    }

    public ArrayList<PredPreyCell> getFish(int x, int y, PredPreyCell[][] gridCopy) {
        ArrayList<PredPreyCell> neighbors = new ArrayList<>();
        neighbors = get4NeighborsTorroidal(x,y,gridCopy, neighbors);
        ArrayList<PredPreyCell> fishList = new ArrayList<PredPreyCell>();
        for(PredPreyCell i: neighbors) {
            if(i.getName().equals("fish")) {
                fishList.add(i);
            }
        }
        return fishList;
    }

    public ArrayList<PredPreyCell> getKelpAndFutureEmpty(int x, int y, PredPreyCell[][] gridCopy) {
        ArrayList<PredPreyCell> neighbors = new ArrayList<>();
        neighbors = get4NeighborsTorroidal(x,y,gridCopy, neighbors);
        ArrayList<PredPreyCell> kelpAndFutureEmptyList = new ArrayList<PredPreyCell>();
        for(PredPreyCell i: neighbors) {
            if(i.getName().equals("kelp") && (i.getNextState() == null || i.getNextState().getName().equals("kelp"))) {
                kelpAndFutureEmptyList.add(i);
            }
        }
        return kelpAndFutureEmptyList;
    }

    /**
     * Purpose: Method to return the future state of the cell.
     * This makes it easier to update the cell in the simulation.
     *
     * Assumptions: Calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: PredPreyCell
     */

    public PredPreyCell getNextState() {return nextState;}

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

    public void setNextState(PredPreyCell input)
    {
        nextState = input;
    }

    /**
     * Purpose: Method to increase the number of lives of an organism
     * by 1 in the simulation.
     *
     * Assumptions: Calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: N/A
     */

    public void increaseLives() {lives++;}

    /**
     * Purpose: Method to decrease the number of lives of an organism
     * by 1 in the simulation.
     *
     * Assumptions: Calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: N/A
     */

    public void decreaseEnergy() {energy--;}

    /**
     * Purpose: Method to return the number of energy of an organism
     * in the simulation.
     *
     * Assumptions: Calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: double
     */

    public double getEnergy() {return energy;}

    /**
     * Purpose: Method to return the number of lives of an organism
     * in the simulation.
     *
     * Assumptions: Calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: double
     */

    public double getLives() {return lives;}

    /**
     * Purpose: Method to update the number of lives of an organism
     * in the simulation.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail or calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: N/A
     */

    public void setLife(int input) {this.lives = input;}
}
