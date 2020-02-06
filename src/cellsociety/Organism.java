package cellsociety;

import java.util.ArrayList;

public class Organism {

    private String name;
    private Organism nextState;
    private double lives;
    private double breedThresh;
    private double energy;
    private int x;
    private int y;
    private Organism[] neighbors;

    public Organism(int x, int y, String name, double lives, double energy) {
        this.name = name;
        this.lives = lives;
        this.energy = energy;
        this.x = x;
        this.y = y;
    }

    public Organism(String name) {
        this.name = name;
    }

    public String getName() {return name;}
    public Organism getNextState() {return nextState;}

    public void setNextState(Organism input)
    {
       nextState = input;
    }

    public void increaseLives() {lives++;}

    public void decreaseEnergy() {energy--;}


    public double getEnergy() {return energy;}
    public void setEnergy(int input) {energy = input;}
    public double getBreedThresh() {return breedThresh;}

    //public void move(int x, int y, Organism[][] grid, Organism[][] gridCopy, ArrayList<Organism> emptyCells);

//    public Organism top(Organism[][] gridCopy, int x, int y) { return gridCopy[(x+gridCopy.length)%gridCopy.length][(y+1+gridCopy[0].length)%gridCopy[0].length];}
//    public Organism bottom(Organism[][] gridCopy, int x, int y) { return gridCopy[(x+gridCopy.length)%gridCopy.length][(y-1+gridCopy[0].length)%gridCopy[0].length];}
//    public Organism left(Organism[][] gridCopy, int x, int y) { return gridCopy[(x-1+gridCopy.length)%gridCopy.length][(y+gridCopy[0].length)%gridCopy[0].length];}
//    public Organism right(Organism[][] gridCopy, int x, int y) { return gridCopy[(x+1+gridCopy.length)%gridCopy.length][(y+gridCopy[0].length)%gridCopy[0].length];}
//
//    public Organism[] get4Neighbors(int x, int y, Organism[][] gridCopy) {
//        Organism[] neighbors = new Organism[4];
//        neighbors[0] = top(gridCopy, x, y);
//        neighbors[1] = bottom(gridCopy, x, y);
//        neighbors[2] = left(gridCopy, x, y);
//        neighbors[3] = right(gridCopy, x, y);
//        return neighbors;
//    }

    private Organism[] get4Neighbors(int x, int y, Organism[][] gridCopy) {
        Organism[] neighbors = new Organism[4];
        int count = 0;
        //System.out.println("ORIG X: "+ x + " ORIG Y: "+y);
        for(int i = x-1; i<=x+1;i++)
        {
            for(int j = y-1; j<=y+1;j++)
            {
                if((i - x + 1 + j - y + 1) % 2 == 0)
                {
                    continue;
                }
                else {
                    neighbors[count] = gridCopy[(i+gridCopy.length)%gridCopy.length][(j+gridCopy[0].length)%gridCopy[0].length];
                    //System.out.println("X: "+ (i+gridCopy.length)%gridCopy.length + " Y: "+(j+gridCopy[0].length)%gridCopy[0].length);
                    count++;
                }
            }
        }
        //System.out.println("END OF NEIGHBOR LIST");
        return neighbors;

    }

    public ArrayList<Organism> getFish(int x, int y, Organism[][] gridCopy)
    {
        Organism[] neighbors = get4Neighbors(x,y,gridCopy);
        ArrayList<Organism> fishList = new ArrayList<Organism>();
        for(Organism i: neighbors)
        {
            if(i.getName().equals("fish"))
            {
                fishList.add(i);
            }
        }
        return fishList;
    }

    public ArrayList<Organism> getKelpAndFutureEmpty(int x, int y, Organism[][] gridCopy)
    {
        Organism[] neighbors = get4Neighbors(x,y,gridCopy);
        ArrayList<Organism> kelpAndFutureEmptyList = new ArrayList<Organism>();
        for(Organism i: neighbors)
        {
            if(i.getName().equals("kelp") && (i.getNextState() == null || i.getNextState().getName().equals("kelp")))
            {
                kelpAndFutureEmptyList.add(i);
            }
        }
        return kelpAndFutureEmptyList;
    }

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
