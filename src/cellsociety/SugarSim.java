package cellsociety;

import javafx.scene.paint.Color;

import java.util.HashMap;

public class SugarSim extends Simulation {

    private int capacity;
    private int metabolism;
    private int defaultSugar;
    private double percentAgent;
    private double percentSugarFull;
    private double percentSugarHalf;
    private double percentSugarZero;


    public SugarSim(double rows, double cols, int width, int height, HashMap<String, Double> params) {
        super((int) rows, (int) cols, width, height, params);
        initParams();
        createGrid((int) rows, (int) cols);
        setUpHashMap();
    }

    public void initParams() {
        capacity = (int) (getParams().get("capacity") * 10) / 10;
        metabolism = (int) (getParams().get("metabolism") * 10) / 10;
        defaultSugar = (int) (getParams().get("defaultSugar") * 10) / 10;
        percentAgent = getParams().get("percentAgent");
        percentSugarFull = getParams().get("percentSugarFull");
        percentSugarHalf = getParams().get("percentSugarHalf");
        percentSugarZero = getParams().get("percentSugarZero");
    }

    public void createGrid(int rows, int cols) {
        SugarCell[][] sugarGrid = new SugarCell[rows][cols];
        for(int i = 0; i<getRows();i++) {
            for(int j = 0; j<getCols();j++) {
                
            }
        }
    }

    public void updateGrid() {

    }

    public void updateCell() {

    }

    public void setUpHashMap() {
        createColorMap(new HashMap<>());
        addToColorMap("agent", "red");
        addToColorMap("sugar_full", "gold");
        addToColorMap("sugar_half", "yellow");
        addToColorMap("sugar_zero", "lemonchiffon");
    }
}
