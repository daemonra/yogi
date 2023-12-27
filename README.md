# Game Description:

Yogi Bear wants to collect all the picnic baskets in the forest of Yellowstone National Park. This park contains mountains and trees, that are obstacles for Yogi. 
Besides the obstacles, there are rangers, who make it harder for Yogi to collect the baskets. Rangers can move only horizontally or vertically in the park. 
If a ranger gets too close (one unit distance) to Yogi, then Yogi loses one life. (It is up to you to define the unit, but it should be at least that wide, as the sprite of Yogi).
If Yogi still has at least one life from the original three, then he spawns at the park entrance.
During the adventures of Yogi, the game counts the number of picnic baskets, that Yogi collected.
If all the baskets are collected, load a new game level, or generate one. 
If Yogi loses all his lives, show a popup message box, where the player can type his name and save it to the database.
Create a menu item, which displays a high score table of the players for the 10 best scores. Also, create a menu item that restarts the game.

# Common requirements:

 Your program should be user friendly and easy to use. You have to make an object
oriented implementation, but it is not necessary to use multilayer architectures (MV,
MVC etc.).
 You have to use simple graphics for the game display. The "sprite" of the player’s
character should be able to moved with the well known WASD keyboard buttons. You
can also implement mouse event handlers to other functions of the game.
 You can generate the game levels with an algorithm, or simply load them from files. If
you load the levels from file, then each one should be put into different files, and you
have to create at least 10 predefined levels. If you generate the levels, then take care that
the levels should be playable (player should be able to solve it).
 Each game needs to have a timer, which counts the elapsed time since the start of the
game level.
 The documentation should contain the description of the task, its analysis, the structure
of the program (UML class diagram), a chapter for the implementation, which describes
the most interesting algorithms (e.g. that generates the level) etc. of the program. Also,
don’t forget to show the connections between the events and their handlers.
 The task description should contain the minimal requirements. It is free to add new
functionalities to the games.
