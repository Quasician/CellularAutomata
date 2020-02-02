package cellsociety;

public class Shark extends Organism {

    private int energy;

    public Shark(String name, int life, int energy, int breedThresh) {
        super(name, life, breedThresh);
        this.energy = energy;
    }

    public int getEnergy() {return energy;}
    public void setEnergy(int input) {this.energy = input;}
    public void checkStarve() {}
}
