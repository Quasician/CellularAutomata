package cellsociety;

import javafx.scene.paint.Color;

import java.util.HashMap;

public class SugarSim extends Simulation {

    private int defaultCapacity;
    private int defaultSugar;
    private int defaultMetabolism;
    private double percentAgent;
    private double percentSugarFull;
    private double percentSugarHalf;
    private double percentSugarZero;


    public SugarSim(double rows, double cols, int width, int height, HashMap<String, Double> params) {
        super((int) rows, (int) cols, width, height, params);
        initParams();
        createGrid((int) rows, (int) cols);
        setUpHashMap();
    }

    public void initParams() {
        defaultCapacity = (int) (getParams().get("capacity") * 10) / 10;
        defaultMetabolism = (int) (getParams().get("metabolism") * 10) / 10;
        defaultSugar = (int) (getParams().get("defaultSugar") * 10) / 10;
        percentAgent = getParams().get("percentAgent");
        percentSugarFull = getParams().get("percentSugarFull");
        percentSugarHalf = getParams().get("percentSugarHalf");
        percentSugarZero = getParams().get("percentSugarZero");
    }

    public void createGrid(int rows, int cols) {
        createGrid(new String[rows][cols]);
        SugarCell[][] sugarGrid = new SugarCell[rows][cols];
        for(int i = 0; i<getRows();i++) {
            for(int j = 0; j<getCols();j++) {
                double choice = Math.random();

                if (choice <= percentAgent) {
                    sugarGrid[i][j] = new SugarCell(i, j, "agent", 0, defaultSugar, defaultMetabolism);
                    sugarGrid[i][j].setPrevState(new SugarCell(i, j, "agent", 0, defaultSugar, defaultMetabolism));
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "agent", 0, defaultSugar, defaultMetabolism));
                    setCell(i,j,"agent");
                }
                if (choice <= percentAgent + percentSugarFull) {
                    sugarGrid[i][j] = new SugarCell(i, j, "sugar_full", defaultCapacity, defaultCapacity, 0);
                    sugarGrid[i][j].setPrevState(new SugarCell(i, j, "sugar_full", defaultCapacity, defaultCapacity, 0));
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "sugar_full", defaultCapacity, defaultCapacity, 0));
                    setCell(i,j,"sugar_full");
                }
                if (choice <= percentAgent + percentSugarFull + percentSugarHalf) {
                    sugarGrid[i][j] = new SugarCell(i, j, "sugar_half", defaultCapacity, defaultCapacity / 2, 0);
                    sugarGrid[i][j].setPrevState(new SugarCell(i, j, "sugar_half", defaultCapacity, defaultCapacity / 2, 0));
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "sugar_half", defaultCapacity, defaultCapacity / 2, 0));
                    setCell(i,j,"sugar_half");
                }
                else {
                    sugarGrid[i][j] = new SugarCell(i, j, "sugar_zero", defaultCapacity, 0, 0);
                    sugarGrid[i][j].setPrevState(new SugarCell(i, j, "sugar_zero", defaultCapacity, 0, 0));
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "sugar_zero", defaultCapacity, 0, 0));
                    setCell(i,j,"sugar_zero");
                }
            }
        }
    }

    public void updateGrid() {

    }

    public void updateCell() {

    }

    public void setUpHashMap() {
        createColorMap(new HashMap<>());
        addToColorMap("agent", "red");
        addToColorMap("sugar_full", "gold");
        addToColorMap("sugar_half", "yellow");
        addToColorMap("sugar_zero", "lemonchiffon");
    }
}
