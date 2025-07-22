package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;
import java.util.ArrayList;

/**
 * Represents the move command, allowing the player to navigate to a different room in the game world.
 * 
 * <p>
 * The move command checks if the specified direction corresponds to an available exit in the current room.
 * If a matching exit is found, the player's location is updated to the connected room.
 * </p>
 */
public class Move extends Command {
    String direction;
    public Move(String direction){
        super();
        commandType = CommandType.MOVE;
        this.direction = direction;
    }

    public String execute(GameState gameState){
        Player player = gameState.getPlayer();
        Map map = gameState.getMap();
        Room currentRoom = map.getCurrentRoom();
        ArrayList<String> allowedDirections = new ArrayList<String>();
        ArrayList<Exit> roomExits = currentRoom.getExits();

        if(direction.equalsIgnoreCase("north")){
            for(Exit exits : roomExits){
                if(exits.getName().equalsIgnoreCase("north") && !exits.getHidden()){
                    map.setCurrentRoom(exits.getNextRoom());
                    //map.addRoom(map.getCurrentRoom());
                    map.playerY--;
                    //map.getCurrentRoom().setHidden(false);
                    return "Moving towards north\n";
                }
            }
        }else if(direction.equalsIgnoreCase("south")){
            for(Exit exits : roomExits){
                if(exits.getName().equalsIgnoreCase("south") && !exits.getHidden()){
                    map.setCurrentRoom(exits.getNextRoom());
                    //map.addRoom(map.getCurrentRoom());
                    //map.getCurrentRoom().setHidden(false);
                    map.playerY++;
                    //map.buildRoomCoordinates();
                    return "Moving towards south\n";
                }
            }
        }else if(direction.equalsIgnoreCase("east")){
            for(Exit exits : roomExits){
                if(exits.getName().equalsIgnoreCase("east") && !exits.getHidden()){
                    map.setCurrentRoom(exits.getNextRoom());
                    //map.addRoom(map.getCurrentRoom());
                    //map.getCurrentRoom().setHidden(false);
                    map.playerX++;
                    return "Moving towards east\n";
                }
            }
        }else if(direction.equalsIgnoreCase("west")){
            for(Exit exits : roomExits){
                if(exits.getName().equalsIgnoreCase("west") && !exits.getHidden()){
                    map.setCurrentRoom(exits.getNextRoom());
                    //map.addRoom(map.getCurrentRoom());
                    //map.getCurrentRoom().setHidden(false);
                    map.playerX--;
                    return "Moving towards west\n";
                }
            }
        }else{
            return "Enter a valid command";
        }
        player.addScore(-1);
        return "No exit found in that direction.";
    }

    public String toString(){
        return commandType + " " + direction;
    }

  
}
