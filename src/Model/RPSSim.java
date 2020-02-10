package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class RPSSim extends Simulation{
    private double percentRock;
    private double percentScissors;
    private int defaultThreshold;
    private RPSCell[][] rpsGrid;

    public RPSSim(int width, int height, HashMap<String, Double> params) {
        super((int)(params.get("grid_height")*10)/10,(int)(params.get("grid_width")*10/10), width,height, params);
        initParams();
        createGrid(getRows(), getCols());
        setUpHashMap();
    }

    public void initParams() {
        defaultThreshold = (int) (getParams().get("threshold") * 10) / 10;
        percentRock = getParams().get("percentRock");
        percentScissors = getParams().get("percentScissors");
        initAddToAgentNumberMap("rock");
        initAddToAgentNumberMap("paper");
        initAddToAgentNumberMap("scissor");
    }

    public void createGrid(int rows, int cols) {
        createGrid(new String[rows][cols]);
        rpsGrid = new RPSCell[rows][cols];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                double choice = Math.random();
                if (choice <= percentRock) {
                    rpsGrid[i][j] = new RPSCell(i, j, "rock", defaultThreshold);
                    rpsGrid[i][j].setNextState(rpsGrid[i][j]);
                    setCell(i, j, "rock");
                }
                else if (choice <= percentRock + percentScissors) {
                    rpsGrid[i][j] = new RPSCell(i, j, "scissor", defaultThreshold);
                    rpsGrid[i][j].setNextState(rpsGrid[i][j]);
                    setCell(i, j, "scissor");
                }
                else {
                    rpsGrid[i][j] = new RPSCell(i, j, "paper", defaultThreshold);
                    rpsGrid[i][j].setNextState(rpsGrid[i][j]);
                    setCell(i, j, "paper");
                }
            }
        }
    }
    public void updateGrid() {
        resetAgentNumbers();
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                rpsGrid[i][j] = rpsGrid[i][j].getNextState();
                rpsGrid[i][j].setNextState(null);
            }
        }
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                updateCell(rpsGrid[i][j]);
            }
        }
        updateStringArray();
        countAgentNumbers();
    }

    public void updateCell(RPSCell input) {
        if (input.getName().equals("rock")) {
            updateRock(input);
        }
        else if (input.getName().equals("scissor")) {
            updateScissor(input);
        }
        else {
            updatePaper(input);
        }
    }

    public void updateRock(RPSCell input) {
        int paper = neighborFilter(input, "paper");
        if (paper >= defaultThreshold) {
            input.setNextState(new RPSCell(input.x, input.y, "paper", defaultThreshold));
        }
        else {
            input.setNextState(new RPSCell(input.x, input.y, "rock", defaultThreshold));
        }
    }

    public void updateScissor(RPSCell input) {
        int rock = neighborFilter(input, "rock");
        if (rock >= defaultThreshold) {
            input.setNextState(new RPSCell(input.x, input.y, "rock", defaultThreshold));
        }
        else {
            input.setNextState(new RPSCell(input.x, input.y, "scissor", defaultThreshold));
        }
    }

    public void updatePaper(RPSCell input) {
        int scissor = neighborFilter(input, "scissor");
        if (scissor >= defaultThreshold) {
            input.setNextState(new RPSCell(input.x, input.y, "scissor", defaultThreshold));
        }
        else {
            input.setNextState(new RPSCell(input.x, input.y, "paper", defaultThreshold));
        }
    }

    public int neighborFilter(RPSCell input, String name) {
        int result = 0;
        ArrayList<RPSCell> neighbors = new ArrayList<>();
        neighbors = input.get8NeighborsFinite(input.x, input.y, rpsGrid, neighbors);
        for (RPSCell n : neighbors) {
            if (n.getName().equals(name)) {
                result++;
            }
        }
        return result;
    }

    public void setUpHashMap() {
        createColorMap(new HashMap<>());
        addToColorMap("rock", "red");
        addToColorMap("paper", "blue");
        addToColorMap("scissor", "yellow");
    }

    private void updateStringArray() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                setCell(i, j, rpsGrid[i][j].getName());
            }
        }
    }
}
