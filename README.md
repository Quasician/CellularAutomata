simulation
====

This project implements a cellular automata simulator.

Names:
Vineet Alaparthi
Rodrigo Araujo
Thomas Chemmanoor

### Timeline

Start Date: 1/27/2020

Finish Date: 2/10/2020

Hours Spent: 85+

### Primary Roles
Vineet: Worked on UI components, such as buttons, graphs, loading, saving, and displaying simulations, etc. Also worked on configuration
 with Thomas such as creating, reading, and formatting XML files, as well as error checking.

Thomas: Worked on configuration with Vineet (such as creating, reading, and formatting XML files), error checking, and worked 
on the simulations with Rodrigo, doing tasks such as creating various shapes to use during the simulation and creating the various
models to run various simulations.

Rodrigo: Handled completing the simulations with help from Thomas. Created abstract cell class to make simulations that inherit
it more efficient by not having to repeat the different neighboring functions. This allows the user to switch between 
torroidal and finite boundaries. Created configuration properties file and relavent methods. Minor XML and UI work. 


### Resources Used
http://tutorials.jenkov.com/javafx/filechooser.html
https://levelup.gitconnected.com/realtime-charts-with-javafx-ed33c46b9c8d
https://docs.oracle.com/javafx/2/charts/line-chart.htm#CIHGBCFI
https://examples.javacodegeeks.com/desktop-java/javafx/javafx-borderpane-example/
https://examples.javacodegeeks.com/core-java/xml/parsers/documentbuilderfactory/modify-xml-file-in-java-using-dom-parser-example/
https://www.codejava.net/coding/reading-and-writing-configuration-for-java-application-using-properties-class#SetProperties


Various StackOverflow links

### Running the Program

Main class: 

To run the program open, View/Main.java

Data files needed: 

To run any simulation we need files that are formatted of a specific type. For Game of Life, it should be game_of_life.xml.
For Percolate it should be percolate.xml, etc. It is ok if these are followed by numbers (this is generally how the custom configs
are saved during the simulation), but any other file name will throw an error. 

Features implemented:
    --Able to load in seven out of nine simulations.
            Ant simulation had to be cut short due to time constraint but was almost functioning. Had to fix edge cases
            that caused cells to be turned into null values.
    --Able to create and display graph of various cell types present during the simulation in real time.
    --Able to save current configuration of any simulation as an xml file.
    --Able to load in custom configuration of grid for any simulation.
    --Able to control speed of simulation.
    --Able to pause and play simulation.
    --Able to step through simulation.
    --Able to catch nearly all the errors thrown during the running of the program so that it does not crash.
    --Able to have grid with different shapes, such as hexagon.
    --Able to choose whether to load in simulation with random configuration or with user-defined parameters or create 
    XML with states of individual cells in the grid. 
    --Added initial home screen and back button that allows user to load in default configurations or custom configurations
    and return back to home screen during running of simulation.
    
Potential functionality:
    Could create a button to toggle between torroidal and finite boundaries for any of the simulations that ran using the abstract
    cell class. This would work by taking advantage of the setPropertyValues method in the GetPropertyValues class. That method
    overwrites the config.properties file and replaces the boundary key with whatever is input. A button could be made to rename 
    the boundary input to "get8NeighborsTorroidal" or "get8NeighborsFinite," which would then allow the user to replace the method
    name and hence "toggle" between the two settings. 
    
    


### Notes/Assumptions

Assumptions or Simplifications:
The files must be named in the manner described above in the data files needed section. We also assume that no negative
numbers are given in the xml files.

Interesting data files:
We have a data file called dumb.xml that showcase some of our error-handling features.

Known Bugs:
The program will crash if the values given in the XML configuration file are negative.

Extra credit:
More features than what was minimally required. See the feature list above.


### Impressions

This project was very difficult but it all thought us a valuable lesson in working together. We were able to help each other 
on all components of the project and this will give us valuable experience doing so in the real world. The experience using git
to branch and merge different pieces of work together often was another invaluable experience.
