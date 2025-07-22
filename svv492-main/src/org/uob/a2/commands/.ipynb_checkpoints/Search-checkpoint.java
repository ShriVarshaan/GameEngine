package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;
import java.util.ArrayList;

/*
    Allows the user to search through an equipment
    If there is something in the equipment(a hidden key or something like that) it will add the key to the player's inventory

*/

public class Search extends Command{
    String item;
    public Search(String item){
        super();
        commandType = CommandType.SEARCH;
        this.item = item;
    }

    public String execute(GameState gameState){
        Player player = gameState.getPlayer();
        Map map = gameState.getMap();

        if(player.hasEquipment(item) && map.getCurrentRoom().getEquipment(player.getEquipment(item).getUseInformation().getResult()) != null && player.getEquipment(item).getUseInformation().getAction().equalsIgnoreCase("search")){
            
            Equipment equipment = map.getCurrentRoom().getEquipment(player.getEquipment(item).getUseInformation().getResult()); //Equipment that we get from searching through the object
            equipment.setHidden(false);
            player.addEquipment(equipment);
            System.out.println(equipment.getName() + " was added to your equipments");
            map.getCurrentRoom().removeEquipment(equipment);
            player.removeEquipment(player.getEquipment(item));
            player.addScore(10); //Incrementing score for correct search
            return "Searched " + item;
        }
        player.addScore(-5);
        return "Invalid search";
    }

    public String toString(){
        return commandType + "";
    }
}