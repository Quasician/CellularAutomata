package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class PredPraySim extends Simulation {

    private int breedThreshFish = 2;
    private int breedThreshShark = 2;
    private int defaultSharkEnergy = 1;
    private ArrayList<Integer> x_empty_cells;
    private ArrayList<Integer> y_empty_cells;
    private Organism[][] gridOrganism;

    public PredPraySim(int rows, int cols, int width, int height)
    {
        super(rows, cols, width,height);
        createGrid(rows,cols);
        setUpHashMap();
    }

    public void createGrid(int numRows, int numCols) {
        gridOrganism = new Organism[numRows][numCols];
        for(int i = 0; i<simRows;i++) {
            for(int j = 0; j<simCols;j++) {
                ArrayList<String> list = new ArrayList<>();
                list.add("fish");
                list.add("empty");
                list.add("shark");
                String choice = list.get((int)Math.round(2 * Math.random()));
                if (choice.equals("fish")) {
                    gridOrganism[i][j] = new Fish(choice,0, breedThreshFish);
                }
                else if (choice.equals("shark")) {
                    gridOrganism[i][j] = new Shark(choice, 0, defaultSharkEnergy, breedThreshShark);
                }
                else {
                    gridOrganism[i][j].setName("empty");
                }
            }
        }
    }
    public void updateGrid() {
        Organism[][] gridCopy = new Organism[simRows][simCols];
        for(int i = 0; i<simRows;i++) {
            for(int j = 0; j<simCols;j++) {
                gridCopy[i][j] = gridOrganism[i][j];
            }
        }
        x_empty_cells = new ArrayList<>();
        y_empty_cells = new ArrayList<>();
        generateEmptyCells(toString(gridCopy));
        for(int i = 0; i<simRows;i++) {
            for(int j = 0; j<simCols;j++) {
                updateCell2(i,j,gridCopy);
            }
        }
    }

    @Override
    public void updateCell(int x, int y, String[][] gridCopy) {

    }

    public void updateCell2(int x, int y, Organism[][] gridCopy) {
        String[] neighbors = get4Neighbors(x,y, toString(gridCopy));
        int sum = 0;
        for(int i = 0; i<neighbors.length;i++) {
            if(neighbors[i].equals("empty")) {
                sum++;
            }
        }
        
    }

    public void generateEmptyCells(String[][] gridCopy) {
        for (int i = 0; i < simRows; i++) {
            for (int j = 0; j < simCols; j++) {
                if (gridCopy[i][j].equals("empty")) {
                    x_empty_cells.add(i);
                    y_empty_cells.add(j);
                }
            }
        }
    }

    public void setUpHashMap() {
        colorMap = new HashMap<>();
        colorMap.putIfAbsent("fish", Color.BLUE);
        colorMap.putIfAbsent("shark", Color.GRAY);
        colorMap.putIfAbsent("empty", Color.BLACK);
    }
    private String[][] toString(Organism[][] grid) {
        String[][] result = new String[simRows][simCols];
        for (int i = 0; i < simRows; i++) {
            for (int j = 0; j < simCols; j++) {
                result[i][j] = grid[i][j].getName();
            }
        }
        return result;
    }
}
