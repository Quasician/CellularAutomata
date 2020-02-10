package Model;

public class RPSCell extends Cell{

    private RPSCell nextState;
    protected int threshold;


    public RPSCell(int x, int y, String name, int threshold) {
        super(x, y, name);
        this.threshold = threshold;
    }

    public RPSCell getNextState() {return nextState;}
    public void setNextState(RPSCell input) {nextState = input;}
}
