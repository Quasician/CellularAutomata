package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class AntSim extends Simulation {

    private AntCell[][] antGrid;
    private AntCell[][] gridCopy;
    private double percentFood = 0.05;
    private int maxFood = 10;
    private int foodRate = 1;
    private int maxAnt = 50;
    private int antCount = 0;
    private int pheroThresh1 = 4;
    private int pheroMax = 10;
    private int pheroGain = 1;
    private int pheroLose = 1;
    private ArrayList<AntCell> takenSpots;
    private ArrayList<AntCell> antsMoved;

    public AntSim(int width, int height, HashMap<String, Double> params) {
        super((int)(params.get("grid_height")*10)/10,(int)(params.get("grid_width")*10/10), width,height, params);
        initParams();
        createGrid(getRows(), getCols());
        setUpHashMap();
    }

    public void initParams() {

    }

    public void createGrid(int rows, int cols) {
        createGrid(new String[rows][cols]);
        antGrid = new AntCell[rows][cols];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                double choice = Math.random();
                if (choice <= percentFood) {
                    int randFood = (int) (Math.random() * maxFood);
                    antGrid[i][j] = new AntCell(i, j, "food", 0, null, randFood);
                    antGrid[i][j].setNextState(antGrid[i][j]);
                    setCell(i, j, "food");
                }
                else {
                    antGrid[i][j] = new AntCell(i, j, "empty", 0, null, 0);
                    antGrid[i][j].setNextState(antGrid[i][j]);
                    setCell(i, j, "empty");
                }
            }
        }
        drawCircle((getRows() / 10) + 2, "empty", 0);
        drawCircle((getRows() / 10) - 4, "home", 0);
        gridCopier(antGrid);
        antSpawn();
    }

    public void updateGrid() {
        takenSpots = new ArrayList<>();
        antsMoved = new ArrayList<>();
        if (antCount < maxAnt) {
            antSpawn();
        }
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                antGrid[i][j] = antGrid[i][j].getNextState();
                if (antGrid[i][j].getName().equals("tl_ant") || antGrid[i][j].getName().equals("tr_ant") || antGrid[i][j].getName().equals("bl_ant") || antGrid[i][j].getName().equals("br_ant")) {
                    System.out.println("ANT: " + i + " " + j);
                    AntCell[] list = antGrid[i][j].get4Neighbors(antGrid[i][j].x, antGrid[i][j].y, antGrid);
                    for (AntCell l : list) {
                        System.out.println("NEIGHBOR: " + l.x + " " + l.y);
                    }
                }
                antGrid[i][j].setNextState(null);
            }
        }
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                updateCell(antGrid[i][j]);
            }
        }
        updateStringArray();
    }

    public void updateCell(AntCell input) {
        if ((input.getName().equals("tl_ant") || input.getName().equals("tr_ant") || input.getName().equals("bl_ant") || input.getName().equals("br_ant")) && !(antsMoved.contains(input))) {
            moveAnt(input);
        }
        else if ((input.getName().equals("food")) && !(takenSpots.contains(input))) {
            updateFood(input);
        }
        else if ((input.getName().equals("home") || input.getName().equals("empty")) && !(takenSpots.contains(input))) {
            input.setNextState(input);
        }
        else if ((input.getName().equals("phero1") || input.getName().equals("phero2") || input.getName().equals("phero3")) && !(takenSpots.contains(input))) {
            reducePhero(input);
        }
    }

    private void moveAnt(AntCell input) {
        //Find all Neighbor(s)
        ArrayList<AntCell> neighbors = new ArrayList<>();
        neighbors = input.get4NeighborsTorroidal(input.x, input.y, antGrid, neighbors);
        //Find Neighbor(s) the ant can move to
        ArrayList<AntCell> locationFinder;
        if (input.getMission().equals("food")) {
            locationFinder = neighborFilterForward(input, neighbors);
        }
        else {
            locationFinder = neighborFilterBackward(input, neighbors);
        }
        //Find Neighbor(s) the ant should move to
        ArrayList<AntCell> chooseSpot = priorityFilter(locationFinder);
        //Keep ant stationary if it can't move
        if (chooseSpot.size() == 0) {
            input.setNextState(input);
            return;
        }
        int choice = (int) (Math.random() * chooseSpot.size());
        AntCell target = chooseSpot.get(choice);
        //Track that ant has moved and spot has been taken
        takenSpots.add(target);
        antsMoved.add(input);
        if (input.getMission().equals("food")) {
            if (target.getName().equals("food")) {
                target.setNextState(new AntCell(target.x, target.y, input.getName(), 0, "home", input.food + target.food));
                input.setNextState(new AntCell(input.x, input.y, "phero1", input.increasePhero(pheroGain), null, 0));
                updatePhero(input);
            }
            else if (target.getName().equals("empty")) {
                System.out.println("Ant Here: " + input.x + "," + input.y);
                target.setNextState(new AntCell(target.x, target.y, input.getName(), 0, "food", input.food));
                if (gridCopy[input.x][input.y].getName().equals("home")) {
                    input.setNextState(new AntCell(input.x, input.y, gridCopy[input.x][input.y].getName(), 0, null, 0));
                    return;
                }
                input.setNextState(new AntCell(input.x, input.y, "phero1", input.increasePhero(pheroGain), null, 0));
                updatePhero(input);
            }
            else if (target.getName().equals("home")) {
                target.setNextState(new AntCell(target.x, target.y, input.getName(), 0, "food", input.food));
                input.setNextState(new AntCell(input.x, input.y, gridCopy[input.x][input.y].getName(), 0, null, 0));
            }
            else if (target.getName().equals("phero1") || target.getName().equals("phero2") || target.getName().equals("phero3")){
                target.setNextState(new AntCell(target.x, target.y, input.getName(), target.pheromone, "food", input.food));
                input.setNextState(new AntCell(input.x, input.y, "phero1", input.increasePhero(pheroGain), null, 0));
                updatePhero(input);
            }
        }
        else {
            if (target.getName().equals("food")) {
                target.setNextState(new AntCell(target.x, target.y, input.getName(), 0, "home", input.food + target.food));
                if (gridCopy[input.x][input.y].getName().equals("food")) {
                    input.setNextState(new AntCell(input.x, input.y, gridCopy[input.x][input.y].getName(), 0, null, 0));
                    return;
                }
                input.setNextState(new AntCell(input.x, input.y, "phero1", input.increasePhero(pheroGain), null, 0));
                updatePhero(input);
            }
            else if (target.getName().equals("empty")) {
                target.setNextState(new AntCell(target.x, target.y, input.getName(), 0, "home", input.food));
                if (gridCopy[input.x][input.y].getName().equals("food")) {
                    input.setNextState(new AntCell(input.x, input.y, gridCopy[input.x][input.y].getName(), 0, null, 0));
                    return;
                }
                input.setNextState(new AntCell(input.x, input.y, "phero1", input.increasePhero(pheroGain), null, 0));
                updatePhero(input);
            }
            else if (target.getName().equals("home")) {
                target.setNextState(new AntCell(target.x, target.y, input.getName(), 0, "food", input.food));
                input.setNextState(new AntCell(input.x, input.y, "phero1", input.increasePhero(pheroGain), null, 0));
                updatePhero(input);
            }
            else {
                target.setNextState(new AntCell(target.x, target.y, input.getName(), target.pheromone, "home", input.food));
                if (gridCopy[input.x][input.y].getName().equals("food")) {
                    input.setNextState(new AntCell(input.x, input.y, gridCopy[input.x][input.y].getName(), 0, null, 0));
                    return;
                }
                input.setNextState(new AntCell(input.x, input.y, "phero1", input.increasePhero(pheroGain), null, 0));
                updatePhero(input);
            }
        }
    }

    private void updateFood(AntCell input) {
        if (!(takenSpots.contains(input)) && input.food < maxFood) {
            input.setNextState(new AntCell(input.x, input.y, input.getName(), 0, null, input.food + foodRate));
            return;
        }
        input.setNextState(input);
        return;
    }

    private void updatePhero(AntCell input) {
        if (input.getNextState().pheromone == pheroMax) {
            input.getNextState().setName("phero3");
            return;
        }
        if (input.getNextState().pheromone >= pheroThresh1) {
            input.getNextState().setName("phero2");
            return;
        }
        if (input.getNextState().pheromone < pheroThresh1 && input.getNextState().pheromone > 0) {
            input.getNextState().setName("phero1");
            return;
        }
        input.getNextState().setName("empty");
        return;
    }

    private void reducePhero(AntCell input) {
        if (!(takenSpots.contains(input)) && input.pheromone > 0) {
            input.setNextState(new AntCell(input.x, input.y, input.getName(), input.pheromone - pheroLose, null, 0));
            updatePhero(input);
            return;
        }
        return;
    }

    private ArrayList<AntCell> neighborFilterForward(AntCell input, ArrayList<AntCell> neighbors) {
        ArrayList<AntCell> result = new ArrayList<>();
        if (input.getName().equals("tl_ant")) {
            for (AntCell n : neighbors) {
                if ((n.x == input.x - 1 && n.y == input.y) || (n.x == input.x && n.y == input.y + 1) || (n.x == input.x+getRows()-1 && n.y == input.y) || (n.x == input.x && n.y == input.y+getCols()-1)) {
                    result.add(n);
                }
            }
        }
        if (input.getName().equals("tr_ant")) {
            for (AntCell n : neighbors) {
                if ((n.x == input.x + 1 && n.y == input.y) || (n.x == input.x && n.y == input.y + 1) || (n.x == input.x-getRows()-1 && n.y == input.y) || (n.x == input.x && n.y == input.y+getCols()-1)) {
                    result.add(n);
                }
            }
        }
        if (input.getName().equals("bl_ant")) {
            for (AntCell n : neighbors) {
                if ((n.x == input.x - 1 && n.y == input.y) || (n.x == input.x && n.y == input.y - 1) || (n.x == input.x+getRows()-1 && n.y == input.y) || (n.x == input.x && n.y == input.y-getCols()-1)) {
                    result.add(n);
                }
            }
        }
        if (input.getName().equals("br_ant")) {
            for (AntCell n : neighbors) {
                if ((n.x == input.x + 1 && n.y == input.y) || (n.x == input.x && n.y == input.y - 1) || (n.x == input.x-getRows()-1 && n.y == input.y) || (n.x == input.x && n.y == input.y-getCols()-1)) {
                    result.add(n);
                }
            }
        }
        return result;
    }

    private ArrayList<AntCell> neighborFilterBackward(AntCell input, ArrayList<AntCell> neighbors) {
        ArrayList<AntCell> result = new ArrayList<>();
        if (input.getName().equals("tl_ant")) {
            for (AntCell n : neighbors) {
                if ((n.x == input.x + 1 && n.y == input.y) || (n.x == input.x && n.y == input.y - 1) || (n.x == input.x-getRows()-1 && n.y == input.y) || (n.x == input.x && n.y == input.y+getCols()-1)) {
                    result.add(n);
                }
            }
        }
        if (input.getName().equals("tr_ant")) {
            for (AntCell n : neighbors) {
                if ((n.x == input.x - 1 && n.y == input.y) || (n.x == input.x && n.y == input.y - 1)  || (n.x == input.x+getRows()-1 && n.y == input.y) || (n.x == input.x && n.y == input.y+getCols()-1)) {
                    result.add(n);
                }
            }
        }
        if (input.getName().equals("bl_ant")) {
            for (AntCell n : neighbors) {
                if ((n.x == input.x + 1 && n.y == input.y) || (n.x == input.x && n.y == input.y + 1)  || (n.x == input.x-getRows()-1 && n.y == input.y) || (n.x == input.x && n.y == input.y-getCols()-1)) {
                    result.add(n);
                }
            }
        }
        if (input.getName().equals("br_ant")) {
            for (AntCell n : neighbors) {
                if ((n.x == input.x - 1 && n.y == input.y) || (n.x == input.x && n.y == input.y + 1)  || (n.x == input.x+getRows()-1 && n.y == input.y) || (n.x == input.x && n.y == input.y-getCols()-1)) {
                    result.add(n);
                }
            }
        }
        return result;
    }

    private ArrayList<AntCell> priorityFilter(ArrayList<AntCell> locationFinder) {
        ArrayList<AntCell> food = new ArrayList<>();
        ArrayList<AntCell> phero = new ArrayList<>();
        ArrayList<AntCell> empty = new ArrayList<>();
        ArrayList<AntCell> home = new ArrayList<>();
        for (AntCell n : locationFinder) {
            if (n.getName().equals("food") && !(takenSpots.contains(n))) {
                food.add(n);
            }
            else if (n.getName().equals("empty") && !(takenSpots.contains(n))) {
                empty.add(n);
            }
            else if (n.getName().equals("home") && !(takenSpots.contains(n))) {
                home.add(n);
            }
            else if (!(antsMoved.contains(n)) && !(takenSpots.contains(n))) {
                phero.add(n);
            }
        }
        if (food.size() > 0) {
            return food;
        }
        if (phero.size() > 0) {
            return phero;
        }
        if (empty.size() > 0) {
            return empty;
        }
        return home;
    }

    public void setUpHashMap() {
        createColorMap(new HashMap<>());
        addToColorMap("tr_ant", "red");
        addToColorMap("tl_ant", "red");
        addToColorMap("br_ant", "red");
        addToColorMap("bl_ant", "red");
        addToColorMap("home", "saddlebrown");
        addToColorMap("empty", "black");
        addToColorMap("phero1", "lightgreen");
        addToColorMap("phero2", "greenyellow");
        addToColorMap("phero3", "lime");
        addToColorMap("food", "blue");
    }

    public void drawCircle(int r, String name, int food) {
        int x, y;
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                x = i - (getRows() / 2);
                y = j - (getCols() / 2);
                if (x*x + y*y <= r*r + 1) {
                    antGrid[i][j] = new AntCell(i, j, name, 0, null, food);
                    antGrid[i][j].setNextState(antGrid[i][j]);
                    setCell(i, j, name);
                }
            }
        }
    }

    private void gridCopier(AntCell[][] grid) {
        gridCopy = new AntCell[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                    gridCopy[i][j] = grid[i][j];

            }
        }
    }

    private void antSpawn() {
        String[] list = {"tr_ant", "tl_ant", "br_ant", "bl_ant"};
        int choice = (int) (Math.random() * 3.999);
        antGrid[getRows()/2][getCols()/2] = new AntCell(getRows()/2, getCols()/2, list[choice], 0, "food", 0);
        antGrid[getRows()/2][getCols()/2].setNextState(antGrid[getRows()/2][getCols()/2]);
        setCell(getRows()/2, getCols()/2, list[choice]);
        antCount++;
    }

    private void updateStringArray() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                setCell(i, j, antGrid[i][j].getName());
            }
        }
    }
}
