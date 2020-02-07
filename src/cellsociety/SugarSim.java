package cellsociety;

import java.util.HashMap;

public class SugarSim extends Simulation {

    public SugarSim(double rows, double cols, int width, int height, HashMap<String, Double> params) {
        super((int) rows, (int) cols, width, height, params);
        initParams();
        createGrid((int) rows, (int) cols);
        setUpHashMap();
    }

    public void initParams() {
        
    }

    public void createGrid(int rows, int cols) {

    }

    public void updateGrid() {

    }

    public void updateCell() {

    }

    public void setUpHashMap() {

    }
}
