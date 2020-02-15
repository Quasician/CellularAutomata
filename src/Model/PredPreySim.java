package Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Rodrigo Araujo, Thomas Chemmanoor
 *
 * Purpose: A class that extends the abstract simulation
 * class and implements the rules to create the Predator
 * Prey simulation.
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
 *          PredPreySim exSim = new PredPreySim(30, 30, params);
 *
 */

public class PredPreySim extends Simulation {

    private double breedThreshFish;
    private double breedThreshShark;
    private double defaultSharkEnergy;
    private double defaultFishEnergy;
    private double percentFish;
    private double percentSharks;
    private PredPreyCell[][] organismGrid;
    private ArrayList<PredPreyCell> fishThatNeedToMove;
    private ArrayList<PredPreyCell> sharksThatNeedToMove;

    /**
     * Purpose: PredPreySim constructor that defines variables
     * to be used.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail.
     *
     * Return: N/A
     */

    public PredPreySim(int width, int height, HashMap<String,Double> params) {
        super((int)(params.get("grid_height")*10)/10,(int)(params.get("grid_width")*10/10), width,height, params);
        initParams();
        createGrid(getRows(),getCols());
        setUpHashMap();
        setName("pred_prey");
    }

    /**
     * Purpose: PredPreySim constructor that defines variables
     * to be used and allows user to load a past simulation.
     *
     * Assumptions: Inputting the wrong values would cause it
     * to fail.
     *
     * Return: N/A
     */

    public PredPreySim(int width, int height, HashMap<String,Double> params, Simulation sim) {
        super((int)(params.get("grid_height")*10)/10,(int)(params.get("grid_width")*10/10), width,height, params);
        initParams();
        createGridFromAnotherSim(sim);
        initPredPreyGridFromFile(getRows(),getCols());
        setUpHashMap();
        setName("pred_prey");
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
        breedThreshFish = getParams().get("breedThreshFish");
        breedThreshShark = getParams().get("breedThreshShark");
        defaultSharkEnergy = getParams().get("defaultSharkEnergy");
        defaultFishEnergy = getParams().get("defaultFishEnergy");
        percentFish = getParams().get("percentFish");
        percentSharks = getParams().get("percentSharks");
        initAddToAgentNumberMap("shark");
        initAddToAgentNumberMap("fish");
        initAddToAgentNumberMap("kelp");
    }



