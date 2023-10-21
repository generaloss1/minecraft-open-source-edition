package minecraftose.client.block.air;

import minecraftose.client.block.Block;
import minecraftose.client.resources.GameResources;

public class VoidAir extends Block{
    
    public VoidAir(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        newState();
    }
    
}
