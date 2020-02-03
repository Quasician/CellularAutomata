package cellsociety;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Fish extends Organism {
    public Fish(int x, int y, String name, int life, int energy, int breedThresh) {
        super(x,y,name, life, breedThresh, energy);
    }

    public void increaseLives() {
        //System.out.println("YEET");
        lives++;
    }

    public void decreaseEnergy()
    {
        return;
    }


    public void move(int x, int y, Organism[][] grid, Organism[][] gridCopy, ArrayList<Organism> emptyCells)
    {
        neighbors = get4Neighbors(x,y,gridCopy);
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
        birth(chosenKelp,grid, x,y);
    }

}
