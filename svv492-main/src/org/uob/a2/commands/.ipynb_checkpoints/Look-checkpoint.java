package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;
import java.util.ArrayList;

/**
 * Represents the look command, allowing the player to examine various elements of the game world.
 * 
 * <p>
 * The look command can provide details about the current room, its exits, features, or specific items and equipment.
 * Hidden objects are not included in the output unless explicitly revealed.
 * </p>
 */
public class Look extends Command {
    String target;
    public Look(String target){
        this.target = target;
        commandType = CommandType.LOOK;
    }

    public String execute(GameState gameState){
        Player player = gameState.getPlayer();
        Map map = gameState.getMap();
        Room room = map.getCurrentRoom();
        StringBuilder out = new StringBuilder();
        ArrayList<Item> roomItems = room.getItems();
        ArrayList<Equipment> roomEquipments = room.getEquipments();
        ArrayList<Feature> roomFeatures = room.getFeatures();
        ArrayList<Exit> roomExits = room.getExits();
        if(target.equalsIgnoreCase("room")){
            out.append(room.getDescription() + "\n");
            for(int i = 0; i < roomItems.size(); i++){
                if(!roomItems.get(i).getHidden()){ //If the item in the room is not hidden
                    out.append(roomItems.get(i).getDescription() + " ");
                }
            }
            if(roomItems.size() != 0){
                out.append("\n");
            }
            for(int i = 0; i < roomEquipments.size(); i++){
                if(!roomEquipments.get(i).getHidden()){ //If the equipments in the room are not hidden
                    out.append(roomEquipments.get(i).getDescription() + " ");
                }
            }
            if(roomEquipments.size() != 0){
                out.append("\n");
            }
            for(int i = 0; i < roomFeatures.size(); i++){
                if(!roomFeatures.get(i).getHidden()){ //If the features are not hidden in the room (chests, consoles and so on)
                    out.append(roomFeatures.get(i).getDescription() + " ");
                }
            }
            if(roomFeatures.size() != 0){
                out.append("\n");
            }
            return out.toString();
        }else if(target.equalsIgnoreCase("exits")){
            out.append("The available exits are: ");
            for(int i = 0; i < roomExits.size(); i++){
                if(!roomExits.get(i).getHidden()){
                    out.append(roomExits.get(i).getDescription() + " ");
                }
            }
            out.append("\n");
            return out.toString();
        }else if(target.equalsIgnoreCase("features")){
            out.append("You also see: ");
            for(int i = 0; i < roomFeatures.size(); i++){
                if(!roomFeatures.get(i).getHidden()){
                    out.append(roomFeatures.get(i).getDescription() + " ");
                }
            }
            out.append("\n");
            return out.toString();
        }else{
            for(Item element : roomItems){
                if(element.getName().equalsIgnoreCase(target) && !element.getHidden()){
                    return element.getDescription();
                }
            }
            for(Equipment element : roomEquipments){
                if(element.getName().equalsIgnoreCase(target) && !element.getHidden()){
                    return element.getDescription();
                }
            }
            for(Feature element : roomFeatures){
                if(element.getName().equalsIgnoreCase(target) && !element.getHidden()){
                    return element.getDescription();
                }
            }
            for(Exit element : roomExits){
                if(element.getName().equalsIgnoreCase(target) && !element.getHidden()){
                    return element.getDescription();
                }
            }
            return "";
        }
    }

    public String toString(){
        return commandType + " " + target;
    }
}
