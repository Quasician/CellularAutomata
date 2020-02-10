package Model;

import java.util.ArrayList;

public abstract class Cell {
    private String name;
    protected int x;
    protected int y;

    public Cell(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public String getName() {return name;}
    public void setName(String input) {this.name = input;}
    private <Type> Type top(Type[][] gridCopy, int x, int y) { return gridCopy[(x+gridCopy.length)%gridCopy.length][(y+1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type top_right(Type[][] gridCopy, int x, int y) { return gridCopy[(x+1+gridCopy.length)%gridCopy.length][(y+1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type top_left(Type[][] gridCopy, int x, int y) { return gridCopy[(x-1+gridCopy.length)%gridCopy.length][(y+1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type bottom(Type[][] gridCopy, int x, int y) { return gridCopy[(x+gridCopy.length)%gridCopy.length][(y-1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type bottom_right(Type[][] gridCopy, int x, int y) { return gridCopy[(x+1+gridCopy.length)%gridCopy.length][(y-1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type bottom_left(Type[][] gridCopy, int x, int y) { return gridCopy[(x-1+gridCopy.length)%gridCopy.length][(y-1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type left(Type[][] gridCopy, int x, int y) { return gridCopy[(x-1+gridCopy.length)%gridCopy.length][(y+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type right(Type[][] gridCopy, int x, int y) { return gridCopy[(x+1+gridCopy.length)%gridCopy.length][(y+gridCopy[0].length)%gridCopy[0].length];}

    public <Type> ArrayList<Type> get4NeighborsTorroidal(int x, int y, Type[][] gridCopy, ArrayList<Type> neighbors) {
        for(int i = x-1; i<=x+1;i++) {
            for(int j = y-1; j<=y+1;j++) {
                if((i - x + 1 + j - y + 1) % 2 == 0) {
                    continue;
                }
                else {
                    neighbors.add(gridCopy[(i+gridCopy.length)%gridCopy.length][(j+gridCopy[0].length)%gridCopy[0].length]);
                }
            }
        }
        return neighbors;
    }


    public <Type> ArrayList<Type>  get8NeighborsTorroidal(int x, int y, Type[][] gridCopy, ArrayList<Type> neighbors) {
        neighbors.add(top(gridCopy, x, y));
        neighbors.add(bottom(gridCopy, x, y));
        neighbors.add(left(gridCopy, x, y));
        neighbors.add(right(gridCopy, x, y));
        neighbors.add(top_right(gridCopy, x, y));
        neighbors.add(top_left(gridCopy, x, y));
        neighbors.add(bottom_right(gridCopy, x, y));
        neighbors.add(bottom_left(gridCopy, x, y));
        return neighbors;
    }

    public <Type> ArrayList<Type> get4NeighborsFinite(int x, int y, Type[][] gridCopy, ArrayList<Type> neighbors) {
        for(int i = x-1; i<=x+1;i++) {
            for(int j = y-1; j<=y+1;j++) {
                if((i - x + 1 + j - y + 1) % 2 == 0) {
                    continue;
                }
                else if(inGrid(i,j, gridCopy)) {
                    neighbors.add(gridCopy[i][j]);
                }
            }
        }
        return neighbors;
    }

    public <Type> ArrayList<Type> get8NeighborsFinite(int x, int y, Type[][] gridCopy, ArrayList<Type> neighbors) {
        for(int i = x-1; i<=x+1;i++) {
            for(int j = y-1; j<=y+1;j++) {
                if(i == x && j == y) {
                    continue;
                }
                else if(inGrid(i,j, gridCopy)) {
                    neighbors.add(gridCopy[i][j]);
                }
            }
        }
        return neighbors;
    }

    public boolean inGrid(int rows, int cols, Object[][] gridCopy) {
        if(rows >= 0 && rows < gridCopy.length && cols >= 0 && cols < gridCopy[0].length) {
            return true;
        }
        return false;
    }
}
