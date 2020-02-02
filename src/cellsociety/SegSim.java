package cellsociety;

import javafx.scene.paint.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class SegSim extends Simulation{

    private double probSatisfy = 0.75;

    public SegSim(int rows, int cols, int width, int height)
    {
        super(rows, cols, width,height);
        createGrid(rows,cols);
        setUpHashMap();
    }

    public void createGrid(int numRows, int numCols) {
        grid = new String[numRows][numCols];
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                ArrayList<String> list = new ArrayList<>();
                list.add("x");
                list.add("o");
                list.add("empty");
                String choice = list.get((int)Math.round(2 * Math.random()));
                grid[i][j] = choice;
            }
        }
        printCount();
    }
    public void updateGrid() {
        String[][] gridCopy = new String[simRows][simCols];
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                gridCopy[i][j] = grid[i][j];
            }
        }
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                updateCell(i,j,gridCopy);
            }
        }
        printCount();
    }

    public void printCount() {
        int countRed = 0;
        int countBlue = 0;
        int countWhite = 0;
        for(int i = 0; i<simRows;i++)
        {
            for(int j = 0; j<simCols;j++)
            {
                if(grid[i][j] == "x") {
                    countRed++;
                }
                if(grid[i][j] == "o") {
                    countBlue++;
                }
                if(grid[i][j] == "empty") {
                    countWhite++;
                }
            }
        }
        System.out.println("Red: " + countRed + "   Blue: " + countBlue + "   White: " + countWhite);
    }

    public void updateCell(int x, int y, String[][]gridCopy) {
        String[] neighbors = get8Neighbors(x,y, gridCopy);
        double satisfaction;
        int sameCount = 0;
        int total = 0;
        if (gridCopy[x][y] == "empty") {
            return;
        }
        for(int i = 0; i<neighbors.length;i++)
        {
            if(neighbors[i].equals(gridCopy[x][y]))
            {
                sameCount++;
            }
            if(!neighbors[i].equals("empty")) {
                total++;
            }
        }
        if (total == 0) {
            return;
        }
        satisfaction = ((double) sameCount) / total;
        if (satisfaction < probSatisfy) {
            ArrayList<Integer> temp = randEmpty(gridCopy);
            grid[temp.get(0)][temp.get(1)] = gridCopy[x][y];
            grid[x][y] = "empty";
        }

    }
    public ArrayList<Integer> randEmpty(String[][] gridCopy) {
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < simRows; i++) {
            for (int j = 0; j < simCols; j++) {
                if (gridCopy[i][j].equals("empty")) {
                    x.add(i);
                    y.add(j);
                }
            }
        }
        int index = (int) (x.size() * Math.random());
        result.add(x.get(index));
        result.add(y.get(index));
        return result;
    }
    public void setUpHashMap()
    {
        colorMap = new HashMap<>();
        colorMap.putIfAbsent("empty", Color.WHITE);
        colorMap.putIfAbsent("x", Color.RED);
        colorMap.putIfAbsent("o", Color.BLUE);
    }
}
