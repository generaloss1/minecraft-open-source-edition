package minecraftose.client.entity;

import minecraftose.client.Minecraft;
import minecraftose.client.level.LevelC;

public class RemotePlayer extends AbstractClientPlayer{
    
    public RemotePlayer(Minecraft minecraft, LevelC level, String name){
        super(minecraft, level, name);
    }

}
