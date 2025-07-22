package org.uob.a2.gameobjects;

import java.util.ArrayList;

public class Equipment extends GameObject implements Usable {
    protected UseInformation useInformation;

    public Equipment(String id, String name, String description, boolean hidden, UseInformation useInformation){
        super(id, name, description, hidden);
        this.useInformation = useInformation;
    }

    public void setUseInformation(UseInformation useInformation){
        this.useInformation = useInformation;
    }

    public void setId(String id){
        this.id = id;
    }

    public UseInformation getUseInformation(){
        return useInformation;
    }

    public String use(GameObject target, GameState gameState){
        Player player = gameState.getPlayer();
        Map map = gameState.getMap();
        Room room = map.getCurrentRoom();
        UseInformation usage = this.getUseInformation();
        Feature feature = room.getFeature(target.getId());
        if(feature.equals(target)){ //If the feature name matches the target name and the equipment hasn't been used
            ArrayList<Item> itemsInRoom = room.getItems();
            for(Item item : itemsInRoom){
                item.setHidden(false); //Once the particular puzzle or challenge has been solved using an equipment the items hidden in the room are made visible
            }
            return usage.getMessage();
            }
        return "Invalid use"; //Hope that this doesn't happen
    }

    public String getName(){
        return name;
    }
   
    /**
     * Returns a string representation of this equipment, including the attributes inherited from {@code GameObject}
     * and the associated use information.
     *
     * @return a string describing the equipment
     */
    @Override
    public String toString() {
        return super.toString() + ", useInformation=" + useInformation;
    }
}
