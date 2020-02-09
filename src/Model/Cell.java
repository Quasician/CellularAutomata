package Model;

import java.lang.reflect.Type;

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

    private <Type> Type top(Type[][] gridCopy, int x, int y) { return gridCopy[(x+gridCopy.length)%gridCopy.length][(y+1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type top_right(Type[][] gridCopy, int x, int y) { return gridCopy[(x+1+gridCopy.length)%gridCopy.length][(y+1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type top_left(Type[][] gridCopy, int x, int y) { return gridCopy[(x-1+gridCopy.length)%gridCopy.length][(y+1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type bottom(Type[][] gridCopy, int x, int y) { return gridCopy[(x+gridCopy.length)%gridCopy.length][(y-1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type bottom_right(Type[][] gridCopy, int x, int y) { return gridCopy[(x+1+gridCopy.length)%gridCopy.length][(y-1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type bottom_left(Type[][] gridCopy, int x, int y) { return gridCopy[(x-1+gridCopy.length)%gridCopy.length][(y-1+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type left(Type[][] gridCopy, int x, int y) { return gridCopy[(x-1+gridCopy.length)%gridCopy.length][(y+gridCopy[0].length)%gridCopy[0].length];}
    private <Type> Type right(Type[][] gridCopy, int x, int y) { return gridCopy[(x+1+gridCopy.length)%gridCopy.length][(y+gridCopy[0].length)%gridCopy[0].length];}

    public <Type> Type[] get4NeighborsTorroidal(int x, int y, Type[][] gridCopy, Type[] neighbors) {
        neighbors[0] = top(gridCopy, x, y);
        neighbors[1] = bottom(gridCopy, x, y);
        neighbors[2] = left(gridCopy, x, y);
        neighbors[3] = right(gridCopy, x, y);
        return neighbors;
    }
    public <Type> Type[] get8NeighborsTorroidal(int x, int y, Type[][] gridCopy, Type[] neighbors) {
        neighbors[0] = top(gridCopy, x, y);
        neighbors[1] = bottom(gridCopy, x, y);
        neighbors[2] = left(gridCopy, x, y);
        neighbors[3] = right(gridCopy, x, y);
        neighbors[4] = top_right(gridCopy, x, y);
        neighbors[5] = top_left(gridCopy, x, y);
        neighbors[6] = bottom_right(gridCopy, x, y);
        neighbors[7] = bottom_left(gridCopy, x, y);
        return neighbors;
    }

    public <Type> Type[] get4NeighborsFinite(int x, int y, Type[][] gridCopy, Type[] neighbors) {
        int count = 0;
        for(int i = x-1; i<=x+1;i++) {
            for(int j = y-1; j<=y+1;j++) {
                if((i - x + 1 + j - y + 1) % 2 == 0) {
                    continue;
                }
                else {
                    if(inGrid(i,j, gridCopy)) {
                        neighbors[count] = gridCopy[i][j];

                    }
                    count++;
                }
            }
        }
        return neighbors;
    }

    public <Type> Type[] get8NeighborsFinite(int x, int y, Type[][] gridCopy, Type[] neighbors) {
        int count = 0;
        for(int i = x-1; i<=x+1;i++) {
            for(int j = y-1; j<=y+1;j++) {
                if(i == x && j == y) {
                    continue;
                }
                else {
                    if(inGrid(i,j, gridCopy)) {
                        neighbors[count] = gridCopy[i][j];
                    }
                    count++;
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
