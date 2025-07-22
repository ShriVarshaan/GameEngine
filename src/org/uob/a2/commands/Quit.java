package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;
import java.util.ArrayList;

/**
 * Represents the quit command, allowing the player to exit the game.
 * 
 * <p>
 * The quit command signals the end of the game session and provides a summary of the player's
 * current status before termination.
 * </p>
 */
public class Quit extends Command {
    public Quit(){
        super();
        commandType = CommandType.QUIT;
    }

    public String execute(GameState gameState){
        Player player = gameState.getPlayer();
        Map map = gameState.getMap();
        StringBuilder out = new StringBuilder();
        out.append("Game over:\n");
        ArrayList<Item> playerInventory = player.getInventory();
        ArrayList<Equipment> playerEquipment = player.getEquipment();
        //Printing out the items that the player had when he quits the game
        if(playerInventory.size() == 0){
            out.append("You finished with 0 items in your inventory\n");
        }else{
            out.append("The items in your inventory are: ");
            for(int i = 0; i < playerInventory.size(); i++){
                out.append(playerInventory.get(i).getName().toLowerCase() + " ");
            }
            out.append("\n");
        }
        //Printing out the equipments that the player had when he quits the game
        if(playerEquipment.size() == 0){
            out.append("You finished with 0 equipments\n");
        }else{
            for(int i = 0; i < playerEquipment.size(); i++){
                out.append(playerEquipment.get(i).getName().toLowerCase() + " ");
            }
            out.append("\n");
        }
        out.append("You finished with a score of: " + player.getScore() + "\n");
        return out.toString();
    }

    public CommandType getCommandType(){
        return this.commandType;
    }

    public String toString(){
        return commandType + ""; //Empty quotes to convert the enum to string, java concat rule
    }
}
