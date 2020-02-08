package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class SugarSim extends Simulation {

    private int defaultCapacity = 2;
    private int defaultSugar = 2;
    private int defaultMetabolism = 1;
    private int sugarRate = 1;
    private double percentAgent = 0.05;
    private double percentSugarFull = 0.1;
    private double percentSugarHalf = 0.5;
    private double percentSugarZero = 0.35;
    private ArrayList<SugarCell> agentsMoved;
    private ArrayList<SugarCell> takenSpots;
    private SugarCell[][] sugarGrid;
    private SugarCell[][] gridCopy;
//    int count = 0;

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
//                    sugarGrid[i][j].setInitState(new SugarCell(i, j, "sugar_zero", 0, 0, 0));
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "agent", 0, defaultSugar, defaultMetabolism));
                    setCell(i, j, "agent");
                } else if (choice <= percentAgent + percentSugarFull) {
                    sugarGrid[i][j] = new SugarCell(i, j, "sugar_full", defaultCapacity, defaultCapacity, 0);
//                    sugarGrid[i][j].setInitState(new SugarCell(i, j, "sugar_full", defaultCapacity, defaultCapacity, 0));
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "sugar_full", defaultCapacity, defaultCapacity, 0));
                    setCell(i, j, "sugar_full");
                } else if (choice <= percentAgent + percentSugarFull + percentSugarHalf) {
                    sugarGrid[i][j] = new SugarCell(i, j, "sugar_half", defaultCapacity / 2, defaultCapacity / 2, 0);
//                    sugarGrid[i][j].setInitState(new SugarCell(i, j, "sugar_half", defaultCapacity / 2, defaultCapacity / 2, 0));
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "sugar_half", defaultCapacity / 2, defaultCapacity / 2, 0));
                    setCell(i, j, "sugar_half");
                } else {
                    sugarGrid[i][j] = new SugarCell(i, j, "sugar_zero", 0, 0, 0);
//                    sugarGrid[i][j].setInitState(new SugarCell(i, j, "sugar_zero", 0, 0, 0));
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "sugar_zero", 0, 0, 0));
                    setCell(i, j, "sugar_zero");
                }
            }
        }
        gridCopier(sugarGrid);
    }

    public void updateGrid() {
//        count = 0;
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
//        System.out.println(count);
    }

    public void updateCell(SugarCell input) {
        if (input.getName().equals("agent") && !(agentsMoved.contains(input))) {
            moveAgents(input);
//            count++;
        } else if (!takenSpots.contains(input)) {
            updateSugar(input);
        }
    }

    public void moveAgents(SugarCell input) {
        if (input.sugar < 0) {
            input.setNextState(new SugarCell(input.x, input.y, gridCopy[input.x][input.y].getName(), gridCopy[input.x][input.y].capacity, 0, 0));
            agentsMoved.add(input);
            return;
        }
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
        if (full.size() > 0 && input.sugar > 0) {
            int choice = (int) (Math.random() * full.size());
            SugarCell target = full.get(choice);

            input.increaseSugar(target.sugar);
            input.decreaseSugar(defaultMetabolism);

            target.setNextState(new SugarCell(target.x, target.y, "agent", 0, input.sugar, defaultMetabolism));
            input.setNextState(new SugarCell(input.x, input.y, "sugar_zero", gridCopy[input.x][input.y].capacity, 0, 0));

            takenSpots.add(target);
            agentsMoved.add(input);
        }
        else if (half.size() > 0 && input.sugar > 0) {
            int choice = (int) (Math.random() * half.size());
            SugarCell target = half.get(choice);

            input.increaseSugar(target.sugar);
            input.decreaseSugar(defaultMetabolism);

            target.setNextState(new SugarCell(target.x, target.y, "agent", 0, input.sugar, defaultMetabolism));
            input.setNextState(new SugarCell(input.x, input.y, "sugar_zero", gridCopy[input.x][input.y].capacity, 0, 0));

            takenSpots.add(target);
            agentsMoved.add(input);
        }
        else if (zero.size() > 0 && input.sugar > 0) {
            int choice = (int) (Math.random() * zero.size());
            SugarCell target = zero.get(choice);

            input.decreaseSugar(defaultMetabolism);

            target.setNextState(new SugarCell(target.x, target.y, "agent", 0, input.sugar, defaultMetabolism));
            input.setNextState(new SugarCell(input.x, input.y, "sugar_zero", gridCopy[input.x][input.y].capacity, 0, 0));

            takenSpots.add(target);
            agentsMoved.add(input);
        }
        else {
            input.setNextState(new SugarCell(input.x, input.y, "agent", 0, input.sugar, input.metabolism));
            agentsMoved.add(input);
        }
    }

    public void updateSugar(SugarCell input) {
        if (!input.getName().equals("sugar_full")) {
            if (input.sugar < gridCopy[input.x][input.y].capacity) {
                input.increaseSugar(sugarRate);
                if (input.sugar == gridCopy[input.x][input.y].capacity && input.getName().equals("sugar_half")) {
                    input.setNextState(new SugarCell(input.x, input.y, "sugar_full", input.capacity, input.sugar, input.metabolism));
                }
                else {
                    input.setNextState(new SugarCell(input.x, input.y, "sugar_half", input.capacity, input.sugar, input.metabolism));
                }
            }
            else {
                input.setNextState(input);
            }
        }
        else {
            input.setNextState(input);
        }
    }

        public void setUpHashMap() {
            createColorMap(new HashMap<>());
            addToColorMap("agent", "red");
            addToColorMap("sugar_full", "gold");
            addToColorMap("sugar_half", "yellow");
            addToColorMap("sugar_zero", "lemonchiffon");
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
