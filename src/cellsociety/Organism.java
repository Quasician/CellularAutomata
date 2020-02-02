package cellsociety;

public abstract class Organism {

    protected String name;
    protected int lives;
    protected int breedThresh;
    protected int energy;
    protected int x;
    protected int y;
    protected Organism[] neighbors;

    public Organism(int x, int y, String name, int lives, int breedThresh, int energy) {
        this.name = name;
        this.lives = lives;
        this.breedThresh = breedThresh;
        this.energy = energy;
        this.x = x;
        this.y = y;
    }

    public Organism(String name) {
        this.name = name;
    }

    public String getName() {return name;}

    public abstract void increaseLives();

    public abstract void decreaseEnergy();

    public int getEnergy() {return energy;}
    public int getBreedThresh() {return breedThresh;}

    public abstract void move(int x, int y, Organism[][] grid, Organism[][] gridCopy);

    public Organism[] get4Neighbors(int x, int y, Organism[][] gridCopy) {
        Organism[] neighbors = new Organism[4];
        int count = 0;
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
                    count++;
                }
            }
        }
        return neighbors;
    }

    public void setName(String input) {this.name = input;}
    public int getLife() {return lives;}
    public void setLife(int input) {this.lives = input;}
    public void birth(Organism chosen, Organism[][] grid)
    {
        if(getLife()>=getBreedThresh())
        {
            setLife(0);
        }
        else
        {
            grid[x][y]= new Kelp("kelp");
        }
        grid[chosen.x][chosen.y]= new Fish(chosen.x,chosen.y, getName(), getLife(),getBreedThresh(), getEnergy());
    }
}
