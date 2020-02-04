package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PredPraySim extends Simulation {

    private int breedThreshFish = 3;
    private int breedThreshShark = 3;
    private int defaultSharkEnergy = 3;
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
                //System.out.println(choice);
//                if (choice<=.792) {
//                    organismGrid[i][j] = new Organism(i,j,"fish",0, defaultFishEnergy);
//                    organismGrid[i][j].setNextState(new Organism(i,j,"fish",0,defaultFishEnergy));
//                    grid[i][j] = "fish";
//                }
//                else if (choice>=.80) {
//                    organismGrid[i][j] = new Organism(i,j,"kelp",0,0);
//                    organismGrid[i][j].setNextState(new Organism(i,j,"kelp",0,0));
//                    grid[i][j] = "kelp";
//                }
//                else {
//                    organismGrid[i][j] = new Organism(i,j,"shark", 0, defaultSharkEnergy);
//                    organismGrid[i][j].setNextState(new Organism(i,j,"shark", 0, defaultSharkEnergy));
//                    grid[i][j] = "shark";
//                }
                organismGrid[i][j] = new Organism(i,j,"kelp", 0, 0);
                organismGrid[i][j].setNextState(new Organism(i,j,"kelp", 0, 0));
                grid[i][j] = "kelp";
            }
        }
        organismGrid[25][25] = new Organism(25,25,"shark", 0, defaultSharkEnergy);
        organismGrid[25][25].setNextState(new Organism(25,25,"shark", 0, defaultSharkEnergy));
        grid[25][25] = "shark";
    }


    public void updateGrid() {
        updateStringArray();

        sharksThatNeedToMove = new ArrayList<>();
        fishThatNeedToMove = new ArrayList<>();

        for (int i = 0; i < simRows; i++) {
            for (int j = 0; j < simCols; j++) {
                organismGrid[i][j] = organismGrid[i][j].getNextState();
                organismGrid[i][j].setNextState(null);
            }
        }

        for (int i = 0; i < simRows; i++) {
            for (int j = 0; j < simCols; j++) {
                updateCell(i,j, grid);
            }
        }

        moveAllSharks();
        moveAllFish();
    }


    public void updateCell(int x, int y, String[][] grid) {
        if(organismGrid[x][y].getName().equals("fish")) {
            //System.out.println("FISH at X: "+ x + "  Y: "+ y + "  Lives: "+organismGrid[x][y].getLives());
            fishThatNeedToMove.add(organismGrid[x][y]);
        }
        if(organismGrid[x][y].getName().equals("shark")) {
            //System.out.print(organismGrid[x][y].getEnergy());
            sharksThatNeedToMove.add(organismGrid[x][y]);
        }
        if(x==25 && y==25)
        {
            System.out.println("Current State: "+organismGrid[25][25].getName());
            System.out.println("Energy: "+organismGrid[25][25].getEnergy());
            //System.out.println("Next State: "+organismGrid[25][25].getNextState().getName());
        }
        if(organismGrid[x][y].getName().equals("kelp")|| (organismGrid[x][y].getName().equals("shark") && organismGrid[x][y].getEnergy()<=0))
        {
            organismGrid[x][y].setNextState(new Organism(x,y,"kelp",0,0));
            if(x==25 && y==25)
            {
                System.out.println("OCurrent State: "+organismGrid[25][25].getName());
                System.out.println("OEnergy: "+organismGrid[25][25].getEnergy());
                System.out.println("ONext State: "+organismGrid[25][25].getNextState().getName());
            }
        }
        updateOrganism(organismGrid[x][y]);
    }

//    public void moveOrganism(int x, int y, Organism[][] organismGridCopy) {
//        organismGridCopy[x][y].move(x,y,organismGrid,organismGridCopy, emptyCells);
//    }

    private void updateOrganism(Organism org) {
        org.increaseLives();
        if(org.getName().equals("shark"))
        {
            org.decreaseEnergy();
        }
    }

    private void updateStringArray() {
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                grid[i][j] = organismGrid[i][j].getName();
            }
        }
    }

    public void setUpHashMap() {
        colorMap = new HashMap<>();
        colorMap.putIfAbsent("fish", Color.BLUE);
        colorMap.putIfAbsent("shark", Color.GRAY);
        colorMap.putIfAbsent("kelp", Color.BLACK);
    }

    public void moveAllSharks() {
        while (sharksThatNeedToMove.size()>0) {
            int sharkListIndex = (int)(Math.random()*sharksThatNeedToMove.size());
            Organism current = sharksThatNeedToMove.get(sharkListIndex);
            sharksThatNeedToMove.remove(current);


            ArrayList<Organism> fishList = current.getFish(current.x, current.y, organismGrid);
            ArrayList<Organism> kelpList = current.getKelpAndFutureEmpty(current.x, current.y, organismGrid);

            if (fishList.size() > 0) {
                int fishListIndex = (int)(Math.random()*fishList.size());
                eatFish(current, fishList.get(fishListIndex));
            } else if (kelpList.size() > 0) {
                int kelpListIndex = (int)(Math.random()*kelpList.size());
                sharkMovesToKelpCell(current, kelpList.get(kelpListIndex));
            } else {
                current.setNextState(current);
            }
        }
    }

    public void eatFish(Organism source, Organism destination) {
        int currentEnergy = source.getEnergy();
        checkBreed(source);
        destination.setNextState(new Organism(source.x, source.y, source.getName(), source.getLives(), currentEnergy+defaultFishEnergy));
    }

    public void sharkMovesToKelpCell(Organism source, Organism destination) {
        int currentEnergy = source.getEnergy();
        checkBreed(source);
        destination.setNextState(new Organism(source.x, source.y, source.getName(), source.getLives(), currentEnergy));
    }

    public void fishMovesToKelpCell(Organism source, Organism destination) {
        int currentEnergy = source.getEnergy();
        checkBreed(source);
        destination.setNextState(new Organism(source.x, source.y, source.getName(), source.getLives(), currentEnergy));
    }

    public void checkBreed(Organism source)
    {
        if(source.getLives()>source.getBreedThresh())
        {
            if(source.getName().equals("fish"))
            {
                source.setNextState(new Organism(source.x, source.y, source.getName(), 0, defaultFishEnergy));
            }else
            {
                source.setNextState(new Organism(source.x, source.y, source.getName(), 0, defaultSharkEnergy));
            }
            source.setLife(0);
        } else
        {
            source.setNextState(new Organism(source.x, source.y, "kelp", 0, 0));
        }
    }

    private void moveAllFish() {
        while (fishThatNeedToMove.size() > 0) {
            int sharkListIndex = (int)(Math.random()*fishThatNeedToMove.size());
            Organism current = fishThatNeedToMove.get(sharkListIndex);
            fishThatNeedToMove.remove(current);

            ArrayList<Organism> kelpList = current.getKelpAndFutureEmpty(current.x,current.y,organismGrid);

            if (kelpList.size() > 0) {
                int kelpListIndex = (int)(Math.random()*kelpList.size());
                fishMovesToKelpCell(current, kelpList.get(kelpListIndex));
            } else {
                current.setNextState(current);
            }
        }
    }

}
