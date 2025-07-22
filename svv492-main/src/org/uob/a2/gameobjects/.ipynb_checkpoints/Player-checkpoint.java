package org.uob.a2.gameobjects;

import java.util.ArrayList;

/**
 * Represents the player in the game, including their name, inventory, and equipment.
 * 
 * <p>
 * The player can carry items and equipment, interact with the game world, and perform
 * actions using their inventory or equipment.
 * </p>
 */
public class Player {
    String name;
    ArrayList<Item> inventory;
    ArrayList<Equipment> equipment;
    int score;
    public static int roomsVisited;

    public Player(){}

    public Player(String name){
        this.name = name;
        this.inventory = new ArrayList<Item>();
        this.equipment = new ArrayList<Equipment>();
        this.score = 0;
    }
    
    public String getName(){
        return name;
    }

    public ArrayList<Item> getInventory(){
        return inventory;
    }

    public boolean hasItem(String itemName){
        for(Item item : inventory){
            if(item.getName().equalsIgnoreCase(itemName)){ //Case insensitive
                return true;
            }
        }
        return false;
    }
    
    public Item getItem(String itemName){
        for(Item item : inventory){
            if(item.getName().equalsIgnoreCase(itemName)){ //Case insensitive
                return item;
            }
        }
        return null;
    }

    public void addItem(Item item){
        inventory.add(item);
    }

    public ArrayList<Equipment> getEquipment(){
        return equipment;
    }

    public boolean hasEquipment(String equipmentName){
        for(Equipment equipmentI : equipment){ //cannot use hasitem cause the list differs
            if(equipmentI.getName().equalsIgnoreCase(equipmentName)){ //Case insensitive
                return true;
            }
        }
        return false;
    }

    public Equipment getEquipment(String equipmentName){ //Overloaded method that gets the equipment by equipment name
        boolean exists = false;
        for(Equipment equipmentI : equipment){
            if(equipmentI.getName().equalsIgnoreCase(equipmentName)){ //Case insensitive
                exists = true;
                return equipmentI;
            }
        }
        return null;
    }

    public void addEquipment(Equipment equipment){
        this.equipment.add(equipment);
    }

    public void removeEquipment(Equipment equipment){ //Own method to remove equipment once it has been used
        this.equipment.remove(equipment);
    }

    public void removeItem(Item item){
        this.inventory.remove(item);
    }

    public void addScore(int num){
        this.score += num;
    }

    public int getScore(){
        return this.score + ((roomsVisited + 1) * 5) + inventory.size() + equipment.size(); //each room is a score of 5, each item/equipment with the player is a score of 1, Each time the player uses the correct equipment on a target the score is added by 10. This is done from the use.java, wear.java, search.java. Every time two items are combined to form an equipment the score will increase by 20. Each time use,combine,wear or search is not used properly the score is reduced by 5 points. Each time you move towards an invalid direction score is reduced by 1
    }
    /**
     * Returns a string representation of the player's current state, including their name,
     * inventory, and equipment descriptions.
     *
     * @return a string describing the player, their inventory, and equipment
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("Player Name: " + this.name + "\nInventory:\n");
        for (Item i : this.inventory) {
            out.append("- ").append(i.getDescription()).append("\n");
        }
        out.append("Equipment:\n");
        for (Equipment e : this.equipment) {
            out.append("- ").append(e.getDescription()).append("\n");
        }
        return out.toString();
    }
}
