package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

/**
 * Represents the use command, allowing the player to use equipment on a specific target in the game.
 * 
 * <p>
 * The use command checks if the player has the specified equipment and whether it can interact with
 * the target. The target can be a feature, item, or the current room, depending on the game context.
 * </p>
 */
public class Use extends Command {
    String equipmentName;
    String target;
    public Use(String equipmentName, String target){
        super();
        this.equipmentName = equipmentName;
        this.target = target;
        commandType = CommandType.USE;
    }

    public String execute(GameState gameState){
        Player player = gameState.getPlayer();
        Map map = gameState.getMap();
        Item item = map.getCurrentRoom().getItemByName(target);
        if(player.hasEquipment(equipmentName) && target != null && item == null){
            Equipment equipment = player.getEquipment(equipmentName);
            UseInformation usage = equipment.getUseInformation();
            String targetId = usage.getTarget();
            Feature feature = map.getCurrentRoom().getFeatureByName(target); //Only the features from the current room, might be why we aren't keeping track of the player coordinates
            Exit exit = map.getCurrentRoom().getExit(usage.getResult());
            Room currentRoom = map.getCurrentRoom();
            /*
            if(feature == null){
                return "Invalid use target";
            }
            */
            if(feature != null && usage.getTarget().equalsIgnoreCase(feature.getId()) && feature.getName().equalsIgnoreCase(target) && !usage.isUsed()){ //If the feature name matches the target name and the equipment hasn't been used
                if(currentRoom.getEquipment(usage.getResult()) != null){
                    currentRoom.getEquipment(usage.getResult()).setHidden(false);
                }
                if(exit != null){
                    exit.setHidden(false);
                }
                player.removeEquipment(equipment);
                usage.setUsed(true);
                player.addScore(10); //incrementing the player's score for using correctly
                return usage.getMessage();
            }else if(exit != null && exit.getName().equalsIgnoreCase(target) && !usage.isUsed() && equipment.getUseInformation().getTarget().equalsIgnoreCase(exit.getId())){ //If the feature name matches the target name and the equipment hasn't been used
                exit.setHidden(false);
                player.removeEquipment(equipment);
                usage.setUsed(true);
                player.addScore(10);
                return usage.getMessage();
            }else if(usage.isUsed()){ //If the equipment has been used already we can't use it again
                player.addScore(-5);
                return "You have already used " + equipmentName;
            }
            /*
            else if(!feature.getName().equalsIgnoreCase(target)){ //if the target doesn't match the target from the feature in the room
                return "Invalid use target";
            }*/
            else{
                player.addScore(-5);
                return "Invalid use target";
            }
            /*
            if(usage.getTarget().equalsIgnoreCase(target)){
                player.removeEquipment(equipment);
                usage.setUsed(true);
                return usage.getMessage();
            }
            */
        }
        if(item != null && player.hasEquipment(equipmentName)){
            UseInformation usage = player.getEquipment(equipmentName).getUseInformation();
            item.setHidden(true);
            player.removeEquipment(player.getEquipment(equipmentName));
            map.getCurrentRoom().getEquipment(usage.getResult()).setHidden(false);
            return usage.getMessage();
        }
        if(player.hasEquipment(equipmentName) && target == null){
            UseInformation usage = null;
            usage = player.getEquipment(equipmentName).getUseInformation();
        
            if(usage != null && usage.getAction().equalsIgnoreCase("own")){
                if(map.getCurrentRoom().getExit(usage.getResult()) != null){
                    map.getCurrentRoom().getExit(usage.getResult()).setHidden(false);
                }
                player.removeEquipment(player.getEquipment(equipmentName));
                player.addScore(10);
                return usage.getMessage();
            }
            player.addScore(-5);
            return equipmentName + " cannot be used on its own";
        }
        player.addScore(-5);
        return "You do not have " + equipmentName;
    }

    public String toString(){
        return commandType + " " + equipmentName + " on " + target;
    }
  
}
