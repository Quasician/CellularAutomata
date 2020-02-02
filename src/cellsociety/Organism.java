package cellsociety;

public class Organism {

    private String name;
    private int life;
    private int energy;

    public Organism(String name, int life, int energy) {
        this.name = name;
        this.life = life;
        this.energy = energy;
    }

    public String getName() {return name;}
    public void setName(String input) {this.name = input;}
    public int getLife() {return life;}
    public void setLife(int input) {this.life = input;}
    public int getEnergy() {return energy;}
    public void setEnergy(int input) {this.energy = input;}
}
