package org.uob.a2.parser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The {@code Tokeniser} class is responsible for converting a string input into a list of tokens
 * that can be parsed into commands by the game.
 * 
 * <p>
 * The tokeniser identifies keywords, variables, and special symbols, assigning each a {@code TokenType}.
 * </p>
 */
public class Tokeniser {
    ArrayList<Token> tokens;
    HashMap<String, Token> commandMapping;

    public Tokeniser(){
        tokens = new ArrayList<Token>();
        commandMapping = new HashMap<String, Token>();
        commandMapping.put("use", new Token(TokenType.USE, "use"));
        commandMapping.put("get", new Token(TokenType.GET, "get"));
        commandMapping.put("drop", new Token(TokenType.DROP, "drop"));
        commandMapping.put("look", new Token(TokenType.LOOK, "look"));
        commandMapping.put("combine", new Token(TokenType.COMBINE, "combine"));
        commandMapping.put("status", new Token(TokenType.STATUS, "status"));
        commandMapping.put("help", new Token(TokenType.HELP, "help"));
        commandMapping.put("quit", new Token(TokenType.QUIT, "quit"));
        commandMapping.put("error", new Token(TokenType.ERROR, "error")); //ask them on monday
        commandMapping.put("search", new Token(TokenType.SEARCH, "search"));
        commandMapping.put("north", new Token(TokenType.VAR, "north")); //Var is just whatever the output controls, ex in move it is north south east or west
        /* commandMapping.put("south", new Token(TokenType.VAR, "south"));
        commandMapping.put("east", new Token(TokenType.VAR, "east"));
        commandMapping.put("west", new Token(TokenType.VAR, "west"));
        */
        //commandMapping.put("wear", new Token(TokenType.WEAR, "wear"));
        commandMapping.put("save", new Token(TokenType.SAVE, "save"));
        commandMapping.put("load", new Token(TokenType.LOAD, "load"));
        commandMapping.put("map", new Token(TokenType.VAR, "map"));
        commandMapping.put("move", new Token(TokenType.MOVE, "move"));
        commandMapping.put("on", new Token(TokenType.PREPOSITION, "on")); //add these later when you figure out what this is
        commandMapping.put("key", new Token(TokenType.VAR, "key"));
        commandMapping.put("and", new Token(TokenType.PREPOSITION, "and"));
        commandMapping.put("buy", new Token(TokenType.BUY, "buy"));
        commandMapping.put("for", new Token(TokenType.PREPOSITION, "for"));
    }
    
    public String sanitise(String s){
        String out;
        out = s.toLowerCase().trim();
        return out;
    }
    
    public void tokenise(String s){
        if(s.equals("")){
            tokens.add(new Token(TokenType.EOL));
        }else{
            String[] command = sanitise(s).split("\\s+");
            for(String word : command){
                tokens.add(commandMapping.getOrDefault(word , new Token(TokenType.VAR, word)));
            }
        }
        tokens.add(new Token(TokenType.EOL));
    }

    public ArrayList<Token> getTokens(){
        return this.tokens;
    }
}
