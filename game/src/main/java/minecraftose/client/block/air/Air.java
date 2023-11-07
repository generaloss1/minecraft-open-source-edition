package minecraftose.client.block.air;

import minecraftose.client.block.BlockClient;
import minecraftose.client.resources.GameResources;

public class Air extends BlockClient{

    public Air(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        newState();
    }
    
}