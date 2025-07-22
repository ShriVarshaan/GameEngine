package org.uob.a2.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;

import org.uob.a2.gameobjects.*;

/**
 * Utility class for parsing a game state from a file.
 * 
 * <p>
 * This class reads a structured text file to create a {@code GameState} object,
 * including the player, map, rooms, items, equipment, features, and exits.
 * </p>
 */
public class GameStateFileParser {
    static ArrayList<Room> fakeRooms; //Array for the cut scenes, logic for this can be implemented in gamejava
    public static GameState parse(String filename){
        boolean currentRoomSet = false;
        int score = 0;
        String currentRoomId = ""; //currentRoomSet, score and currentRoomId are for the game load feature
        ArrayList<Room> roomList = new ArrayList<Room>();
        ArrayList<String> loadedPlayerItems = new ArrayList<String>();
        ArrayList<String> loadedPlayerEquipments = new ArrayList<String>();
        fakeRooms = new ArrayList<Room>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            String playerName = "";
            int numberRooms = -1; //Counting the number of rooms added so I can make a hashmap of the exits and use it to add the equipments to the room, starts at -1 so I can use it to index from the roomList and don't have to do weird calcs here and there
            
            while((line = reader.readLine()) != null){
                String[] info = line.trim().split("[:,]"); //Regex matches either a : or a , And we are trimming to remove the indents in yaml
                String mapId;
                if(info[0].equalsIgnoreCase("player")){
                    playerName = info[1];
                }else if(info[0].equalsIgnoreCase("map")){
                    mapId = info[1]; 
                }else if(info[0].equalsIgnoreCase("room")){

                    if(info[1].startsWith("f")){ //this is for the fake room, and they are never hidden, otherwise story will not show up when the player goes there
                        fakeRooms.add(new Room(info[1], info[2], info[3], false));
                    }else if(info[4].equalsIgnoreCase("true")){ //Because the hidden parameter is a boolean and the text file reads in strings
                        roomList.add(new Room(info[1], info[2], info[3], true));
                        numberRooms++;
                    }else{
                        roomList.add(new Room(info[1], info[2], info[3], false));
                        numberRooms++;
                    }
                }else if(info[0].equalsIgnoreCase("equipment")){
                    Room temp = roomList.get(numberRooms);
                    Equipment tempEquip;
                    UseInformation tempUse;
                    if(info[4].equalsIgnoreCase("true")){
                        tempUse = new UseInformation(false, info[5], info[6], info[7], info[8]);
                        tempEquip = new Equipment(info[1], info[2], info[3], true, tempUse);
                    }else{
                        tempUse = new UseInformation(false, info[5], info[6], info[7], info[8]);
                        tempEquip = new Equipment(info[1], info[2], info[3], false, tempUse);
                    }
                    temp.addEquipment(tempEquip);
                }else if(info[0].equalsIgnoreCase("container")){
                    Room temp = roomList.get(numberRooms);
                    if(info[4].equalsIgnoreCase("true")){
                        temp.addFeature(new Container(info[1], info[2], info[3], true));
                    }else{
                        temp.addFeature(new Container(info[1], info[2], info[3], false));
                    }
                }else if(info[0].equalsIgnoreCase("item")){
                    Room temp = roomList.get(numberRooms);
                    if(info[4].equalsIgnoreCase("true")){
                        temp.addItem(new Item(info[1], info[2], info[3], true));
                    }else{
                        temp.addItem(new Item(info[1], info[2], info[3], false));
                    }
                }else if(info[0].equalsIgnoreCase("exit")){
                    Room temp = roomList.get(numberRooms);
                    if(info[5].equalsIgnoreCase("true")){
                        temp.addExit(new Exit(info[1], info[2], info[3], info[4], true));
                    }else{
                        temp.addExit(new Exit(info[1], info[2], info[3], info[4], false));
                    }
                }else if(info[0].equalsIgnoreCase("currentRoom")){
                    currentRoomSet = true;
                    currentRoomId = info[1];
                }else if(info[0].equalsIgnoreCase("score")){
                    score = Integer.parseInt(info[1]);
                }/*else if(info[0].equalsIgnoreCase("playerItems")){
                    for(int i = 1; i < info.length; i++){
                        loadedPlayerItems.add(info[i]);
                    }
                }else if(info[0].equalsIgnoreCase("playerEquipments")){
                    for(int i = 1; i < info.length; i++){
                        loadedPlayerEquipments.add(info[i]);
                    }
                }*/else{
                    Map map = new Map();
                    reader.close();
                    GameState gameState = new GameState(map, new Player(playerName));
                    return gameState;
                }
            }
            Map map = new Map();
            Player player = new Player(playerName);
            for(Room element : roomList){
                map.addRoom(element); //Adding every room to the map's rooms list
            }
            
            if(currentRoomSet){ //If there was a currentRoom title in yaml, it sets the current room to that particular room and also the score from one of the later titles to the player
                map.setCurrentRoom(currentRoomId);
                player.addScore(score);
                
            }else{
                map.setCurrentRoom(roomList.get(0).getId()); //If it couldn't find a currentRoom title in yaml, it takes the first room as the current room
            }
            map.buildRoomCoordinates();
            if(currentRoomSet){
                HashMap<String, int[]> roomCoordinates = map.getRoomCoordinates(); //Setting initial playerX and playerY
                map.playerX = roomCoordinates.get(currentRoomId)[0];
                map.playerY = roomCoordinates.get(currentRoomId)[1];
            }
            reader.close();
            GameState gameState = new GameState(map, player);
            return gameState;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static ArrayList<Room> getFakeRooms(){
        return fakeRooms; //Returns the list of fakerooms to game.java
    }
}
