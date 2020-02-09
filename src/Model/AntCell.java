package Model;

public class AntCell extends Cell {

    private AntCell nextState;

    public AntCell(int x, int y, String name) {
        super(x, y, name);
    }

    public void setNextState(AntCell input) {this.nextState = input;}
    public AntCell getNextState() {return nextState;}
}
