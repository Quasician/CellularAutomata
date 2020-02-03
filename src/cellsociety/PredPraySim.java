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
    private ArrayList<Organism> movedFishCells;
    private ArrayList<Organism> movedSharkCells;

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
                ArrayList<String> list = new ArrayList<>();
                list.add("fish");
                list.add("shark");
                list.add("shark");
                list.add("kelp");
                list.add("kelp");
                list.add("kelp");
                list.add("kelp");
                list.add("kelp");
                String choice = list.get((int)(8 * Math.random()));
                if (choice.equals("fish")) {
                    organismGrid[i][j] = new Fish(i,j,choice,0, breedThreshFish, defaultFishEnergy);
                    grid[i][j] = "fish";
                }
                else if (choice.equals("shark")) {
                    organismGrid[i][j] = new Shark(i,j,choice, 0, defaultSharkEnergy, breedThreshShark);
                    grid[i][j] = "shark";
                }
                else {
                    organismGrid[i][j] = new Kelp(choice, i,j);
                    grid[i][j] = "kelp";
                }
            }
        }
    }


    public void updateGrid() {
        String[][] gridCopy = new String[simRows][simCols];
        Organism[][] organismGridCopy = new Organism[simRows][simCols];
        for (int i = 0; i < simRows; i++) {
            for (int j = 0; j < simCols; j++) {
                organismGridCopy[i][j] = organismGrid[i][j];
                gridCopy[i][j] = grid[i][j];
            }
        }
        emptyCells = new ArrayList<>();
        emptyCells = generateEmptyCells(organismGridCopy);
        movedFishCells = new ArrayList<>();
        movedSharkCells = new ArrayList<>();

//        for (int i = 0; i < simRows; i++) {
//            for (int j = 0; j < simCols; j++) {
//                moveOrganism(i, j, organismGridCopy);
//                updateCell(i, j, grid);
//                updateStringArray(i, j);
//            }
//        }

        for (int i = 0; i < simRows; i++) {
            for (int j = 0; j < simCols; j++) {
                organismGridCopy = updateShark(organismGridCopy, i, j);
                organismGridCopy = updateFish(organismGridCopy, i, j);
                organismGridCopy[i][j].decreaseEnergy();
                organismGridCopy[i][j].increaseLives();
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
        if(organismGrid[x][y].getName().equals("fish")) {
            System.out.println("FISH at X: "+ x + "  Y: "+ y + "  Lives: "+organismGrid[x][y].getLives());
        }
        if(organismGrid[x][y].getName().equals("shark")) {
            System.out.print(organismGrid[x][y].getEnergy());
            organismGrid[x][y].decreaseEnergy();
            System.out.print(" DECREASED to : " +organismGrid[x][y].getEnergy()+"\n");
            if(organismGrid[x][y].getEnergy()<=0) {
                //grid[x][y] = "kelp";
                organismGrid[x][y] = new Kelp("kelp",x,y);
            }
        }
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

    public Organism[][] updateShark(Organism[][] gridOrgCopy, int i, int j) {
        if (gridOrgCopy[i][j].getName().equals("shark")) {
            if(gridOrgCopy[i][j].getEnergy() <= 0) {
                gridOrgCopy[i][j] = new Kelp("kelp", i, j);
                emptyCells.add(gridOrgCopy[i][j]);
                return gridOrgCopy;
            }
            Organism[] neighborsOrg = gridOrgCopy[i][j].get4Neighbors(i, j, gridOrgCopy);
            ArrayList<Organism> fish = new ArrayList<>();
            ArrayList<Organism> kelp = new ArrayList<>();
            for(Organism n : neighborsOrg) {
                if(n.getName().equals("fish") && emptyCells.contains(n)) {
                    fish.add(n);
                }
                if(n.getName().equals("kelp") && emptyCells.contains(n)) {
                    kelp.add(n);
                }
            }
            if(fish.size() > 0 && (!movedSharkCells.contains(gridOrgCopy[i][j]))) {
                movedSharkCells.add(gridOrgCopy[i][j]);
                Organism target = fish.get((int)(Math.random() * fish.size()));
                emptyCells.remove(target);
                gridOrgCopy[i][j].setEnergy(gridOrgCopy[i][j].getEnergy() + 2);
                if(gridOrgCopy[i][j].getLives() > breedThreshShark) {
                    gridOrgCopy[target.x][target.y] = new Shark(i, j, "shark", 0, gridOrgCopy[i][j].getEnergy(), breedThreshShark);
                    gridOrgCopy[i][j] = new Shark(i, j, "shark", 0, defaultSharkEnergy, breedThreshShark);
                    movedSharkCells.add(gridOrgCopy[i][j]);
                }
                else {
                    gridOrgCopy[target.x][target.y] = gridOrgCopy[i][j];
                    gridOrgCopy[i][j] = new Kelp("kelp", i, j);
                    emptyCells.add(gridOrgCopy[i][j]);
                }
            }
            else if(kelp.size() > 0 && (!movedSharkCells.contains(gridOrgCopy[i][j]))) {
                movedSharkCells.add(gridOrgCopy[i][j]);
                Organism target = kelp.get((int)(Math.random() * kelp.size()));
                emptyCells.remove(target);
                if(gridOrgCopy[i][j].getLives() >= breedThreshShark) {
                    gridOrgCopy[target.x][target.y] = new Shark(i, j, "shark", 0, gridOrgCopy[i][j].getEnergy(), breedThreshShark);
                    gridOrgCopy[i][j] = new Shark(i, j, "shark", 0, defaultSharkEnergy, breedThreshShark);
                    movedSharkCells.add(gridOrgCopy[i][j]);
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
