package cellsociety;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Kelp extends Organism {
    public Kelp(String name, int x, int y) {
        super(name);
        this.x = x;
        this.y = y;
    }

    public void increaseLives() {
        return;
    }

    public void decreaseEnergy()
    {
        return;
    }

    @Override
    public void move(int x, int y, Organism[][] grid, Organism[][] gridCopy, ArrayList<Organism> emptyCells) {
        return;
    }
}
