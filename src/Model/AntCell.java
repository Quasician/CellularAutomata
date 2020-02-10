package Model;

public class AntCell extends Cell {

    protected int pheromone;
    protected int food;
    private String mission;
    private AntCell nextState;

    public AntCell(int x, int y, String name, int pheromone, String mission, int food) {
        super(x, y, name);
        this.pheromone = pheromone;
        this.mission = mission;
        this.food = food;
    }

    private void setMission(String input) {this.mission = mission;}
    private String getMission() {return mission;}
    private void setNextState(AntCell input) {this.nextState = input;}
    private AntCell getNextState() {return nextState;}
    private int increasePhero(int input) {return this.pheromone += input;}


    private AntCell top(AntCell[][] gridCopy, int x, int y) { return gridCopy[(x+gridCopy.length)%gridCopy.length][(y+1+gridCopy[0].length)%gridCopy[0].length];}
    private AntCell bottom(AntCell[][] gridCopy, int x, int y) { return gridCopy[(x+gridCopy.length)%gridCopy.length][(y-1+gridCopy[0].length)%gridCopy[0].length];}
    private AntCell left(AntCell[][] gridCopy, int x, int y) { return gridCopy[(x-1+gridCopy.length)%gridCopy.length][(y+gridCopy[0].length)%gridCopy[0].length];}
    private AntCell right(AntCell[][] gridCopy, int x, int y) { return gridCopy[(x+1+gridCopy.length)%gridCopy.length][(y+gridCopy[0].length)%gridCopy[0].length];}

    public AntCell[] get4Neighbors(int x, int y, AntCell[][] gridCopy) {
        AntCell[] neighbors = new AntCell[4];
        neighbors[0] = top(gridCopy, x, y);
        neighbors[1] = bottom(gridCopy, x, y);
        neighbors[2] = left(gridCopy, x, y);
        neighbors[3] = right(gridCopy, x, y);
        return neighbors;
    }
}
