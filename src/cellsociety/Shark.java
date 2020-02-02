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

    public void move(int x, int y, Organism[][] grid, Organism[][] gridCopy) {
        neighbors = get4Neighbors(x,y,gridCopy);
        if(!moveToEatFish(x,y,grid))
        {
            moveToKelpCell(x,y,grid);
        }
    }

    public boolean moveToEatFish(int x, int y, Organism[][] grid) {
        ArrayList<Organism> fishList = new ArrayList<Organism>();
        for(Organism i : neighbors)
        {
            if(i.getName().equals("fish"))
            {
                fishList.add(i);
            }
        }
        if(fishList.size()<=0)
        {
            return false;
        }
        Organism chosenFish = fishList.get((int)(Math.random() * fishList.size()));
        birth(chosenFish,grid);
        return true;
    }

    public void moveToKelpCell(int x, int y, Organism[][] grid) {
        ArrayList<Organism> kelpList = new ArrayList<Organism>();
        for(Organism i : neighbors)
        {
            if(i.getName().equals("kelp"))
            {
                kelpList.add(i);
            }
        }
        if(kelpList.size()<=0)
        {
            return;
        }
        Organism chosenKelp = kelpList.get((int)(Math.random() * kelpList.size()));
        birth(chosenKelp,grid);
    }

    public void setEnergy(int input) {this.energy = input;}
    public void checkStarve() {}



}
