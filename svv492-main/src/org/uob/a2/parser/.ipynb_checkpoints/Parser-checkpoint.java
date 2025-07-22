package org.uob.a2.parser;

import java.util.ArrayList;

import org.uob.a2.commands.*;

/**
 * The {@code Parser} class processes a list of tokens and converts them into {@code Command} objects
 * that can be executed by the game.
 * 
 * <p>
 * The parser identifies the type of command from the tokens and creates the appropriate command object.
 * If the command is invalid or incomplete, a {@code CommandErrorException} is thrown.
 * </p>
 */
public class Parser {
    ArrayList<Token> tokens;

    public Command parse(ArrayList<Token> tokens) throws CommandErrorException{
        this.tokens = tokens;
        
        if(tokens.size() == 1){
            throw new CommandErrorException("No tokens provided.");
        }

        if(tokens.get(tokens.size() - 1).getTokenType() != TokenType.EOL){
            tokens.add(new Token(TokenType.EOL));
        }
        Token command = tokens.get(0);
        switch(command.getTokenType()){ //Checking what type of token it is Method from token class and throwing the exception wherever required
            case MOVE:
                if(tokens.size() != 3 || tokens.get(1).getTokenType() != TokenType.VAR){ //If the command is not exactly 2 words or the second word is not a var, north south east or west this will throw and exception
                    throw new CommandErrorException("Invalid MOVE command.");
                }
                return new Move(tokens.get(1).getValue()); //If the conditions above are not true it passes the value of the second token in this case either north south east or west to the move command and that can take care of the rest
            case GET:
                if(tokens.size() != 3 || tokens.get(1).getTokenType() != TokenType.VAR){
                    throw new CommandErrorException("Invalid GET command");
                }
                return new Get(tokens.get(1).getValue());
            case USE:
                if(tokens.size() == 3 && tokens.get(1).getTokenType() == TokenType.VAR){
                    return new Use(tokens.get(1).getValue(), null);
                }
                if(tokens.size() != 5 || tokens.get(1).getTokenType() != TokenType.VAR || tokens.get(2).getTokenType() != TokenType.PREPOSITION || tokens.get(3).getTokenType() != TokenType.VAR || !tokens.get(2).getValue().equalsIgnoreCase("on")){
                    throw new CommandErrorException("Invalid USE command");
                }
                return new Use(tokens.get(1).getValue(), tokens.get(3).getValue());
            case COMBINE:
                if(tokens.size() != 5 || tokens.get(1).getTokenType() != TokenType.VAR || tokens.get(2).getTokenType() != TokenType.PREPOSITION || tokens.get(3).getTokenType() != TokenType.VAR || !tokens.get(2).getValue().equalsIgnoreCase("and")){
                    throw new CommandErrorException("Invalid combine command");
                }
                return new Combine(tokens.get(1).getValue(), tokens.get(3).getValue());
            case QUIT:
                if(tokens.get(1).getTokenType() != TokenType.EOL){
                    throw new CommandErrorException("Invalid quit");
                }
                return new Quit();
            case STATUS:
                if(tokens.size() != 3 || tokens.get(1).getTokenType() != TokenType.VAR){
                    throw new CommandErrorException("Invalid status");
                }
                return new Status(tokens.get(1).getValue());
            case LOOK:
                if(tokens.size() != 3 || tokens.get(1).getTokenType() != TokenType.VAR){
                    throw new CommandErrorException("Invalid look");
                }
                return new Look(tokens.get(1).getValue());
            case HELP:
                if(tokens.size() > 3){
                    throw new CommandErrorException("Invalid HELP command");
                }
                /*
                else if(tokens.size() == 2 && tokens.get(1).getTokenType() != TokenType.VAR){
                    throw new CommandErrorException("Invalid HELP command");
                }
                */

                if(tokens.size() == 2){
                    return new Help();
                }else{
                    return new Help(tokens.get(1).getValue());
                }
            case DROP:
                if(tokens.size() != 3 || tokens.get(1).getTokenType() != TokenType.VAR){
                    throw new CommandErrorException("Invalid drop command");
                }
                return new Drop(tokens.get(1).getValue());
            case SEARCH:
                if(tokens.size() != 3 || tokens.get(1).getTokenType() != TokenType.VAR){
                    throw new CommandErrorException("Invalid search command");
                }
                return new Search(tokens.get(1).getValue());
            case SAVE:
                if(tokens.size() != 2){
                    throw new CommandErrorException("Invalid save command");
                }
                return new Save("data/game1.txt"); //The file where everything gets saved
            case LOAD:
                if(tokens.size() != 2){
                    throw new CommandErrorException("Invalid load command");
                }
                return new Load();
            case BUY:
                if(tokens.size() != 5 || tokens.get(1).getTokenType() != TokenType.VAR || tokens.get(2).getTokenType() != TokenType.PREPOSITION || !tokens.get(2).getValue().equalsIgnoreCase("for") || tokens.get(3).getTokenType() != TokenType.VAR){
                    throw new CommandErrorException("Invalid buy command");
                }
                return new Buy(tokens.get(1).getValue(), tokens.get(3).getValue());
            default:
                throw new CommandErrorException("Invalid command.");
        }
    }
}
