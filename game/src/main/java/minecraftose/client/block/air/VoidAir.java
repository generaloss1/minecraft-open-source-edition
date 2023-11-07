package minecraftose.client.block.air;

import minecraftose.client.block.BlockClient;
import minecraftose.client.resources.GameResources;

public class VoidAir extends BlockClient{
    
    public VoidAir(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        newState();
    }
    
}