    private void initPredPreyGridFromFile(int numRows, int numCols) {
        organismGrid = new PredPreyCell[numRows][numCols];
        for(int i = 0; i<getRows();i++) {
            for(int j = 0; j<getCols();j++) {
                if (getCell(i,j).equals("fish")) {
                    organismGrid[i][j] = new PredPreyCell(i,j,"fish",0, defaultFishEnergy);
                    organismGrid[i][j].setNextState(new PredPreyCell(i,j,"fish",0,defaultFishEnergy));
                }
                else if (getCell(i,j).equals("kelp")) {
                    organismGrid[i][j] = new PredPreyCell(i,j,"kelp",0,0);
                    organismGrid[i][j].setNextState(new PredPreyCell(i,j,"kelp",0,0));
                }
                else {
                    organismGrid[i][j] = new PredPreyCell(i,j,"shark", 0, defaultSharkEnergy);
                    organismGrid[i][j].setNextState(new PredPreyCell(i,j,"shark", 0, defaultSharkEnergy));
                }
            }
        }
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

    public void createGrid(int numRows, int numCols) {
        organismGrid = new PredPreyCell[numRows][numCols];
        for(int i = 0; i<getRows();i++) {
            for(int j = 0; j<getCols();j++) {
                double choice = Math.random();
                //System.out.println(choice);
                if (choice<=percentFish) {
                    organismGrid[i][j] = new PredPreyCell(i,j,"fish",0, defaultFishEnergy);
                    organismGrid[i][j].setNextState(new PredPreyCell(i,j,"fish",0,defaultFishEnergy));
                    setCell(i,j,"fish");
                }
                else if (choice>=percentFish+percentSharks) {
                    organismGrid[i][j] = new PredPreyCell(i,j,"kelp",0,0);
                    organismGrid[i][j].setNextState(new PredPreyCell(i,j,"kelp",0,0));
                    setCell(i,j,"kelp");
                }
                else {
                    organismGrid[i][j] = new PredPreyCell(i,j,"shark", 0, defaultSharkEnergy);
                    organismGrid[i][j].setNextState(new PredPreyCell(i,j,"shark", 0, defaultSharkEnergy));
                    setCell(i,j,"shark");
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

        sharksThatNeedToMove = new ArrayList<>();
        fishThatNeedToMove = new ArrayList<>();

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                organismGrid[i][j] = organismGrid[i][j].getNextState();
                organismGrid[i][j].setNextState(null);
            }
        }

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                updateCell(i,j);
            }
        }

        moveAllSharks();
        moveAllFish();
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

    public void updateCell(int x, int y) {
        if(organismGrid[x][y].getName().equals("fish")) {
            //System.out.println("FISH at X: "+ x + "  Y: "+ y + "  Lives: "+organismGrid[x][y].getLives());
            fishThatNeedToMove.add(organismGrid[x][y]);
        }
        if(organismGrid[x][y].getName().equals("shark")) {
            //System.out.print(organismGrid[x][y].getEnergy());
            sharksThatNeedToMove.add(organismGrid[x][y]);
        }
//        if(x==25 && y==25)
//        {
//            System.out.println("Current State: "+organismGrid[25][25].getName());
//            System.out.println("Energy: "+organismGrid[25][25].getEnergy());
//            //System.out.println("Next State: "+organismGrid[25][25].getNextState().getName());
//        }
        if(organismGrid[x][y].getName().equals("kelp")|| (organismGrid[x][y].getName().equals("shark") && organismGrid[x][y].getEnergy()<=0)) {
            if(organismGrid[x][y].getName().equals("shark")) {
                sharksThatNeedToMove.remove(organismGrid[x][y]);
            }
            organismGrid[x][y].setNextState(new PredPreyCell(x,y,"kelp",0,0));
        }
        updateOrganism(organismGrid[x][y]);
    }

//    public void moveOrganism(int x, int y, Organism[][] organismGridCopy) {
//        organismGridCopy[x][y].move(x,y,organismGrid,organismGridCopy, emptyCells);
//    }

    private void updateOrganism(PredPreyCell org) {
        org.increaseLives();
        if(org.getName().equals("shark")) {
            org.decreaseEnergy();
        }
    }

    private void updateStringArray() {
        for(int i = 0; i<getRows();i++) {
            for(int j = 0; j<getCols();j++) {
                setCell(i,j, organismGrid[i][j].getName());
            }
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
        addToColorMap("fish", "blue");
        addToColorMap("shark", "gray");
        addToColorMap("kelp", "black");
    }

    private void moveAllSharks() {
        while (sharksThatNeedToMove.size()>0) {
            int sharkListIndex = (int)(Math.random()*sharksThatNeedToMove.size());
            PredPreyCell current = sharksThatNeedToMove.get(sharkListIndex);
            sharksThatNeedToMove.remove(current);


            ArrayList<PredPreyCell> fishList = current.getFish(current.x, current.y, organismGrid);
            ArrayList<PredPreyCell> kelpList = current.getKelpAndFutureEmpty(current.x, current.y, organismGrid);

            if (fishList.size() > 0) {
                int fishListIndex = (int)(Math.random()*fishList.size());
                fishThatNeedToMove.remove(fishList.get(fishListIndex));
                eatFish(current, fishList.get(fishListIndex));
            }
            else if (kelpList.size() > 0) {
                int kelpListIndex = (int)(Math.random()*kelpList.size());
                sharkMovesToKelpCell(current, kelpList.get(kelpListIndex));
            }
            else {
                current.setNextState(current);
            }
        }
    }

    private void eatFish(PredPreyCell source, PredPreyCell destination) {
        double currentEnergy = source.getEnergy();
        String name = source.getName();
        checkBreed(source);
        destination.setNextState(new PredPreyCell(destination.x, destination.y, name, source.getLives(), currentEnergy+defaultFishEnergy));
    }

    private void sharkMovesToKelpCell(PredPreyCell source, PredPreyCell destination) {
        double currentEnergy = source.getEnergy();
        String name = source.getName();
        checkBreed(source);
        destination.setNextState(new PredPreyCell(destination.x, destination.y, name, source.getLives(), currentEnergy));
    }

    private void fishMovesToKelpCell(PredPreyCell source, PredPreyCell destination) {
        double currentEnergy = source.getEnergy();
        String name = source.getName();
        checkBreed(source);
        destination.setNextState(new PredPreyCell(destination.x, destination.y, name, source.getLives(), currentEnergy));
    }

    private void checkBreed(PredPreyCell source) {

        if(source.getName().equals("fish") && source.getLives()>breedThreshFish) {
            source.setNextState(new PredPreyCell(source.x, source.y, source.getName(), 0, defaultFishEnergy));
            source.setLife(0);
        }
        else if(source.getName().equals("shark") && source.getLives()>breedThreshShark && source.getEnergy() > (defaultSharkEnergy / 2)) {
            source.setNextState(new PredPreyCell(source.x, source.y, source.getName(), 0, defaultSharkEnergy));
            source.setLife(0);
        }
         else {
            //System.out.println("YEET");
            source.setNextState(new PredPreyCell(source.x, source.y, "kelp", 0, 0));
        }
    }

    private void moveAllFish() {
        while (fishThatNeedToMove.size() > 0) {
            int fishListIndex = (int)(Math.random()*fishThatNeedToMove.size());
            PredPreyCell current = fishThatNeedToMove.get(fishListIndex);
            fishThatNeedToMove.remove(current);

            ArrayList<PredPreyCell> kelpList = current.getKelpAndFutureEmpty(current.x,current.y,organismGrid);

            if (kelpList.size() > 0) {
                int kelpListIndex = (int)(Math.random()*kelpList.size());
                fishMovesToKelpCell(current, kelpList.get(kelpListIndex));
            } else {
                current.setNextState(current);
            }
        }
    }

}
