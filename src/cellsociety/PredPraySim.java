package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PredPraySim extends Simulation {

    private int breedThreshFish = 3;
    private int breedThreshShark = 3;
    private int defaultSharkEnergy = 10;
    private int defaultFishEnergy = 1;
    private Organism[][] organismGrid;
    private ArrayList<Organism> emptyCells;
    private ArrayList<Organism> fishThatNeedToMove;
    private ArrayList<Organism> sharksThatNeedToMove;

    public PredPraySim(int rows, int cols, int width, int height)
    {
        super(rows, cols, width,height);
        createGrid(rows,cols);
        setUpHashMap();
    }

    public void createGrid(int numRows, int numCols) {
        grid = new String[numRows][numCols];
        organismGrid = new Organism[numRows][numCols];
        for(int i = 0; i<simRows;i++) {
            for(int j = 0; j<simCols;j++) {
                double choice = Math.random();
                System.out.println(choice);
                if (choice<=.792) {
                    organismGrid[i][j] = new Fish(i,j,"fish",0, breedThreshFish, defaultFishEnergy);
                    grid[i][j] = "fish";
                }
                else if (choice>=.80) {
                    organismGrid[i][j] = new Kelp("kelp", i,j);
                    grid[i][j] = "kelp";
                }
                else {
                    organismGrid[i][j] = new Shark(i,j,"shark", 0, defaultSharkEnergy, breedThreshShark);
                    grid[i][j] = "shark";
                }
            }
        }
    }


    public void updateGrid() {

        sharksThatNeedToMove = new ArrayList<>();
        fishThatNeedToMove = new ArrayList<>();


        for (int i = 0; i < simRows; i++) {
            for (int j = 0; j < simCols; j++) {
                updateCell(i,j, grid);
            }
        }
        for (int i = 0; i < simRows; i++) {
            for (int j = 0; j < simCols; j++) {
                organismGrid[i][j] = organismGridCopy[i][j];
                updateStringArray(i, j);
            }
        }
    }

    public ArrayList<Organism> generateEmptyCells(Organism[][] gridCopy) {
        ArrayList<Organism> result = new ArrayList<>();
        for (int i = 0; i < simRows; i++) {
            for (int j = 0; j < simCols; j++) {
                if (gridCopy[i][j].getName().equals("kelp") || gridCopy[i][j].getName().equals("fish")) {
                   result.add(gridCopy[i][j]);
                }
            }
        }
        return result;
    }


    public void updateCell(int x, int y, String[][] grid) {
        organismGrid[x][y].increaseLives();
        if(organismGrid[x][y].getCurrentState().equals("fish")) {
            //System.out.println("FISH at X: "+ x + "  Y: "+ y + "  Lives: "+organismGrid[x][y].getLives());
            fishThatNeedToMove.add(organismGrid[x][y]);
        }
        if(organismGrid[x][y].getCurrentState().equals("shark")) {
            //System.out.print(organismGrid[x][y].getEnergy());
            sharksThatNeedToMove.add(organismGrid[x][y]);
        }

        moveAllSharks();
    }

    public void moveOrganism(int x, int y, Organism[][] organismGridCopy) {
        organismGridCopy[x][y].move(x,y,organismGrid,organismGridCopy, emptyCells);
    }

    private void updateStringArray(int i, int j) {
        grid[i][j] = organismGrid[i][j].getName();
    }

    public void setUpHashMap() {
        colorMap = new HashMap<>();
        colorMap.putIfAbsent("fish", Color.BLUE);
        colorMap.putIfAbsent("shark", Color.GRAY);
        colorMap.putIfAbsent("kelp", Color.BLACK);
    }

    public void moveAllSharks() {
        while (sharksThatNeedToMove.size()>0) {
            int index = (int)(Math.random()*sharksThatNeedToMove.size());
            Organism current = sharksThatNeedToMove.get(index);
            sharksThatNeedToMove.remove(current);

            Organism[] neighbors = current.get4Neighbors(current.x,current.y,organismGrid);
            ArrayList<Organism> fishList = new ArrayList<Organism>();
            ArrayList<Organism> kelpList = new ArrayList<Organism>();
            for(Organism org:neighbors)
            {
                if(org.getCurrentState().equals("fish"))
                {
                    fishList.add(org);
                }else if(org.getCurrentState().equals("kelp"))
                {
                    kelpList.add(org);
                }
            }


            if (fishList.size() > 0) {
                int place = (int)(Math.random()*fishList.size());
                moveSharkToSpot(fishLoc.get(place), current);
            } else if (kelpList.size() > 0) {
                int place = (int)(Math.random()*kelpList.size());
                moveSharkToSpot(empty.get(place), current);
            } else {
                current.setNextState("shark");
            }
        }
    }

    public Organism[][] updateFish(Organism[][] gridOrgCopy, int i, int j) {
        if (gridOrgCopy[i][j].getName().equals("fish")) {
            Organism[] neighborsOrg = gridOrgCopy[i][j].get4Neighbors(i, j, gridOrgCopy);
            ArrayList<Organism> kelp = new ArrayList<>();
            for(Organism n : neighborsOrg) {
                if(n.getName().equals("kelp") && emptyCells.contains(n)) {
                    kelp.add(n);
                }
            }
            if(kelp.size() > 0 && (!movedFishCells.contains(gridOrgCopy[i][j]))) {
                movedFishCells.add(gridOrgCopy[i][j]);
                Organism target = kelp.get((int)(Math.random() * kelp.size()));
                emptyCells.remove(target);
                if(gridOrgCopy[i][j].getLives() >= breedThreshFish) {
                    gridOrgCopy[target.x][target.y] = new Fish(i, j, "fish", 0, defaultFishEnergy, breedThreshFish);
                    gridOrgCopy[i][j] = new Fish(i, j, "fish", 0, defaultFishEnergy, breedThreshFish);
                    emptyCells.add(gridOrgCopy[i][j]);
                }
                else {
                    gridOrgCopy[target.x][target.y] = gridOrgCopy[i][j];
                    gridOrgCopy[i][j] = new Kelp("kelp", i, j);
                    emptyCells.add(gridOrgCopy[i][j]);
                }
            }
        }
        return gridOrgCopy;
    }

}
