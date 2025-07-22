package org.uob.a2.gameobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.nio.file.*;
import java.util.Set;

import org.uob.a2.utils.*;

/**
 * Represents the game map, which consists of a collection of rooms and the current room the player is in.
 * 
 * <p>
 * The map allows for navigation between rooms, adding new rooms, and managing the current room context.
 * </p>
 */
public class Map {
    StringBuilder map; //Insert the new discoveries to the top of the already discovered places
    final String EMPTY = ".";
    //ArrayList<Room> allRoomsList;
    ArrayList<Room> rooms; //Storing the list of all the rooms
    HashMap<String, int[]> roomCoordinates;
    //HashMap<Room, Boolean> roomDiscovered;
    HashMap<String, Room> roomMapping;
    Room currentRoom;
    int xFurthest;
    int yFurthest;
    public int playerX;
    public int playerY;
    int count;
    ArrayList<String> coordAddedRooms;

    public Map(){
        rooms = new ArrayList<Room>(); //List of all the rooms in the game
        map = new StringBuilder();
        //allRoomsList = new ArrayList<Room>();
        roomMapping = new HashMap<String, Room>();
        //roomDiscovered = new HashMap<Room, Boolean>();
        roomCoordinates = new HashMap<String, int[]>();
        xFurthest = 1;
        yFurthest = 1;
        playerX = 1;
        playerY = 1;
        count = 1;
        coordAddedRooms = new ArrayList<String>();
        coordAddedRooms.add("r1");
        int[] firstRoomCoord = {1, 1};
        roomCoordinates.put("r1", firstRoomCoord);
    }

    
    public Room getCurrentRoom(){
        return currentRoom;
    }

    public void addRoom(Room room){
        rooms.add(room);
        roomMapping.put(room.getId(), room);
    }

    public void setCurrentRoom(String roomId){
        
        for(Room room : rooms){
            if(room.getId().equalsIgnoreCase(roomId)){
                currentRoom = room;
                if(currentRoom.getHidden()){ //If the room was hidden previous to the player discovering it we set the hidden to false and increase the number of rooms the player has visited by 1
                    currentRoom.setHidden(false); 
                    Player.roomsVisited += 1;
                }
            }
        }
    }

    

    
    public String displayMap(){

        //Puts blank ... in all entries in the map
        String[][] map = new String[yFurthest][xFurthest];
        for(int y = 0; y < yFurthest; y++){
            for(int x = 0; x < xFurthest; x++){
                map[y][x] = "...";
            }
        }

        //This is building the map
        for(int y = 1; y < yFurthest + 1; y++){ //Using (1, 1) as our initial coordinates
            for(int x = 1; x < xFurthest + 1; x++){
                if(x == playerX && y == playerY){ //If current x and y are the same as the player's coordinates
                    map[y - 1][x - 1] = "You";
                }else{
                    boolean roomAdded = false;
                    for(Room room : rooms){ //For each room we will fetch the coordinates and see what to add to the list so that it can be displayed
                        if(roomCoordinates.get(room.getId()) != null && x == roomCoordinates.get(room.getId())[0] && y == roomCoordinates.get(room.getId())[1]){
                            char c;
                            c = room.getId().toCharArray()[0];
                            if(Character.isDigit(c)){ //All of the empty rooms(pathways) will have an id starting with a digit
                                map[y - 1][x - 1] = "...";
                                roomAdded = true;
                            }else if(!room.getHidden()){ //If the room is not hidden show it on the map otherwise don't
                                char[] name = room.getName().toCharArray();
                                StringBuilder out = new StringBuilder();
                                if(name.length < 3){
                                    for(int i = 0; i < name.length; i++){
                                        out.append(name[i]);
                                    }
                                    out.append(" ");
                                }else{
                                    for(int i = 0; i < 3; i++){
                                        out.append(name[i]);
                                    }
                                }
                                map[y - 1][x - 1] = out.toString();
                                roomAdded = true; //the room will be added if it has come here
                            }
                        }
                    }
                    if(!roomAdded){
                        map[y - 1][x - 1] = "..."; //If a room wasn't added just put out ..., should never get here but this is a fail safe
                    }
                }
            }
        }

        //displaying the actual map

        String displayMap = "";
        for(int y = 0; y < yFurthest; y++){
            for(int x = 0; x < xFurthest; x++){
                displayMap += map[y][x] + " ";
            }
            displayMap += "\n";
        }
        return displayMap;
    }
    

        

    public void buildRoomCoordinates(){
        //Code to see how far east and north the game extends, so I can put in empty spaces and make a box

        

        if(count == 1){ //Fpr the first room alone we are starting it off with (1, 1)
            int[] coordR1 = {1, 1};
            roomCoordinates.put(currentRoom.getId(), coordR1);
            count = 0;
        }
        
        int tempx;
        int tempy;
        
        
        for(Room room : rooms){ //this is for getting the list of exits for each room
            ArrayList<Exit> exit = room.getExits(); //Use nextRoom to see if the previous room and the next room  are the same if they are then that point is the exit
            
            ArrayList<Room> goToRoom; //The list of the rooms that the current room can go to

            for(Exit leave : exit){ //For each exit in the room we are seeing if the next room has already been added to the room coordinates hashmap. If it hasn't been added then we will
                if(!coordAddedRooms.contains(leave.getNextRoom())){
                    coordAddedRooms.add(leave.getNextRoom());

                    
                    if(leave.getName().equalsIgnoreCase("south")){
                        this.yFurthest++;
                        int[] coords = {xFurthest, yFurthest};
                        roomCoordinates.put(leave.getNextRoom(), coords);
                    }else if(leave.getName().equalsIgnoreCase("east")){
                        this.xFurthest++;
                        int[] coords = {xFurthest, yFurthest};
                        roomCoordinates.put(leave.getNextRoom(), coords);
                    }
                }
            }
            Set<String> coords = roomCoordinates.keySet();
            ArrayList<String> coordinates = new ArrayList<String>(coords);
        }
    }

    public ArrayList<Room> getRooms(){
        return this.rooms;
    }

    public HashMap<String, int[]> getRoomCoordinates(){
        return this.roomCoordinates;
    }
    
    
    /**
     * Returns a string representation of the map, including all rooms.
     *
     * @return a string describing the map and its rooms
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Map:\n");
        for (Room r : this.rooms) {
            out.append(r.toString()).append("\n");
        }
        return out.toString();
    }
}

