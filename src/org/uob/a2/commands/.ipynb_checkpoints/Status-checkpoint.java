package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;
import java.util.ArrayList; //own import
/**
 * Represents the status command, allowing the player to retrieve information
 * about their inventory, specific items, or their overall status.
 * 
 * <p>
 * The status command can display a list of items in the player's inventory, 
 * provide details about a specific item, or show the player's general status.
 * </p>
 */
public class Status extends Command {
    String topic;
    public Status(String topic){
        super();
        this.topic = topic;
        commandType = CommandType.STATUS;
    }

    public String execute(GameState gameState){
        Player player = gameState.getPlayer();
        Map map = gameState.getMap();
        StringBuilder out = new StringBuilder();
        ArrayList<Item> items = player.getInventory();
        ArrayList<Equipment> equipments = player.getEquipment();
        if(topic.equalsIgnoreCase("inventory")){
            out.append("items: ");
            for(Item item : items){
                out.append(item.getName() + " ");
            }
            out.append("\n");
            for(Equipment equipment : equipments){
                out.append("equipments:");
                out.append(equipment.getName() + " ");
            }
            return out.toString();
        }else if(items.contains(player.getItem(topic))){
            out.append(player.getItem(topic).getDescription());
            return out.toString();
        }else if(equipments.contains(player.getEquipment(topic))){
            out.append(player.getEquipment(topic).getDescription());
            return out.toString();
        }else if(topic.equalsIgnoreCase("player")){
            out.append(player.toString());
            return out.toString();
        }else if(topic.equalsIgnoreCase("map")){
            return map.displayMap();
        }else if(topic.equalsIgnoreCase("score")){
            out.append(player.getScore() + " ");
            return out.toString();
        }
        return ""; //Invalid test if I give a message here
    }

    public String toString(){
        return commandType + " " + topic;
    }
}
