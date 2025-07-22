package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

public class Buy extends Command{
    String item;
    String cost;
    public Buy(String item, String cost){
        this.item = item;
        this.cost = cost;
        commandType = CommandType.BUY;
    }

    public String execute(GameState gameState){
        Player player = gameState.getPlayer();
        Map map = gameState.getMap();

        int score = player.getScore();

        int amount = Integer.parseInt(cost);

        if(map.getCurrentRoom().getName().equalsIgnoreCase("shop")){
            if(map.getCurrentRoom().hasEquipment(item) && map.getCurrentRoom().getEquipmentByName(item).getId().endsWith("buy")){
                Equipment equipment = map.getCurrentRoom().getEquipmentByName(item);
                String[] equipmentSplit = equipment.getId().split("-");
                if(equipmentSplit[2].equalsIgnoreCase("buy") && equipmentSplit[1].equalsIgnoreCase(cost)){
                    if(score > amount){
                        player.addEquipment(equipment);
                        player.getEquipment(item).setId(equipmentSplit[0]);
                        player.addScore(-amount); //Subtracting the score from the player after he buys the item from the shop
                        map.getCurrentRoom().removeEquipment(map.getCurrentRoom().getEquipmentByName(item));
                        return "You have successfully purchased the item";
                    }
                return "You do not have enough points to buy this item";
                }
                return "Item cannot be bought";
            }
            return "Item is not available in the shop";
        }
        return "You must be in the shop to buy items";
    }

    public String toString(){
        return commandType + "";
    }
}