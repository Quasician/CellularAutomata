package Model;

public class RPSCell {

    private String name;
    private RPSCell nextState;
    protected int x;
    protected int y;
    protected int threshold;


    public RPSCell(int x, int y, String name, int threshold) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.threshold = threshold;
    }

    public RPSCell(String name) {
        this.name = name;
    }

    public String getName() {return name;}
    public RPSCell getNextState() {return nextState;}
    public void setNextState(RPSCell input) {nextState = input;}

    public RPSCell[] get8Neighbors(int x, int y, RPSCell[][] gridCopy, int simRows, int simCols) {
        RPSCell[] neighbors = new RPSCell[8];
        int count = 0;
        for(int i = x-1; i<=x+1;i++) {
            for(int j = y-1; j<=y+1;j++) {
                if(i == x && j == y) {
                    continue;
                }
                else {
                    if(inGrid(i,j, simRows, simCols)) {
                        neighbors[count] = gridCopy[i][j];

                    }
                    else {
                        neighbors[count] = new RPSCell("outOfBounds");
                    }
                    count++;
                }
            }
        }
        return neighbors;
    }

    public boolean inGrid(int rows, int cols, int simRows, int simCols) {
        if(rows>=0 && rows <simRows && cols>=0 && cols<simCols)
        {
            return true;
        }
        return false;
    }

}
