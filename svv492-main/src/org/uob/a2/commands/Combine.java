package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

import java.util.ArrayList;

/*
Represent the combine command, allowing the player to combine two items to form an equipment


Checks if the player possesses the two items required in their inventory and, if so removes it from their inventory and combines them to form an equipment. 
The items are removed from the player's inventory and they are set to hidden.

The items whose id starts with a c can be used for combination

item 1 has id c1
item 2 has id c2
equipment with the id c1 c2 is formed by combining c1 and c2
*/

public class Combine extends Command{
    String item1;
    String item2;

    public Combine(String item1, String item2){
        super();
        this.item1 = item1;
        this.item2 = item2;
        commandType = CommandType.COMBINE;
    }

    public String execute(GameState gameState){
        Player player = gameState.getPlayer();
        Map map = gameState.getMap();
        //ArrayList<item> inventory = player.getInventory();
        
        if(player.hasItem(item1) && player.hasItem(item2)){
            String id1 = player.getItem(item1).getId();
            String id2 = player.getItem(item2).getId(); //Gets the id for each equipment

            if(id1.toCharArray()[0] == 'c' && id2.toCharArray()[0] == 'c'){//If the id of the equipment starts with a c they can be combined
                char num1 = id1.toCharArray()[1];
                char num2 = id2.toCharArray()[1]; //gets the number in their id to compare the number with the equipment to be gained

                Room currentRoom = map.getCurrentRoom();
                ArrayList<Equipment> equipments = currentRoom.getEquipments();

                for(Equipment equipment : equipments){
                    if(equipment.getId().toCharArray()[1] == num1 && equipment.getId().toCharArray()[2] == num2){ //If the equipment numbers matches with the item numbers
                        /*We are changing the item from hidden to not hidden
                        adding the item to the player's inventory
                        removing the item from the room and removing the items from the players inventory as well
                        */
                        equipment.setHidden(false);
                        player.addEquipment(equipment);
                        currentRoom.removeEquipment(equipment);
                        player.removeItem(player.getItem(item1));
                        player.removeItem(player.getItem(item2));
                        player.addScore(20); //Score increment
                        return equipment.getName() + " was made and added to your inventory";
                    }
                }
                player.addScore(-5);
                return "These items do not make this equipment"; //If the items can be combined but not to get this equipment
            }
            player.addScore(-5);
            return "These items cannot be combined"; //Items cannot be combined
        }
        player.addScore(-5);
        return "Item not in inventory"; //Items are not in the inventory
    }

    @Override
    public String toString(){
        return commandType + " " + item1 + " " + item2;
    }
}