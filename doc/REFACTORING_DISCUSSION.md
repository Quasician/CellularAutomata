Refactoring Lab:

After looking through the issues provided by the static analysis tool,
we have decided to focus on a few issues in order to ensure that our 
code follows the project specific design goals.
Firstly, we want to reduce our longest method (which is in the Main class)
as it contains duplicated code and could be shortened.
Also, we want to remove any imports of JavaFx in the model classes as
we want to ensure that all forms of Visualization occurs in those 
respective classes and not in the Model classes.
We also want to remove any public instance variables from our all of our code.
Finally, we would like to ensure that other classes do not have complete
access to our 2D array as this breaks the encapsulation, and Visualizer
should not have access to the entire grid created by Model.