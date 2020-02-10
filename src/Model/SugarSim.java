package Model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class SugarSim extends Simulation {

    private double defaultCapacity;
    private double defaultSugar;
    private double defaultMetabolism;
    private double sugarRate;
    private double percentAgent;
    private double percentSugarFull;
    private double percentSugarHalf;
    private double percentSugarZero;
    private ArrayList<SugarCell> agentsMoved;
    private ArrayList<SugarCell> takenSpots;
    private SugarCell[][] sugarGrid;
    private SugarCell[][] gridCopy;

    public SugarSim(int width, int height, HashMap<String,Double> params)
    {
        super((int)(params.get("grid_height")*10)/10,(int)(params.get("grid_width")*10/10), width,height, params);
        initParams();
        createGrid(getRows(),getCols());
        setUpHashMap();
        setName("sugar");
    }

    public SugarSim(int width, int height, HashMap<String,Double> params, Simulation sim)
    {
        super((int)(params.get("grid_height")*10)/10,(int)(params.get("grid_width")*10/10), width,height, params);
        initParams();
        createGridFromAnotherSim(sim);
        initSugarGridFromFile(getRows(),getCols());
        setUpHashMap();
        setName("sugar");
    }

    public void initParams() {
        defaultCapacity = getParams().get("defaultCapacity");
        defaultMetabolism = getParams().get("defaultMetabolism");
        defaultSugar = getParams().get("defaultSugar");
        sugarRate = getParams().get("sugarRate");
        percentAgent = getParams().get("percentAgent");
        percentSugarFull = getParams().get("percentSugarFull");
        percentSugarHalf = getParams().get("percentSugarHalf");
        percentSugarZero = getParams().get("percentSugarZero");
        initAddToAgentNumberMap("agent");
        initAddToAgentNumberMap("sugar_zero");
        initAddToAgentNumberMap("sugar_almost");
        initAddToAgentNumberMap("sugar_some");
        initAddToAgentNumberMap("sugar_half");
        initAddToAgentNumberMap("sugar_full");
    }

    public void createGrid(int rows, int cols) {
        sugarGrid = new SugarCell[rows][cols];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                double choice = Math.random();

                if (choice <= percentAgent) {
                    sugarGrid[i][j] = new SugarCell(i, j, "agent", 0, (int)defaultSugar, (int)defaultMetabolism);
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "agent", 0, (int)defaultSugar, (int)defaultMetabolism));
                    setCell(i, j, "agent");
                } else if (choice <= percentAgent + percentSugarFull) {
                    sugarGrid[i][j] = new SugarCell(i, j, "sugar_full", (int)defaultCapacity, (int)defaultCapacity, 0);
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "sugar_full", (int)defaultCapacity, (int)defaultCapacity, 0));
                    setCell(i, j, "sugar_full");
                } else if (choice <= percentAgent + percentSugarFull + percentSugarHalf) {
                    sugarGrid[i][j] = new SugarCell(i, j, "sugar_half", (int)(defaultCapacity / 2), (int)(defaultCapacity / 2), 0);
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "sugar_half", (int)(defaultCapacity / 2), (int)(defaultCapacity / 2), 0));
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

    public void initSugarGridFromFile(int rows, int cols) {
        sugarGrid = new SugarCell[rows][cols];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {

                if (getCell(i,j).equals("agent")) {
                    sugarGrid[i][j] = new SugarCell(i, j, "agent", 0, (int)defaultSugar, (int)defaultMetabolism);
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "agent", 0, (int)defaultSugar, (int)defaultMetabolism));
                } else if (getCell(i,j).equals("sugar_full")) {
                    sugarGrid[i][j] = new SugarCell(i, j, "sugar_full", (int)defaultCapacity, (int)defaultCapacity, 0);
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "sugar_full", (int)defaultCapacity, (int)defaultCapacity, 0));
                } else if (getCell(i,j).equals("sugar_almost")) {
                    sugarGrid[i][j] = new SugarCell(i, j, "sugar_almost", (int)(defaultCapacity * (3/4)), (int)(defaultCapacity * (3/4)), 0);
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "sugar_almost", (int)(defaultCapacity * (3/4)), (int)(defaultCapacity * (3/4)), 0));
                } else if (getCell(i,j).equals("sugar_half")) {
                    sugarGrid[i][j] = new SugarCell(i, j, "sugar_half", (int)(defaultCapacity / 2), (int)(defaultCapacity / 2), 0);
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "sugar_half", (int)(defaultCapacity / 2), (int)(defaultCapacity / 2), 0));
                } else if (getCell(i,j).equals("sugar_some")) {
                    sugarGrid[i][j] = new SugarCell(i, j, "sugar_some", (int)(defaultCapacity * (1/4)), (int)(defaultCapacity * (1/4)), 0);
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "sugar_some", (int)(defaultCapacity * (1/4)), (int)(defaultCapacity * (1/4)), 0));
                } else {
                    sugarGrid[i][j] = new SugarCell(i, j, "sugar_zero", 0, 0, 0);
                    sugarGrid[i][j].setNextState(new SugarCell(i, j, "sugar_zero", 0, 0, 0));
                }
            }
        }
        gridCopier(sugarGrid);
    }


    public void updateGrid() {
        resetAgentNumbers();
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
        countAgentNumbers();
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
            input.increaseSugar((int)sugarRate);
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
        ArrayList<SugarCell> neighbors = new ArrayList<>();
        neighbors = input.get4NeighborsTorroidal(input.x, input.y, sugarGrid, neighbors);
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
        input.decreaseSugar((int)defaultMetabolism);

        target.setNextState(new SugarCell(target.x, target.y, "agent", 0, input.sugar, (int)defaultMetabolism));
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
                    int[] capa = {(int)defaultCapacity, (int)defaultCapacity / 2, 0};
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
