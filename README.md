# GameEngine
Built a game engine using OOP concepts in Java. The program reads a text file in the YAML format and converts it into a playable game.

The YAML can be edited in a specific format and users can use it to create their own editions of the game. These new games can have any storyline as long as they follow the given structure of the file.

 player:player name
 map:map id
 room:id,name,description,hidden
 equipment:id,name,description,hidden,use action,use
 target>,use result,use description
 container:id,name,description,hidden
 item:id,name,description,hidden
 exit:id,name,description,next room,hidden

 This also has a save game feature which saves the game to a file called game1.txt which has a slightly different structure compared to the example provided.
