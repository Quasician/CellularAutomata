package Model;


import java.util.ArrayList;
import java.util.HashMap;

public class PredPreySim extends Simulation {

    private double breedThreshFish;
    private double breedThreshShark;
    private double defaultSharkEnergy;
    private double defaultFishEnergy;
    private double percentFish;
    private double percentSharks;
    private Organism[][] organismGrid;
    private ArrayList<Organism> emptyCells;
    private ArrayList<Organism> fishThatNeedToMove;
    private ArrayList<Organism> sharksThatNeedToMove;

    public PredPreySim(double rows, double cols, int width, int height, HashMap<String,Double> params)
    {
        super((int)rows, (int)cols, width,height,params);
        initParams();
        createGrid((int)rows,(int)cols);
        setUpHashMap();
    }

    public void initParams()
    {
        breedThreshFish = getParams().get("breedThreshFish");
        breedThreshShark = getParams().get("breedThreshShark");
        defaultSharkEnergy = getParams().get("defaultSharkEnergy");
        defaultFishEnergy = getParams().get("defaultFishEnergy");
        percentFish = getParams().get("percentFish");
        percentSharks = getParams().get("percentSharks");
    }

    public void createGrid(int numRows, int numCols) {
        createGrid(new String[numRows][numCols]);
        organismGrid = new Organism[numRows][numCols];
        for(int i = 0; i<getRows();i++) {
            for(int j = 0; j<getCols();j++) {
                double choice = Math.random();
                //System.out.println(choice);
                if (choice<=percentFish) {
                    organismGrid[i][j] = new Organism(i,j,"fish",0, defaultFishEnergy);
                    organismGrid[i][j].setNextState(new Organism(i,j,"fish",0,defaultFishEnergy));
                    setCell(i,j,"fish");
                }
                else if (choice>=percentFish+percentSharks) {
                    organismGrid[i][j] = new Organism(i,j,"kelp",0,0);
                    organismGrid[i][j].setNextState(new Organism(i,j,"kelp",0,0));
                    setCell(i,j,"kelp");
                }
                else {
                    organismGrid[i][j] = new Organism(i,j,"shark", 0, defaultSharkEnergy);
                    organismGrid[i][j].setNextState(new Organism(i,j,"shark", 0, defaultSharkEnergy));
                    setCell(i,j,"shark");
                }
//                organismGrid[i][j] = new Organism(i,j,"kelp", 0, 0);
//                organismGrid[i][j].setNextState(new Organism(i,j,"kelp", 0, 0));
//                grid[i][j] = "kelp";
            }
        }
//        organismGrid[25][25] = new Organism(25,25,"shark", 0, defaultSharkEnergy);
//        organismGrid[25][25].setNextState(new Organism(25,25,"shark", 0, defaultSharkEnergy));
//        grid[25][25] = "shark";
//
//        organismGrid[25][26] = new Organism(25,26,"fish", 0, defaultFishEnergy);
//        organismGrid[25][26].setNextState(new Organism(25,26,"fish", 0, defaultFishEnergy));
//        grid[25][26] = "fish";
    }


    public void updateGrid() {


        sharksThatNeedToMove = new ArrayList<>();
        fishThatNeedToMove = new ArrayList<>();

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                organismGrid[i][j] = organismGrid[i][j].getNextState();
                organismGrid[i][j].setNextState(null);
            }
        }

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                updateCell(i,j);
            }
        }

        moveAllSharks();
        moveAllFish();
        updateStringArray();
    }


    public void updateCell(int x, int y) {
        if(organismGrid[x][y].getName().equals("fish")) {
            //System.out.println("FISH at X: "+ x + "  Y: "+ y + "  Lives: "+organismGrid[x][y].getLives());
            fishThatNeedToMove.add(organismGrid[x][y]);
        }
        if(organismGrid[x][y].getName().equals("shark")) {
            //System.out.print(organismGrid[x][y].getEnergy());
            sharksThatNeedToMove.add(organismGrid[x][y]);
        }
//        if(x==25 && y==25)
//        {
//            System.out.println("Current State: "+organismGrid[25][25].getName());
//            System.out.println("Energy: "+organismGrid[25][25].getEnergy());
//            //System.out.println("Next State: "+organismGrid[25][25].getNextState().getName());
//        }
        if(organismGrid[x][y].getName().equals("kelp")|| (organismGrid[x][y].getName().equals("shark") && organismGrid[x][y].getEnergy()<=0)) {
            if(organismGrid[x][y].getName().equals("shark")) {
                sharksThatNeedToMove.remove(organismGrid[x][y]);
            }
            organismGrid[x][y].setNextState(new Organism(x,y,"kelp",0,0));
        }
        updateOrganism(organismGrid[x][y]);
    }

