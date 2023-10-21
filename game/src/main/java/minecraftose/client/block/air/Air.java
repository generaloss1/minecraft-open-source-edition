package minecraftose.client.block.air;

import minecraftose.client.block.Block;
import minecraftose.client.resources.GameResources;

public class Air extends Block{

    public Air(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        newState();
    }
    
}