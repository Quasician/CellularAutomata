package cellsociety;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Fish extends Organism {


    public Fish(int x, int y, String name, int life, int energy, int breedThresh) {
        super(x,y,name, life, breedThresh, energy);
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

    public void birth(Organism chosen, Organism[][] grid, int x, int y)
    {
        if(getLives()>=getBreedThresh())
        {
            setLife(0);
            System.out.println("reset for birth");
        }
        else
        {
            System.out.println("NOT ENOUGH LIVES FOR RESET");
            grid[x][y]= new Kelp("kelp", x, y);
        }

        grid[chosen.x][chosen.y]= new Fish(x,y,"fish",getLives(), getEnergy(), getBreedThresh());
        return;

    }
}