//    public void moveOrganism(int x, int y, Organism[][] organismGridCopy) {
//        organismGridCopy[x][y].move(x,y,organismGrid,organismGridCopy, emptyCells);
//    }

    private void updateOrganism(Organism org) {
        org.increaseLives();
        if(org.getName().equals("shark")) {
            org.decreaseEnergy();
        }
    }

    private void updateStringArray() {
        for(int i = 0; i<getRows();i++) {
            for(int j = 0; j<getCols();j++) {
                setCell(i,j, organismGrid[i][j].getName());
            }
        }
    }

    public void setUpHashMap() {
        createColorMap(new HashMap<>());
        addToColorMap("fish", "blue");
        addToColorMap("shark", "gray");
        addToColorMap("kelp", "black");
    }

    public void moveAllSharks() {
        while (sharksThatNeedToMove.size()>0) {
            int sharkListIndex = (int)(Math.random()*sharksThatNeedToMove.size());
            Organism current = sharksThatNeedToMove.get(sharkListIndex);
            sharksThatNeedToMove.remove(current);


            ArrayList<Organism> fishList = current.getFish(current.getX(), current.getY(), organismGrid);
            ArrayList<Organism> kelpList = current.getKelpAndFutureEmpty(current.getX(), current.getY(), organismGrid);

            if (fishList.size() > 0) {
                int fishListIndex = (int)(Math.random()*fishList.size());
                fishThatNeedToMove.remove(fishList.get(fishListIndex));
                eatFish(current, fishList.get(fishListIndex));
            }
            else if (kelpList.size() > 0) {
                int kelpListIndex = (int)(Math.random()*kelpList.size());
                sharkMovesToKelpCell(current, kelpList.get(kelpListIndex));
            }
            else {
                current.setNextState(current);
            }
        }
    }

    public void eatFish(Organism source, Organism destination) {
        double currentEnergy = source.getEnergy();
        String name = source.getName();
        checkBreed(source);
        destination.setNextState(new Organism(destination.getX(), destination.getY(), name, source.getLives(), currentEnergy+defaultFishEnergy));
    }

    public void sharkMovesToKelpCell(Organism source, Organism destination) {
        double currentEnergy = source.getEnergy();
        String name = source.getName();
        checkBreed(source);
        destination.setNextState(new Organism(destination.getX(), destination.getY(), name, source.getLives(), currentEnergy));
    }

    public void fishMovesToKelpCell(Organism source, Organism destination) {
        double currentEnergy = source.getEnergy();
        String name = source.getName();
        checkBreed(source);
        destination.setNextState(new Organism(destination.getX(), destination.getY(), name, source.getLives(), currentEnergy));
    }

    public void checkBreed(Organism source) {

        if(source.getName().equals("fish") && source.getLives()>breedThreshFish) {
            source.setNextState(new Organism(source.getX(), source.getY(), source.getName(), 0, defaultFishEnergy));
            source.setLife(0);
        }
        else if(source.getName().equals("shark") && source.getLives()>breedThreshShark && source.getEnergy() > (defaultSharkEnergy / 2)) {
            source.setNextState(new Organism(source.getX(), source.getY(), source.getName(), 0, defaultSharkEnergy));
            source.setLife(0);
        }
         else {
            //System.out.println("YEET");
            source.setNextState(new Organism(source.getX(), source.getY(), "kelp", 0, 0));
        }
    }

    private void moveAllFish() {
        while (fishThatNeedToMove.size() > 0) {
            int fishListIndex = (int)(Math.random()*fishThatNeedToMove.size());
            Organism current = fishThatNeedToMove.get(fishListIndex);
            fishThatNeedToMove.remove(current);

            ArrayList<Organism> kelpList = current.getKelpAndFutureEmpty(current.getX(),current.getY(),organismGrid);

            if (kelpList.size() > 0) {
                int kelpListIndex = (int)(Math.random()*kelpList.size());
                fishMovesToKelpCell(current, kelpList.get(kelpListIndex));
            } else {
                current.setNextState(current);
            }
        }
    }

}