package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;
import java.util.HashMap;

/**
 * Represents the help command, providing the player with instructions or information
 * about various topics related to the game.
 * 
 * <p>
 * The help command displays information on how to play the game, including details about 
 * available commands, their syntax, and their purpose.
 * </p>
 */
public class Help extends Command {
    String topic;
    HashMap<String, String> topicHelp;

    public Help(){
        commandType = CommandType.HELP;
        this.topicHelp = new HashMap<String, String>();
        topicHelp.put("move", "MOVE Command: Use the 'move' command and the direction in which you want to move");
        topicHelp.put("drop", "DROP Command: Use the 'drop' command and the item/equipment that you want to drop from your inventory");
        topicHelp.put("get", "GET Command: Use the 'get' command and the item/equipment name to pick it up and add it to your equipment list or item inventory");
        topicHelp.put("look", "LOOK Command: Use the 'look' command and an atribute that you want to look at");
        topicHelp.put("quit", "QUIT Command: Use the 'quit' command to exit the game");
        topicHelp.put("status", "STATUS Command: Use the 'status' command and an input to check the status of a particular thing");
        topicHelp.put("use", "USE Command: Use the 'use' command with the equipment name and the target");
        topicHelp.put("help", "HELP Command: Use the 'hwlp' command and the topic to see specific help or just help to see general help");
    }
    
    public Help(String topic){
        super();
        commandType = CommandType.HELP;
        this.topic = topic;
        this.topicHelp = new HashMap<String, String>();
        topicHelp.put("move", "MOVE Command: Use the 'move' command and the direction in which you want to move");
        topicHelp.put("drop", "DROP Command: Use the 'drop' command and the item/equipment that you want to drop from your inventory");
        topicHelp.put("get", "GET Command: Use the 'get' command and the item/equipment name to pick it up and add it to your equipment list or item inventory");
        topicHelp.put("look", "LOOK Command: Use the 'look' command and an atribute that you want to look at");
        topicHelp.put("quit", "QUIT Command: Use the 'quit' command to exit the game");
        topicHelp.put("status", "STATUS Command: Use the 'status' command and an input to check the status of a particular thing");
        topicHelp.put("use", "USE Command: Use the 'use' command with the equipment name and the target");
        topicHelp.put("load", "LOAD Command: Loads a saved state if it exists");
        topicHelp.put("save", "SAVE Command: saves the game at the current state");
        topicHelp.put("search", "SEARCH Command: searches through an equipment");
        topicHelp.put("help", "HELP Command: Use the 'help' command and the topic to see specific help or just help to see general help");
    }

    //help changes based on where you are I think
    public String execute(GameState gameState){ 
        if(topic == null){
            String help = """
            Welcome to the game!
            Command to move - MOVE
            Command to drop an item or an equipment - DROP
            Command to pick up an item or equipment in the room - GET
            Command to examine elements of the game - LOOK
            Command to exit the game - QUIT
            Command to view the status - STATUS
            Command to use an equipment - USE
            Command to search through an equipment - SEARCH
            Command to save the game state - SAVE
            Command to load a saved game state - LOAD
            Command to help - HELP""";
            return help;
        }else if(topicHelp.containsKey(topic.toLowerCase())){
            return topicHelp.get(topic.toLowerCase());
        }
        return "No help available for the topic: " + topic;
    }

    public String toString(){
        return commandType + " " + topic;
    }
}
