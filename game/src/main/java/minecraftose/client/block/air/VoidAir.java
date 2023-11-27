package minecraftose.client.block.air;

import minecraftose.client.block.ClientBlock;
import minecraftose.client.resources.GameResources;

public class VoidAir extends ClientBlock{
    
    public VoidAir(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        newState();
    }
    
}
