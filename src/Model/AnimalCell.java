package Model;

import java.util.ArrayList;

public class AnimalCell extends Cell{

    private AnimalCell nextState;
    private double lives;
    private double energy;

    public AnimalCell(int x, int y, String name, double lives, double energy) {
        super(x, y, name);
        this.lives = lives;
        this.energy = energy;
    }

    public ArrayList<AnimalCell> getFish(int x, int y, AnimalCell[][] gridCopy) {
        AnimalCell[] neighbors = new AnimalCell[4];
        neighbors = get4NeighborsTorroidal(x,y,gridCopy, neighbors);
        ArrayList<AnimalCell> fishList = new ArrayList<AnimalCell>();
        for(AnimalCell i: neighbors)
        {
            if(i.getName().equals("fish"))
            {
                fishList.add(i);
            }
        }
        return fishList;
    }

    public ArrayList<AnimalCell> getKelpAndFutureEmpty(int x, int y, AnimalCell[][] gridCopy) {
        AnimalCell[] neighbors = new AnimalCell[4];
        neighbors = get4NeighborsTorroidal(x,y,gridCopy, neighbors);
        ArrayList<AnimalCell> kelpAndFutureEmptyList = new ArrayList<AnimalCell>();
        for(AnimalCell i: neighbors)
        {
            if(i.getName().equals("kelp") && (i.getNextState() == null || i.getNextState().getName().equals("kelp")))
            {
                kelpAndFutureEmptyList.add(i);
            }
        }
        return kelpAndFutureEmptyList;
    }

    public AnimalCell getNextState() {return nextState;}

    public void setNextState(AnimalCell input)
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
