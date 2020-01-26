# Simulation Design Plan
### Team Number: 18
### Names: Thomas Chemmanoor, Vineet Alaparthi, Rodrigo Araujo

## Introduction
We need to create a general Cellular automata program that can run any of the five CA programs given to us. The primary architecture
of the design is broken up into three parts: simulation, configuration, visualization. Simulation should be open since we need it 
to work with different types of rules. The simulation is where the code needs to be the most flexible. Configuration should be closed 
since its operation is the same everytime. Visualization needs to be closed because it will be the same everytime. 

## Overview
    ADD PICTURES OF CRC CARDS
    
We plan to have a runner class should just have a main method, setupSimulation
    The runner class would do the configuration of the simulation with the setupSimulation method.
    start and update the simulation, and graphically visualize the simulation to the user. 
    
Simulation class that will contain the rules of the simulation and a constructor, startSim method, and updateGrid method.
    We will have to create an object inside the runner class for the actual simulation to start.
    
We plan to also have an abstract Cell class that has the methods:
    getState
    setState
    getTopCell
    getBottomCell
    getLeftCell
    getRightCell
    updateState
We also need a Middle Cell, Edge Cell, and Corner Cell that will extend from the abstract Cell class.

Two different implementations:
    1. the grid is 2d array
    2. the grid is map
        To make our program as flexible as possible, grid, regardless of its data structure will be made in the simulation class so 
        any methods in the simulation that need to use the grid would not need to pass in the grid. No method signatures in our program
        will need to pass in grid since all methods that need to use grid will be inside the simulation class and have access to the 
        private variable.
        
       
        
        public void setupSimulation (File file) {}
            returns a variable of type simulation that has the grid in the runner class associated to it
        

## User Interface 

    ADD PICTURE

    User will be presented with a screen that shows a grid of all cells. User has a text field that they can write a file name and click a 
    button near the textfield to load that file to restart the simulation. We will have a button for pausing, and a button for resuming
    the simulation. We will also need a button to step through the simulation. We would need three buttons that allow the user to 
    pick the simulation's speed.
    
    Erroneous situations that will be reported to the user:
        When user loads file that does not exist. 

## Design Details
    
    *Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
    
    *Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
    
    *Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
    
    *Set a simulation parameter: set the value of a global configuration parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire
    
    *Switch simulations: load a new simulation from an XML file, stopping the current running simulation, Segregation, and starting the newly loaded simulation, Wator


## Design Considerations

#### Components

#### Use Cases


## Team Responsibilities

 * Team Member #1

 * Team Member #2

 * Team Member #3

