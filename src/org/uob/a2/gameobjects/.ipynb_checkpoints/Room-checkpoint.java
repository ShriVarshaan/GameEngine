package org.uob.a2.gameobjects;

import java.util.ArrayList;

/**
 * Represents a room in the game, which is a type of {@code GameObject}.
 * 
 * <p>
 * Rooms can have items, equipment, features, and exits. They also manage navigation
 * and interactions within the game world.
 * </p>
 */
public class Room extends GameObject {

    private ArrayList<Exit> exits; //Not sure if this should be here, not given in the instructions

    private ArrayList<Item> items;
    private ArrayList<Feature> features; //This is just the targets (containers)
    private ArrayList<Equipment> equipment;

    public Room(){
        super();
    }
    public Room(String id, String name, String description, boolean hidden){
        super(id, name, description, hidden);
        exits = new ArrayList<Exit>();
        items = new ArrayList<Item>();
        features = new ArrayList<Feature>();
        equipment = new ArrayList<Equipment>();
    }

    @Override
    public void setName(String name){
        this.name = name;
    }

    @Override
    public void setDescription(String description){
        this.description = description;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public String getDescription(){
        return description;
    }

    public ArrayList<Exit> getExits(){
        return exits;
    }

    public void addExit(Exit exit){
        exits.add(exit);
    }

    public ArrayList<Item> getItems(){
        return items;
    }

    public Item getItem(String id){
        for(Item item : items){
            if(item.getId().equalsIgnoreCase(id)){
                return item;
            }
        }
        return null;
    }

    public Item getItemByName(String name){
        for(Item item : items){
            if(item.getName().equalsIgnoreCase(name)){
                return item;
            }
        }
        return null;
    }

    public Feature getFeatureByName(String name){
        for(Feature feature : features){
            if(feature.getName().equalsIgnoreCase(name)){
                return feature;
            }
        }
        return null;
    }

    public ArrayList<Equipment> getEquipments(){
        return equipment;
    }

    public Equipment getEquipmentByName(String name){
        for(Equipment element : equipment){
            if(element.getName().equalsIgnoreCase(name)){
                return element;
            }
        }
        return null;
    }

    public Equipment getEquipment(String id){
        for(Equipment element : this.equipment){
            if(element.getId().equalsIgnoreCase(id)){
                return element;
            }
        }
        return null;
    }

    public void removeItem(Item item){
        this.items.remove(item);
    }

    public void removeEquipment(Equipment equipment){
        this.equipment.remove(equipment);
    }

    public Exit getExit(String id){
        for(Exit exit : exits){
            if(exit.getId().equalsIgnoreCase(id)){
                return exit;
            }
        }
        return null;
    }

    public Exit getExitByName(String name){
        for(Exit element : exits){
            if(element.getName().equalsIgnoreCase(name)){
                return element;
            }
        }
        return null;
    }

    public void addEquipment(Equipment equipment){
        this.equipment.add(equipment);
    }

    public Feature getFeature(String id){
        for(Feature feature : features){
            if(feature.getId().equalsIgnoreCase(id)){
                return feature;
            }
        }
        return null;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public ArrayList<Feature> getFeatures(){
        return features;
    }

    public ArrayList<GameObject> getAll(){
        ArrayList<GameObject> everything = new ArrayList<GameObject>();
        everything.addAll(exits);
        everything.addAll(items);
        everything.addAll(features);
        everything.addAll(equipment);
        return everything;
    }

    public void addFeature(Feature feature){
        this.features.add(feature);
    }

    public void setId(String id){
        this.id = id;
    }

    public boolean hasItem(String itemName){
        for(Item item : items){
            if(item.getName().equalsIgnoreCase(itemName)){
                return true;
            }
        }
        return false;
    }

    public boolean hasEquipment(String name){
        for(Equipment element : equipment){
            if(element.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns a string representation of the room, including its contents and description.
     *
     * @return a string describing the room
     */
    @Override
    public String toString() {
        String out = "[" + id + "] Room: " + name + "\nDescription: " + description + "\nIn the room there is: ";
        for (Item i : this.items) {
            out += i + "\n";
        }
        for (Equipment e : this.equipment) {
            out += e + "\n";
        }
        for (Feature f : this.features) {
            out += f + "\n";
        }
        for (Exit e : this.exits) {
            out += e + "\n";
        }
        return out + "\n";
    }
}
