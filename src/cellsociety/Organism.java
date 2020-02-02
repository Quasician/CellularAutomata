package cellsociety;

public abstract class Organism {

    private String name;
    private int life;
    private int breedThresh;

    public Organism(String name, int life, int breedThresh) {
        this.name = name;
        this.life = life;
        this.breedThresh = breedThresh;
    }

    public String getName() {return name;}
    public void setName(String input) {this.name = input;}
    public int getLife() {return life;}
    public void setLife(int input) {this.life = input;}
    public void move() {}
    public void birth() {}
}
