package cellsociety;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class SugarSim extends Simulation {

    private int defaultCapacity;
    private int defaultSugar;
    private int defaultMetabolism;
    private double percentAgent;
    private double percentSugarFull;
    private double percentSugarHalf;
    private double percentSugarZero;
    private ArrayList<SugarCell> agentsMoved;
    private ArrayList<SugarCell> takenSpots;
    private SugarCell[][] sugarGrid;


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
        sugarGrid = new SugarCell[rows][cols];
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
        agentsMoved = new ArrayList<>();
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                sugarGrid[i][j] = sugarGrid[i][j].getNextState();
                sugarGrid[i][j].setNextState(null);
            }
        }
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {

            }
        }
    }

    public void updateCell() {

    }

    public void moveAgents(SugarCell input) {
        if(input.getName().equals("agent") && !(agentsMoved.contains(input))) {
            SugarCell[] neighbors = input.get4Neighbors(input.x, input.y, sugarGrid);
            ArrayList<SugarCell> full = new ArrayList<>();
            ArrayList<SugarCell> half = new ArrayList<>();
            ArrayList<SugarCell> zero = new ArrayList<>();
            for (SugarCell n : neighbors) {
                if (n.getName().equals("sugar_full") && !(takenSpots.contains(n))) {
                    full.add(n);
                }
                if (n.getName().equals("sugar_half") && !(takenSpots.contains(n))) {
                    half.add(n);
                }
                if (n.getName().equals("sugar_zero") && !(takenSpots.contains(n))) {
                    zero.add(n);
                }
            }
            if (full.size() > 0) {
                int choice = (int) (Math.random() * full.size());
                SugarCell target = full.get(choice);
                input.setNextState(target);
                takenSpots.add(target);
                agentsMoved.add(input);
            }
            if (half.size() > 0) {
                int choice = (int) (Math.random() * half.size());
                SugarCell target = half.get(choice);
                input.setNextState(target);
                takenSpots.add(target);
                agentsMoved.add(input);
            }
            if (zero.size() > 0) {
                int choice = (int) (Math.random() * zero.size());
                SugarCell target = zero.get(choice);
                input.setNextState(target);
                takenSpots.add(target);
                agentsMoved.add(input);
            }
        }
    }

    public void setUpHashMap() {
        createColorMap(new HashMap<>());
        addToColorMap("agent", "red");
        addToColorMap("sugar_full", "gold");
        addToColorMap("sugar_half", "yellow");
        addToColorMap("sugar_zero", "lemonchiffon");
    }
}
