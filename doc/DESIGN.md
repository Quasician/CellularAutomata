# Simulation Design Final
### Names

 * Thomas Chemmanoor

 * Rodrigo Araujo

 * Vineet Alaparthi

## Team Roles and Responsibilities

 * Thomas Chemmanoor": Generalist. Helped create and debug basic implementation simulations, revamped xml writing (writing each cell into a file to load from)
  and xml parsing (file that created sims from files). I created the Visualizer class as well as the autoscaling cell feature for the window. 
  I created common methods used across all simulations to keep track of the number of each sim's states so
  that we could supply this info to the graph. Worked on exception handling towards the end of the project with Vineet.

 * Rodrigo Araujo

 * Vineet Alaparthi


## Design goals

#### What Features are Easy to Add
One of the features that is easy to add are new simulations. This is because there is an abstract simulation class, that makes it relatively  easy to create and add new simulation. While the rules of the simulation still need to be implemented (this is not something that can be abstracted as it is a unique feature of each simulation) the abstraction allows for the other parts of the simulation to be implemented easier than if it was not there. Another feature that we designed to be easy to implement was the graph boundaries (finite, toroidal). Our design allows it so that if we added a tag to the xml file for the graph boundary, we could implement the boundary for the simulation based on whatever is contained in this tag. This is because there is an abstract cell class, that contains methods based on the type of graph boundary in order to find the neighbors of the cell. These are a few of the features that we decided to make easy to implement.


## High-level Design

#### Core Classes
The design of the project starts with the configuration. This is done by the XML parser, which reads the XML files that contain the information needed to create the simulation objects. The information read from the parser is then stored in the parameters hashmap, and used to create the simulations in the Main class. The way the simulation part is designed is there is an abstract Simulation class, and various types of simulations extend this abstract class. The abstract class contains various methods, constructors, and instance variables that are used by all the simulation sub-classes, such as checking the neighbors (method for both 4 neighbors and 8 neighbors) of the cells that are used to represent various states based on the simulation. The data structure used to hold all the cells for each simulation was a 2D array, and each subclass implemented a createGrid and setCell method that created the 2D array and then set each individual cell based on the desired state. In order to update the state of the grid, a temporary, duplicate grid is created, and then the updated cell states are placed in this grid. Once all the updated cell states have been placed in this grid, the actual grid is updated to match this. The visualization of the grid is handled by the Visualizer class. This class takes in the simulation that is supposed to be displayed, and creates rectangle objects to represent each cell in the 2D array that contains the states of the cells in the simulation. It then uses the instance variable Colormap hashmap which has the cell state as keys and the color associated with it as values, and based on the state of the cell, the visualizer makes each rectangle its correct color. This method (colorGrid) is called repeatedly in the animation loop along with the updateGrid method, so that as the simulation goes along, the display updates as well. Finally, the Main class ties everything together. It contains the start method, which sets up the stage and the initial scene, and also creates the animation loop that, as described above, updates the simulation and the displayed grid. In addition, the Main class creates the rest of the UI, such as the various buttons, the graph, etc.  


## Assumptions that Affect the Design

#### Features Affected by Assumptions
In order to simplify our project’s design, some decisions were made. Firstly, we decided to create an abstract simulation class because we knew we had to implement at least 5 simulations, and possibly more based on the complete implementation. So, we knew that making an abstract simulation class would benefit us and lead to better design in the long run as all the simulations contained many similar features (the only thing that made the simulations distinctive were the individual simulation rules and cell states), and so this choice greatly simplified our design. Also, we created an abstract cell class, which allowed us to change the boundary of the simulation for every type of cell  (some of the simulations use a corresponding cell class, which are subclasses of this abstract cell class). This is a choice we made midway through the project, after seeing what the complete implementation required and the new simulations that had to be implemented. 


## New Features HowTo

#### Easy to Add Features

#### Other Features not yet Done

