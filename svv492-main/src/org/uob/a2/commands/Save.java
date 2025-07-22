package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class Save extends Command{
    String fileName;

    public Save(String fileName){
        this.fileName = fileName; //This should come from game.java
        commandType = CommandType.SAVE;
    }

    public String execute(GameState gameState){
        Player player = gameState.getPlayer();
        Map map = gameState.getMap();
        ArrayList<Room> rooms = map.getRooms();
        ArrayList<Item> items = player.getInventory();
        ArrayList<Equipment> equipments = player.getEquipment();
        for(int i = 0; i < items.size(); i++){
            map.getCurrentRoom().addItem(items.get(i));
            player.removeItem(items.get(i));
        }
        for(int i = 0; i < equipments.size(); i++){
            map.getCurrentRoom().addEquipment(equipments.get(i));
            player.removeEquipment(equipments.get(i));
        }
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write("player:" + player.getName() + "\n");
            writer.write("score:" + player.getScore() + "\n");
            writer.write("map:" + "m1" + "\n");
            writer.write("currentRoom:" + map.getCurrentRoom().getId() + "\n");


            
            for(int i = 0; i < rooms.size(); i++){
                Room room = rooms.get(i); //Storing each room here so that I can get everything easily
                ArrayList<Item> roomItems = room.getItems();
                ArrayList<Equipment> roomEquipments = room.getEquipments();
                ArrayList<Exit> roomExits = room.getExits();
                ArrayList<Feature> roomContainers = room.getFeatures();
                //writing the room into the file
                writer.write("room:" + room.getId() + "," + room.getName() + "," + room.getDescription() + "," + room.getHidden() + "\n");

                //looping through the items, exits, containers and equipments to write them into the file
                for(int j = 0; j < roomItems.size(); j++){
                    Item item = roomItems.get(j);
                    writer.write("item:" + item.getId() + "," + item.getName() + "," + item.getDescription() + "," + item.getHidden() + "\n");
                }

                for(int j = 0; j < roomContainers.size(); j++){
                    Feature container = roomContainers.get(j);
                    writer.write("container:" + container.getId() + "," + container.getName() + "," + container.getDescription() + "," + container.getHidden() + "\n");
                }

                for(int j = 0; j < roomEquipments.size(); j++){
                    Equipment equipment = roomEquipments.get(j);
                    UseInformation usage = equipment.getUseInformation();
                    writer.write("equipment:" + equipment.getId() + "," + equipment.getName() + "," + equipment.getDescription() + "," + equipment.getHidden() + "," + usage.getAction() + "," + usage.getTarget() + "," + usage.getResult() + "," + usage.getMessage() + "\n");
                }

                for(int j = 0; j < roomExits.size(); j++){
                    Exit exit = roomExits.get(j);
                    writer.write("exit:" + exit.getId() + "," + exit.getName() + "," + exit.getDescription() + "," + exit.getNextRoom() + "," + exit.getHidden() + "\n");
                }
            }
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return "Game saved\nAll of your items have been dropped in your current room";
    }

    public String toString(){
        return commandType + "";
    }
}