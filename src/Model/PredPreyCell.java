package Model;

import java.util.ArrayList;

public class PredPreyCell extends Cell{

    private PredPreyCell nextState;
    private double lives;
    private double energy;

    public PredPreyCell(int x, int y, String name, double lives, double energy) {
        super(x, y, name);
        this.lives = lives;
        this.energy = energy;
    }

    public ArrayList<PredPreyCell> getFish(int x, int y, PredPreyCell[][] gridCopy) {
        ArrayList<PredPreyCell> neighbors = new ArrayList<>();
        neighbors = get4NeighborsTorroidal(x,y,gridCopy, neighbors);
        ArrayList<PredPreyCell> fishList = new ArrayList<PredPreyCell>();
        for(PredPreyCell i: neighbors) {
            if(i.getName().equals("fish")) {
                fishList.add(i);
            }
        }
        return fishList;
    }

    public ArrayList<PredPreyCell> getKelpAndFutureEmpty(int x, int y, PredPreyCell[][] gridCopy) {
        ArrayList<PredPreyCell> neighbors = new ArrayList<>();
        neighbors = get4NeighborsTorroidal(x,y,gridCopy, neighbors);
        ArrayList<PredPreyCell> kelpAndFutureEmptyList = new ArrayList<PredPreyCell>();
        for(PredPreyCell i: neighbors) {
            if(i.getName().equals("kelp") && (i.getNextState() == null || i.getNextState().getName().equals("kelp"))) {
                kelpAndFutureEmptyList.add(i);
            }
        }
        return kelpAndFutureEmptyList;
    }

    public PredPreyCell getNextState() {return nextState;}

    public void setNextState(PredPreyCell input)
    {
        nextState = input;
    }

    public void increaseLives() {lives++;}

    public void decreaseEnergy() {energy--;}

    public double getEnergy() {return energy;}

    public double getLives() {return lives;}

    public void setLife(int input) {this.lives = input;}

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
