#### How does a Cell know about its neighbors? How can it update itself without effecting its neighbors update?

We will have a cell class that contains a top, bottom, left, and right data elements of type cell.
Cells will need a current state and a future state.
The cell can update it self without affecting its neighbors update since it will not have access to the future state of the cells near it.

#### What relationship exists between a Cell and a simulation's rules?

The cell exists by itself, the rules act on it.

#### What is the grid? Does it have any behaviors? Who needs to know about it?

The grid is the 2D array. The simulation function needs to know about the grid. 
The grid should have the simulation's rules (behavior) within an update function (so it can update the state of a cell's neighbors).
The simulation's rules need to be known by the simulation itself.

#### What information about a simulation needs to be the configuration file?
Rules, size dimensions, intial state cells.

#### How is the graphical view of the simulation updated after all the cells have been updated?
Function will run through all cells and update the graphical array accordingly depending on the cells' values.


#### Class Hierarchy
Runner class
Abstract Cell
    MiddleCell
    EdgeCell
    CornerCell
