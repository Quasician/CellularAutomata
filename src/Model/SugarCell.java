package Model;

public class SugarCell extends Cell{

    private SugarCell nextState;
    protected int capacity;
    protected int sugar;
    protected int metabolism;


    public SugarCell(int x, int y, String name, int capacity, int sugar, int metabolism) {
        super(x, y, name);
        this.capacity = capacity;
        this.sugar = sugar;
        this.metabolism = metabolism;
    }

    public SugarCell getNextState() {return nextState;}
    public void setNextState(SugarCell input) {nextState = input;}
    public void increaseSugar(int input) {sugar += input;}
    public void decreaseSugar(int input) {sugar -= input;}

}
