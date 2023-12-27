# Project Description:

Embark on an adventurous journey with Yogi Bear in the vast wilderness of Yellowstone National Park through this interactive game. Yogi's mission is to gather all the scattered picnic baskets amidst the park's challenging terrain, filled with mountains and trees acting as obstacles. Navigating through the park, Yogi must also contend with vigilant rangers, strategically moving either horizontally or vertically, posing a threat to his basket-collecting quest. Proximity to a ranger within a predefined unit distance results in Yogi losing one of his three lives, with respawn occurring at the park entrance if lives remain.

Throughout Yogi's escapades, the game tallies the number of successfully collected picnic baskets. Upon completion of the basket collection, the game advances to a new level, either through generation or loading. In the unfortunate event of Yogi depleting all lives, a popup message prompts the player to enter their name for record-keeping in the game's database.

Enhancing the gaming experience, the project includes a menu featuring a high-score table showcasing the top 10 players. Additionally, a restart option allows players to reset the game for a fresh start.

# How I did it:
This program prioritizes user-friendliness and ease of use, employing an object-oriented implementation. While multilayer architectures (MV, MVC, etc.) are not mandatory, simplicity remains a key focus.

Graphics for the game are kept simple, with the player's character controlled using the familiar WASD keyboard buttons. Mouse event handlers can be implemented for additional game functions.

The creation of game levels can be achieved through algorithmic generation or loading from files. In the case of file loading, each level should reside in a separate file, with a minimum of 10 predefined levels. For algorithmically generated levels, careful consideration ensures playability.

A timer tracks the elapsed time from the start of each game level, contributing to a comprehensive documentation encompassing task description, analysis, program structure (UML class diagram), and an implementation chapter detailing notable algorithms. Special attention is given to the connections between events and their respective handlers. While adhering to minimal requirements, the project allows for the incorporation of new functionalities to enhance the gaming experience.

# Attachments:
- UML Diagram for the classes and packages used in this project.
- Full Documentation with test cases.
- SQL file to setup the required database for the HighScore classes.
