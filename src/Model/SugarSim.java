package Model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class SugarSim extends Simulation {

    private int defaultCapacity = 20;
    private int defaultSugar = 10;
    private int defaultMetabolism = 5;
    private int sugarRate = 1;
    private double percentAgent = 0.05;
    private double percentSugarFull = 0.1;
    private double percentSugarHalf = 0.5;
    private double percentSugarZero = 0.35;
    private ArrayList<SugarCell> agentsMoved;
    private ArrayList<SugarCell> takenSpots;
    private SugarCell[][] sugarGrid;
    private SugarCell[][] gridCopy;

    public SugarSim(double rows, double cols, int width, int height, HashMap<String, Double> params) {
        super((int) rows, (int) cols, width, height, params);
        initParams();
        createGrid((int) rows, (int) cols);
        setUpHashMap();
    }

    public void initParams() {
//        defaultCapacity = (int) (getParams().get("capacity") * 10) / 10;
//        defaultMetabolism = (int) (getParams().get("metabolism") * 10) / 10;
//        defaultSugar = (int) (getParams().get("defaultSugar") * 10) / 10;
//        sugarRate = (int) (getParams().get("sugarRate") * 10) / 10;
//        percentAgent = getParams().get("percentAgent");
//        percentSugarFull = getParams().get("percentSugarFull");
//        percentSugarHalf = getParams().get("percentSugarHalf");
//        percentSugarZero = getParams().get("percentSugarZero");
    }

    public void createGrid(int rows, int cols) {
        createGrid(new String[rows][cols]);
        sugarGrid = new SugarCell[rows][cols];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                double choice = Math.random();

                if (choice <= percentAgent) {
                    sugarGrid[i][j] = new SugarCell(i, j, "agent", 0, defaultSugar, defaultMetabolism);
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "agent", 0, defaultSugar, defaultMetabolism));
                    setCell(i, j, "agent");
                } else if (choice <= percentAgent + percentSugarFull) {
                    sugarGrid[i][j] = new SugarCell(i, j, "sugar_full", defaultCapacity, defaultCapacity, 0);
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "sugar_full", defaultCapacity, defaultCapacity, 0));
                    setCell(i, j, "sugar_full");
                } else if (choice <= percentAgent + percentSugarFull + percentSugarHalf) {
                    sugarGrid[i][j] = new SugarCell(i, j, "sugar_half", defaultCapacity / 2, defaultCapacity / 2, 0);
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "sugar_half", defaultCapacity / 2, defaultCapacity / 2, 0));
                    setCell(i, j, "sugar_half");
                } else {
                    sugarGrid[i][j] = new SugarCell(i, j, "sugar_zero", 0, 0, 0);
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "sugar_zero", 0, 0, 0));
                    setCell(i, j, "sugar_zero");
                }
            }
        }
        gridCopier(sugarGrid);
    }

    public void updateGrid() {
        agentsMoved = new ArrayList<>();
        takenSpots = new ArrayList<>();
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                sugarGrid[i][j] = sugarGrid[i][j].getNextState();
                sugarGrid[i][j].setNextState(null);
            }
        }
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                updateCell(sugarGrid[i][j]);
            }
        }
        updateStringArray();
    }

    public void updateCell(SugarCell input) {
        if (input.getName().equals("agent") && !(agentsMoved.contains(input))) {
            moveAgents(input);
        }
        else if (!takenSpots.contains(input)) {
            updateSugar(input);
        }
    }

    public void moveAgents(SugarCell input) {
        if (input.sugar < 0) {
            input.setNextState(new SugarCell(input.x, input.y, "sugar_zero", gridCopy[input.x][input.y].capacity, 0, 0));
            agentsMoved.add(input);
            return;
        }
        ArrayList<SugarCell> full = neighborFilter(input, "sugar_full");
        ArrayList<SugarCell> almost = neighborFilter(input, "sugar_almost");
        ArrayList<SugarCell> half = neighborFilter(input, "sugar_half");
        ArrayList<SugarCell> some = neighborFilter(input, "sugar_some");
        ArrayList<SugarCell> zero = neighborFilter(input, "sugar_zero");
        if (full.size() > 0 && input.sugar > 0) {
            eatSugar(full, input);
        }
        else if (almost.size() > 0 && input.sugar > 0) {
            eatSugar(almost, input);
        }
        else if (half.size() > 0 && input.sugar > 0) {
            eatSugar(half, input);
        }
        else if (some.size() > 0 && input.sugar > 0) {
            eatSugar(some, input);
        }
        else if (zero.size() > 0 && input.sugar > 0) {
            eatSugar(zero, input);
        }
        else {
            input.setNextState(input);
        }
    }

    public void updateSugar(SugarCell input) {
        if (input.sugar < gridCopy[input.x][input.y].capacity) {
            input.increaseSugar(sugarRate);
            if (input.sugar > 0 && input.sugar < (defaultCapacity / 2)) {
                input.setNextState(new SugarCell(input.x, input.y, "sugar_some", gridCopy[input.x][input.y].capacity, input.sugar, input.metabolism));
            }
            else if (input.sugar == (defaultCapacity / 2)) {
                input.setNextState(new SugarCell(input.x, input.y, "sugar_half", gridCopy[input.x][input.y].capacity, input.sugar, input.metabolism));
            }
            else if (input.sugar > (defaultCapacity / 2) && input.sugar < defaultCapacity) {
                input.setNextState(new SugarCell(input.x, input.y, "sugar_almost", gridCopy[input.x][input.y].capacity, input.sugar, input.metabolism));
            }
            else {
                input.setNextState(new SugarCell(input.x, input.y, "sugar_full", gridCopy[input.x][input.y].capacity, input.sugar, input.metabolism));
            }
        }
        else {
            input.setNextState(input);
        }
    }

    public ArrayList<SugarCell> neighborFilter(SugarCell input, String name) {
        SugarCell[] neighbors = input.get4Neighbors(input.x, input.y, sugarGrid);
        ArrayList<SugarCell> result = new ArrayList<>();
        for (SugarCell n : neighbors) {
            if (n.getName().equals(name) && !(takenSpots.contains(n))) {
                result.add(n);
            }
        }
        return result;
    }

    public void eatSugar(ArrayList<SugarCell> list, SugarCell input) {
        int choice = (int) (Math.random() * list.size());
        SugarCell target = list.get(choice);

        input.increaseSugar(target.sugar);
        input.decreaseSugar(defaultMetabolism);

        target.setNextState(new SugarCell(target.x, target.y, "agent", 0, input.sugar, defaultMetabolism));
        input.setNextState(new SugarCell(input.x, input.y, "sugar_zero", gridCopy[input.x][input.y].capacity, 0, 0));

        takenSpots.add(target);
        agentsMoved.add(input);
    }

    public void setUpHashMap() {
        createColorMap(new HashMap<>());
        addToColorMap("agent", "red");
        addToColorMap("sugar_full", "goldenrod");
        addToColorMap("sugar_almost", "gold");
        addToColorMap("sugar_half", "yellow");
        addToColorMap("sugar_some", "lemonchiffon");
        addToColorMap("sugar_zero", "white");
    }

    private void updateStringArray() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                setCell(i, j, sugarGrid[i][j].getName());
            }
        }
    }

    private void gridCopier(SugarCell[][] grid) {
        gridCopy = new SugarCell[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j].getName().equals("agent")) {
                    String[] name = {"sugar_full", "sugar_half", "sugar_zero"};
                    int[] capa = {defaultCapacity, defaultCapacity / 2, 0};
                    int indexRand = (int) (Math.random() * 2.999);
                    gridCopy[i][j] = new SugarCell(i, j, name[indexRand], capa[indexRand], 0, 0);
                }
                else {
                    gridCopy[i][j] = grid[i][j];
                }
            }
        }
    }
}
