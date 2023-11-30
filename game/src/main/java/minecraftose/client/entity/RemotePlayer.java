package minecraftose.client.entity;

import minecraftose.client.Minecraft;
import minecraftose.client.level.ClientLevel;

public class RemotePlayer extends AbstractClientPlayer{
    
    public RemotePlayer(Minecraft minecraft, ClientLevel level, String name){
        super(minecraft, level, name);
    }

}
