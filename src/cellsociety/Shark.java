package cellsociety;

import java.util.ArrayList;

public class Shark extends Organism {

    public Shark(int x, int y, String name, int life, int energy, int breedThresh) {
        super(x, y,name, life, breedThresh, energy);
    }

    public void increaseLives() {
        lives++;
    }

    public void decreaseEnergy()
    {
        energy--;
    }

    public void move(int x, int y, Organism[][] grid, Organism[][] gridCopy, ArrayList<Organism> emptyCells) {
        neighbors = get4Neighbors(x,y,gridCopy);
        if(!moveToEatFish(x,y,grid, emptyCells))
        {
            moveToKelpCell(x,y,grid, emptyCells);
        }
    }

    public boolean moveToEatFish(int x, int y, Organism[][] grid, ArrayList<Organism> emptyCells) {
        ArrayList<Organism> fishList = new ArrayList<Organism>();
        for(Organism i : neighbors)
        {
            if(i.getName().equals("fish") && emptyCells.contains(i))
            {
                fishList.add(i);
            }
        }
        if(fishList.size()<=0)
        {
            return false;
        }
        Organism chosenFish = fishList.get((int)(Math.random() * fishList.size()));
        emptyCells.remove(chosenFish);
        birthWithFish(chosenFish,grid, x,y);
        return true;
    }

    public void moveToKelpCell(int x, int y, Organism[][] grid, ArrayList<Organism> emptyCells) {
        ArrayList<Organism> kelpList = new ArrayList<Organism>();
        for(Organism i : neighbors)
        {
            if(i.getName().equals("kelp") && emptyCells.contains(i))
            {
                kelpList.add(i);
            }
        }
        if(kelpList.size()<=0)
        {
            return;
        }
        Organism chosenKelp = kelpList.get((int)(Math.random() * kelpList.size()));
        emptyCells.remove(chosenKelp);
        birthWithKelp(chosenKelp,grid,x,y);
    }

    public void setEnergy(int input) {this.energy = input;}
    public void checkStarve() {}

    public void birthWithKelp(Organism chosen, Organism[][] grid, int x, int y)
    {
        if(getLives()>=getBreedThresh())
        {
            grid[chosen.x][chosen.y]= new Shark(chosen.x,chosen.y,"shark",0, getEnergy(), getBreedThresh());
            setLife(0);
            setEnergy(defaultSharkEnergy);
            System.out.println("reset for birth");
            return;
        }

        System.out.println("NOT ENOUGH LIVES FOR RESET");
        grid[chosen.x][chosen.y]= new Shark(chosen.x,chosen.y,"shark",getLives(), getEnergy(), getBreedThresh());
        grid[x][y]= new Kelp("kelp", x, y);

    }

    public void birthWithFish(Organism chosen, Organism[][] grid, int x, int y)
    {
        if(getLives()>=getBreedThresh())
        {
            grid[chosen.x][chosen.y]= new Shark(chosen.x,chosen.y,"shark",0, getEnergy()+defaultFishEnergy, getBreedThresh());
            setLife(0);
            setEnergy(defaultSharkEnergy);
            System.out.println("reset for birth");
            return;
        }

        System.out.println("NOT ENOUGH LIVES FOR RESET");
        grid[chosen.x][chosen.y]= new Shark(chosen.x,chosen.y,"shark",getLives(), getEnergy()+defaultFishEnergy, getBreedThresh());
        grid[x][y]= new Kelp("kelp", x, y);
    }

}
