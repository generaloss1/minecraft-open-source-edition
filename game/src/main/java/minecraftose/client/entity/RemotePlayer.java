package minecraftose.client.entity;

import minecraftose.client.ClientGame;
import minecraftose.client.level.ClientLevel;

public class RemotePlayer extends AbstractClientPlayer{
    
    public RemotePlayer(ClientGame game, ClientLevel level, String name){
        super(game, level, name);
    }
    
}
