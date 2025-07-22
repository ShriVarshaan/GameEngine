package org.uob.a2.commands;

import org.uob.a2.gameobjects.*;

public class Load extends Command{
    public Load(){
        commandType = CommandType.LOAD;
    }

    public String execute(GameState gameState){
        return "Loading the saved state game";
    }
}