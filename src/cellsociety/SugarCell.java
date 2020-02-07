package cellsociety;

public class SugarCell {

    private String name;
    private SugarCell nextState;
    private SugarCell prevState;
    private int x;
    private int y;
    private int capacity;
    private int sugar;
    private int metabolism;


    public SugarCell(int x, int y, String name, int capacity, int sugar, int metabolism) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.capacity = capacity;
        this.sugar = sugar;
        this.metabolism = metabolism;
    }

    public String getName() {return name;}
    public SugarCell getNextState() {return nextState;}
    public void setNextState(SugarCell input) {nextState = input;}
    public SugarCell getPrevState() {return prevState;}
    public void setPrevState(SugarCell input) {prevState = input;}
    public void increaseSugar(int input) {sugar += input;}
    public void decreaseSugar(int input) {sugar -= input;}


    private SugarCell top(SugarCell[][] gridCopy, int x, int y) { return gridCopy[(x+gridCopy.length)%gridCopy.length][(y+1+gridCopy[0].length)%gridCopy[0].length];}
    private SugarCell bottom(SugarCell[][] gridCopy, int x, int y) { return gridCopy[(x+gridCopy.length)%gridCopy.length][(y-1+gridCopy[0].length)%gridCopy[0].length];}
    private SugarCell left(SugarCell[][] gridCopy, int x, int y) { return gridCopy[(x-1+gridCopy.length)%gridCopy.length][(y+gridCopy[0].length)%gridCopy[0].length];}
    private SugarCell right(SugarCell[][] gridCopy, int x, int y) { return gridCopy[(x+1+gridCopy.length)%gridCopy.length][(y+gridCopy[0].length)%gridCopy[0].length];}

    public SugarCell[] get4Neighbors(int x, int y, SugarCell[][] gridCopy) {
        SugarCell[] neighbors = new SugarCell[4];
        neighbors[0] = top(gridCopy, x, y);
        neighbors[1] = bottom(gridCopy, x, y);
        neighbors[2] = left(gridCopy, x, y);
        neighbors[3] = right(gridCopy, x, y);
        return neighbors;
    }
}
