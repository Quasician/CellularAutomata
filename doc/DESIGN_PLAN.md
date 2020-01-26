# Simulation Design Plan
### Team Number: 18
### Names: Thomas Chemmanoor, Vineet Alaparthi, Rodrigo Araujo

## Introduction
We need to create a general Cellular automata program that can run any of the five CA programs given to us. The primary architecture
of the design is broken up into three parts: simulation, configuration, visualization. Simulation should be open since we need it 
to work with different types of rules. The simulation is where the code needs to be the most flexible. Configuration should be closed 
since its operation is the same everytime. Visualization needs to be closed because it will be the same everytime. 

## Overview
We plan to have a runner class should just have a main method, setupSimulation
    The runner class would do the configuration of the simulation with the setupSimulation method.
    start and update the simulation, and graphically visualize the simulation to the user. 
Simulation class that will contain the rules of the simulation and a setup method, startSim method, endSim method, 
and updateGrid method.
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
    the grid is 2d array
    the grid is map
        To make our program as flexible as possible, grid, regardless of its data structure will be made in the runner class so 
        any methods in the Runner class would not need to pass in the grid.
        
        Runner file
        
        public void setupSimulation () {}

## User Interface


## Design Details


## Design Considerations

#### Components

#### Use Cases


## Team Responsibilities

 * Team Member #1

 * Team Member #2

 * Team Member #3

