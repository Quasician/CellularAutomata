package Model;

import java.util.ArrayList;

/**
 * @author Rodrigo Araujo
 *
 * Purpose: An abstract cell class to define
 * common behaviors that can be inherited
 * by multiple subclasses to create new
 * cell types for simulations.
 *
 * Assumptions: Inputting the wrong values would cause the simulation
 * class to fail.
 *
 * Dependencies: All files in the model package, specifically
 * all of the subclass cell classes depend on the abstract cell class
 *
 * Example:
 *
 *          public class NewExCell extends Cell {
 *              ...
 *          }
 */

public abstract class Cell {
    private String name;
    protected int x;
    protected int y;

    public Cell(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    /**
     * Purpose: Method to return the name of the cell
     *
     * Assumptions: Calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: string
     */

    public String getName() {return name;}

    /**
     * Purpose: Method to update the name of the cell
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail or calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: N/A
     */

    public void setName(String input) {this.name = input;}
    private <Type> Type top(Type[][] gridCopy, int x, int y) { return gridCopy[(x+gridCopy.length)%gridCopy.length][(y+1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type top_right(Type[][] gridCopy, int x, int y) { return gridCopy[(x+1+gridCopy.length)%gridCopy.length][(y+1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type top_left(Type[][] gridCopy, int x, int y) { return gridCopy[(x-1+gridCopy.length)%gridCopy.length][(y+1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type bottom(Type[][] gridCopy, int x, int y) { return gridCopy[(x+gridCopy.length)%gridCopy.length][(y-1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type bottom_right(Type[][] gridCopy, int x, int y) { return gridCopy[(x+1+gridCopy.length)%gridCopy.length][(y-1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type bottom_left(Type[][] gridCopy, int x, int y) { return gridCopy[(x-1+gridCopy.length)%gridCopy.length][(y-1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type left(Type[][] gridCopy, int x, int y) { return gridCopy[(x-1+gridCopy.length)%gridCopy.length][(y+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type right(Type[][] gridCopy, int x, int y) { return gridCopy[(x+1+gridCopy.length)%gridCopy.length][(y+gridCopy[0].length)%gridCopy[0].length];}

    /**
     * Purpose: Method to return the 4 torroidal neighbors of a cell
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail or calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: ArrayList of generic cell type (the same as generic type passed into neighbor)
     */

    public <Type> ArrayList<Type> get4NeighborsTorroidal(int x, int y, Type[][] gridCopy, ArrayList<Type> neighbors) {
        for(int i = x-1; i<=x+1;i++) {
            for(int j = y-1; j<=y+1;j++) {
                if((i - x + 1 + j - y + 1) % 2 == 0) {
                    continue;
                }
                else {
                    neighbors.add(gridCopy[(i+gridCopy.length)%gridCopy.length][(j+gridCopy[0].length)%gridCopy[0].length]);
                }
            }
        }
        return neighbors;
    }

    /**
     * Purpose: Method to return the 8 torroidal neighbors of a cell
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail or calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: ArrayList of generic cell type (the same as generic type passed into neighbor)
     */

    public <Type> ArrayList<Type>  get8NeighborsTorroidal(int x, int y, Type[][] gridCopy, ArrayList<Type> neighbors) {
        neighbors.add(top(gridCopy, x, y));
        neighbors.add(bottom(gridCopy, x, y));
        neighbors.add(left(gridCopy, x, y));
        neighbors.add(right(gridCopy, x, y));
        neighbors.add(top_right(gridCopy, x, y));
        neighbors.add(top_left(gridCopy, x, y));
        neighbors.add(bottom_right(gridCopy, x, y));
        neighbors.add(bottom_left(gridCopy, x, y));
        return neighbors;
    }

    /**
     * Purpose: Method to return the 4 finite neighbors of a cell
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail or calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: ArrayList of generic cell type (the same as generic type passed into neighbor)
     */

    public <Type> ArrayList<Type> get4NeighborsFinite(int x, int y, Type[][] gridCopy, ArrayList<Type> neighbors) {
        for(int i = x-1; i<=x+1;i++) {
            for(int j = y-1; j<=y+1;j++) {
                if((i - x + 1 + j - y + 1) % 2 == 0) {
                    continue;
                }
                else if(inGrid(i,j, gridCopy)) {
                    neighbors.add(gridCopy[i][j]);
                }
            }
        }
        return neighbors;
    }

    /**
     * Purpose: Method to return the 8 finite neighbors of a cell
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail or calling this method on an object that is not
     * of the subclass cell would cause it to fail.
     *
     * Return: ArrayList of generic cell type (the same as generic type passed into neighbor)
     */

    public <Type> ArrayList<Type> get8NeighborsFinite(int x, int y, Type[][] gridCopy, ArrayList<Type> neighbors) {
        for(int i = x-1; i<=x+1;i++) {
            for(int j = y-1; j<=y+1;j++) {
                if(i == x && j == y) {
                    continue;
                }
                else if(inGrid(i,j, gridCopy)) {
                    neighbors.add(gridCopy[i][j]);
                }
            }
        }
        return neighbors;
    }

    private boolean inGrid(int rows, int cols, Object[][] gridCopy) {
        if(rows >= 0 && rows < gridCopy.length && cols >= 0 && cols < gridCopy[0].length) {
            return true;
        }
        return false;
    }
}
