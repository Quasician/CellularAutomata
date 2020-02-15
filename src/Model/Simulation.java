package Model;


import javafx.scene.control.Alert;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rodrigo Araujo, Thomas Chemmanoor
 *
 * Purpose: An abstract simulation class to define
 * common behaviors that can be inherited
 * by multiple subclasses to create new
 * simulations.
 *
 * Assumptions: Typically, negative values would cause
 * the simulation method to fail; however, we catch
 * negative values and print out an error. Besides that,
 * inputting the wrong values would cause the simulation
 * class to fail.
 *
 * Dependencies: All files in the model package, specifically
 * all of the subclass simulations depend on the simulation class
 *
 * Example:
 *
 *          public class NewExSim extends Simulation {
 *              ...
 *          }
 *
 */

public abstract class Simulation {

    private int simRows, simCols;
    private int simWidth, simHeight;
    private String name;
    private HashMap<String, String> colorMap;
    private HashMap<String, Double> params;
    private HashMap<String, Double> agentNumbers;

    private String[][] grid;

    /**
     * Simulation constructor that defines variables
     * to be used within the simulations.
     */

    public Simulation(int rows, int cols, int width, int height, HashMap<String, Double> params){
        this.simRows = rows;
        this.simCols = cols;
        this.simWidth = width;
        this.simHeight = height;
        this.params = params;
        agentNumbers = new HashMap<>();
        try {
            createGrid(new String[rows][cols]);
        }
        catch(NegativeArraySizeException e)
        {
            showError("Invalid Grid Dimensions");
        }
    }

    /**
     * Abstract method to allow each simulation subclass
     * to inherit their respective variables from the xml
     */

    public abstract void initParams();

    /**
     * Method to return the number of rows in the simulation
     */

    public int getRows(){return simRows;}

    /**
     * Method to update the name of the simulation
     */

    public void setName(String name){this.name = name;}

    /**
     * Method to return the name of the simulation
     */

    public String getName(){return name;}

    /**
     * Method to return the number of columns in the simulation
     */

    public int getCols(){return simCols;}

    /**
     * Method to return the pixel width of the simulation window
     */

    public int getSimWidth(){return simWidth;}

    /**
     * Method to return the pixel height of the simulation window
     */

    public int getSimHeight(){return simHeight;}

    /**
     * Method to return the parameters Hashmap that stores the initial values for the simulation
     */

    public HashMap<String, Double> getParams(){return params;}

    /**
     * Method to overwrite the colormap Hashmap that stores the color values for the cells of the simulation
     */

    public void createColorMap(HashMap<String, String> colorMap){
        this.colorMap = colorMap;
    }

    /**
     * Method to add a new key to the agentNumbers Hashmap, which stores the number of cells in a simulation
     */

    public void initAddToAgentNumberMap(String type)
    {
        agentNumbers.putIfAbsent(type, 0.0);
    }

    /**
     * Method to update a key in the agentNumbers Hashmap, which stores the number of cells in a simulation
     */

    public void updateAgentNumberMap(String type, Double num)
    {
        agentNumbers.put(type, num);
    }

    /**
     * Method to return the agentNumbers Hashmap, which stores the number of cells in a simulation
     */

    public HashMap<String, Double> getAgentNumberMap(){return agentNumbers;}

    /**
     * Method to update the 2D array grid, which stores name values of each cell from a simulation
     */

    public void createInitialGridFromFile(String[][] grid)
    {
        this.grid = grid;
    }

    /**
     * Method to update the 2D array grid, using any simulation subclass
     */

    public void createGridFromAnotherSim(Simulation sim) {
        for(int i = 0; i<getRows();i++) {
            for(int j = 0; j<getCols();j++) {
                grid[i][j] = sim.getCell(i,j);
            }
        }
    }

    /**
     * Method to count the cells in the agentNumbers Hashmap
     */

    public void countAgentNumbers() {
        for(int i = 0; i<getRows();i++) {
            for(int j = 0; j<getCols();j++) {
                updateAgentNumberMap(getCell(i,j),getAgentNumberMap().get(getCell(i,j))+1);
            }
        }
    }

    /**
     * Method to reset the count of all cells in the agentNumbers Hashmap
     */

    public void resetAgentNumbers() {
        for(Map.Entry<String,Double> entry : getAgentNumberMap().entrySet()) {
            updateAgentNumberMap(entry.getKey(),0.0);
        }
    }

    /**
     * Method to add a new color key to the colormap Hashmap
     */

    public void addToColorMap(String type, String color)
    {
        colorMap.putIfAbsent(type, color);
    }

    /**
     * Method to update the 2D string array grid
     */

    public void createGrid(String[][] grid)
    {
        this.grid = grid;
    }

    /**
     * Method to return the string value of a specific cell in the grid
     */

    public String getCell(int x, int y)
    {
        return grid[x][y];
    }

    /**
     * Method to update the string value of a specific cell in the grid
     */

    public void setCell(int x, int y, String value)
    {
        grid[x][y] = value;
    }

    /**
     * Abstract method for the subclass simulations to create a grid
     * using either a 2D array of strings or a 2D array of cells
     */

    public abstract void createGrid(int numRows, int numCols);

    /**
     * Abstract method for the subclass simulations to update
     * their grids
     */

    public abstract void updateGrid();

    /**
     * Abstract method for the subclass simulations to set the color
     * schemes for the cell types
     */

    public abstract void setUpHashMap();

    /**
     * Method to return the colorMap Hashmap of the simulation
     */

    public HashMap<String, String> getColorMap() {return colorMap;}

    /**
     * Method to check if an x,y value exists within the grid
     */

    public boolean inGrid(int rows, int cols) {
        if(rows>=0 && rows <simRows && cols>=0 && cols<simCols) {
            return true;
        }
        return false;
    }

    /**
     * Method to return an array of the 4 finite neighbor cells in the grid
     * for a predetermined x,y location
     */

    public String[] get4Neighbors(int x, int y, String[][] gridCopy) {
        String[] neighbors = new String[4];
        int count = 0;
        for(int i = x-1; i<=x+1;i++) {
            for(int j = y-1; j<=y+1;j++) {
                if((i - x + 1 + j - y + 1) % 2 == 0) {
                    continue;
                }
                else {
                    if(inGrid(i,j)) {
                        neighbors[count] = gridCopy[i][j];

                    }
                    else {
                        neighbors[count] = "outOfBounds";
                    }
                    count++;
                }
            }
        }
        return neighbors;
    }

    /**
     * Method to return an array of the 8 finite neighbor cells in the grid
     * for a predetermined x,y location
     */

    public String[] get8Neighbors(int x, int y, String[][] gridCopy) {
        String[] neighbors = new String[8];
        int count = 0;
        for(int i = x-1; i<=x+1;i++) {
            for(int j = y-1; j<=y+1;j++) {
                if(i == x && j == y) {
                    continue;
                }
                else {
                    if(inGrid(i,j)) {
                        neighbors[count] = gridCopy[i][j];

                    }
                    else {
                        neighbors[count] = "outOfBounds";
                    }
                    count++;
                }
            }
        }
        return neighbors;
    }

    private void showError(String mes)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mes);
        alert.showAndWait();
    }

}
