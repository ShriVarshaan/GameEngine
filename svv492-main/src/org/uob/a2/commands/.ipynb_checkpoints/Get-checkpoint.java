package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

/**
 * Represents the get command, allowing the player to pick up an item from the current room and add it to their inventory.
 * 
 * <p>
 * This command checks if the specified item is present in the current room. If the item exists and the player
 * does not already have it, the item is added to the player's inventory and removed from the room. Otherwise,
 * an appropriate message is returned.
 * </p>
 */
public class Get extends Command {
    String item;

    public Get(String item){
        super();
        this.item = item;
        commandType = CommandType.GET;
    }

    public String execute(GameState gameState){
        Player player = gameState.getPlayer();
        Map map = gameState.getMap();
        Room room = map.getCurrentRoom();
        if(room.hasItem(item) && !player.hasItem(item)){
            if(!room.getItemByName(item).getHidden() && !room.getItemByName(item).getId().endsWith("buy")){
                player.addItem(room.getItemByName(item));
                room.removeItem(room.getItemByName(item));
                return "You pick up: " + item;
            }else{
                return "No nonexistent to get.";
            }
        }else if(room.hasEquipment(item) && !player.hasEquipment(item)){
            if(!room.getEquipmentByName(item).getHidden() && !room.getEquipmentByName(item).getId().endsWith("buy")){
                player.addEquipment(room.getEquipmentByName(item));
                room.removeEquipment(room.getEquipmentByName(item));
                return "You pick up: " + item;
            }else{
                return "No nonexistent to get.";
            }
        }else if(player.hasItem(item)){
            return "You already have " + item;
        }else if(player.hasEquipment(item)){
            return "You already have " + item;
        }
        return "No nonexistent to get.";
    }

    public String toString(){
        return commandType + " " + item;
    }
}
