package org.uob.a2;

import java.util.Scanner;
import java.util.ArrayList;

import org.uob.a2.commands.*;
import org.uob.a2.gameobjects.*;
import org.uob.a2.parser.*;
import org.uob.a2.utils.*;

/**
 * Main class for the game application. Handles game setup, input parsing, and game execution.
 * 
 * <p>
 * This class initializes the game state, reads user input, processes commands, and maintains the game loop.
 * </p>
 */
public class Game {
    static GameState mainGame;
    static Tokeniser tokeniser;
    static Parser parser;
    static Scanner sc;
    public static void main(String[] args){
        setup(); //Initializes the game state

        
        start(); //main game loop
    }

    public static void start(){
        boolean load = false; //for loading into saved state
        ArrayList<Room> fakeRooms = GameStateFileParser.getFakeRooms();
        int[] count = new int[fakeRooms.size()];

        for(int i = 0; i < count.length; i++){
            count[i] = 0;
        }

        String input;
        while(true){
            tokeniser = new Tokeniser();
            parser = new Parser();
            for(int i = 0; i < fakeRooms.size(); i++){
                char[] roomCheck = mainGame.getMap().getCurrentRoom().getId().toCharArray();
                char[] fakeRoomCheck = fakeRooms.get(i).getId().toCharArray();
                if(fakeRoomCheck.length == 2 && roomCheck.length == 2 && roomCheck[1] == fakeRoomCheck[1] && count[i] == 0){ //If the fakeroom ids and the room id matches while their length is two
                    count[i] = 1;
                    System.out.println(fakeRooms.get(i).getDescription());
                    //fakeRooms.get(i).setId("used");
                }else if(fakeRoomCheck.length == 3 && roomCheck.length == 3 && roomCheck[1] == fakeRoomCheck[1] && roomCheck[2] == fakeRoomCheck[2] && count[i] == 0){ //fakeroom id matches the real room id and their lengths are 3
                    count[i] = 1;
                    System.out.println(fakeRooms.get(i).getDescription());
                    //fakeRooms.get(i).setId("used");
                }
            }
            System.out.print("> ");
            input = sc.nextLine();

            tokeniser.tokenise(input);

            ArrayList<Token> tokenList = tokeniser.getTokens();

            try{
                Command currentCommand = parser.parse(tokenList);
                
                turn(currentCommand);
                
                if(currentCommand.commandType == CommandType.QUIT){
                    break;
                }else if(mainGame.getMap().getCurrentRoom().getName().equalsIgnoreCase("final")){ //If the room name is final, which is the last room, the game loop is broken
                    Command quitting = new Quit();
                    turn(quitting);
                    break;
                }else if(currentCommand.commandType == CommandType.SAVE){ //If the user is trying to save we execute quit as well to show the current stats
                    Command quitting = new Quit();
                    turn(quitting);
                    break;
                }else if(currentCommand.commandType == CommandType.LOAD){
                    load = true; //If the command is load we break this loop and go to the next one, have a load condition to know whether use has quitted or is loading a saved game
                    break;
                }
            }catch(CommandErrorException e){
                e.printStackTrace();
            }
        } 
        int num = 0;
        while(load){ //If the user's input was load it just changes mainGame to game1.txt and comes into this loop instead of the other one
            if(num == 0){
                mainGame = GameStateFileParser.parse("data/game1.txt");
                num = 1;
            }
            Tokeniser tokeniser = new Tokeniser();
            Parser parser = new Parser();
            for(int i = 0; i < fakeRooms.size(); i++){
                char[] roomCheck = mainGame.getMap().getCurrentRoom().getId().toCharArray();
                char[] fakeRoomCheck = fakeRooms.get(i).getId().toCharArray();
                if(fakeRoomCheck.length == 2 && roomCheck.length == 2 && roomCheck[1] == fakeRoomCheck[1] && count[i] == 0){
                    count[i] = 1;
                    System.out.println(fakeRooms.get(i).getDescription());
                }else if(fakeRoomCheck.length == 3 && roomCheck.length == 3 && roomCheck[1] == fakeRoomCheck[1] && roomCheck[2] == fakeRoomCheck[2] && count[i] == 0){
                    count[i] = 1;
                    System.out.println(fakeRooms.get(i).getDescription());
                }
            }
            System.out.print("> ");
            input = sc.nextLine();

            tokeniser.tokenise(input);

            ArrayList<Token> tokenList = tokeniser.getTokens();

            try{
                Command currentCommand = parser.parse(tokenList);

                if(currentCommand.commandType != CommandType.LOAD){
                    turn(currentCommand);;
                }
                //turn(currentCommand);
                
                if(currentCommand.commandType == CommandType.QUIT){
                    break;
                }else if(mainGame.getMap().getCurrentRoom().getName().equalsIgnoreCase("final")){
                    Command quitting = new Quit();
                    turn(quitting);
                    break;
                }else if(currentCommand.commandType == CommandType.SAVE){
                    Command quitting = new Quit();
                    turn(quitting);
                    break;
                }else if(currentCommand.commandType == CommandType.LOAD){
                    System.out.println("Already in loaded game");
                }
            }catch(CommandErrorException e){
                e.printStackTrace();
            }
        }
    }

    public static void setup(){
        mainGame = GameStateFileParser.parse("data/game.txt");
        sc = new Scanner(System.in);
        parser = new Parser();
        tokeniser = new Tokeniser();
    }

    public static void turn(Command command){
        System.out.println(command.execute(mainGame));
    }
}
