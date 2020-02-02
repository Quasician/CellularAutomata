package cellsociety;


public abstract class Simulation {

    protected int simRows, simCols;
    protected int simWidth, simHeight;

    protected String[][] grid;

    public Simulation(int rows, int cols, int width, int height){
        this.simRows = rows;
        this.simCols = cols;
        this.simWidth = width;
        this.simHeight = height;

    }

    public int getWidth(){return simWidth;}
    public int getHeight(){return simHeight;}
    public int getColPos(){return simCols;}
    public int getRowPos(){return simRows;}


    public abstract void updateCell(int x, int y,String[][]gridCopy);

    public abstract void createGrid(int numRows, int numCols);

    public abstract void updateGrid();

    public String[][] getGrid()
    {
        return grid;
    }

    public boolean inGrid(int rows, int cols)
    {
        if(rows>=0 && rows <simRows && cols>=0 && cols<simCols)
        {
            return true;
        }
        return false;
    }

    public String[] get4Neighbors(int x, int y)
    {
        String[] neighbors = new String[4];
        if(x >= 1)
        {
            neighbors[0]=grid[x-1][y];
        }
        if(x <= grid.length-2)
        {
            neighbors[1]=grid[x-1][y];
        }
        if(y >= 1)
        {
            neighbors[2]=grid[x][y-1];
        }
        if(y <= grid[0].length-2)
        {
            neighbors[2]=grid[x][y+1];
        }
        return neighbors;
    }

    public String[] get8Neighbors(int x, int y, String[][] gridCopy)
    {
        String[] neighbors = new String[8];
        int count = 0;
        for(int i = x-1; i<=x+1;i++)
        {
            for(int j = y-1; j<=y+1;j++)
            {
                if(i == x && j == y)
                {
                    continue;
                }
                else {
                    if(inGrid(i,j))
                    {
                        neighbors[count] = gridCopy[i][j];
                        //System.out.println(neighbors[count]);
//                        if(x==26 && y ==26)
//                        {
//                            System.out.println(i + " " + j + neighbors[count]);
//
//                        }
                    } else {
                        neighbors[count] = "outOfBounds";
                    }
                    count++;
                }
            }
        }
        return neighbors;
    }


}
