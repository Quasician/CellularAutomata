#### Classes
We would need a runner class that would run the gameloop.
    It also checks who wins each round and updates the players' scores.
        To ensure the players participate in rounds, in a while loop each player will be asked to pick their option until one wins.

There will be a player class that has a data element called score.
    There will have to be an abstract weapon class where all weapon option classes would extend from.
    The abstract weapon class would have losing and winning data structures to contain the subclasses that would lose or win against the current subclass.
    The weapon class would have generalized methods to return the winning and losing subclasses regardless of the data structure we are using to contain all winning and losing subclasses.
    Player would need a weapon data element called option.

When the creator adds a new weapon option, we will have a methods that loops through all losing and winning data structures and update all subclasses.
This class hierachy should also work for new game files.

#### Alternative Implementations
For example if you were using an arraylists, it would be very easy to create and read all the object relationships, but it would be a pain to loop through all of the values.
You could use a hashmap, but the efficiency compared to an arraylist when the dataset is this small would be negligible.
With a hashmap you also have the added complexity of hashing each object and returning it, while in an arraylist implementation, it would be easy to do both.
We could use an array but it wouldn't generalize well to adding new options, since you would have to create a new array every time. 
