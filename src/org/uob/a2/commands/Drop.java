package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

/**
 * Represents the drop command, allowing the player to drop an item from their inventory into the current room.
 * 
 * <p>
 * This command checks if the player possesses the specified item and, if so, removes it from their inventory
 * and adds it to the current room. If the player does not have the item, an error message is returned.
 * </p>
 */
public class Drop extends Command {
    String item;
    public Drop(String item){
        super();
        commandType = CommandType.DROP; 
        this.item = item;
    }

    public String execute(GameState gameState){
        Player player = gameState.getPlayer();
        Room room = gameState.getMap().getCurrentRoom();
        if(player.hasItem(item)){ //If the item or equipment is in the player's inventory then we can remove it
            room.addItem(player.getItem(item));
            player.removeItem(player.getItem(item));//REmoving the item from the array list
            return "You drop: " + item;
        }else if(player.hasEquipment(item)){
            room.addEquipment(player.getEquipment(item));
            player.removeEquipment(player.getEquipment(item));
            return "You drop: " + item;
        }else{
            return "You cannot drop nonexistent";
        }
    }

    @Override
    public String toString(){
        return commandType + " " + item;
    }
   
}
