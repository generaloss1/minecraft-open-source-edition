package minecraftose.client.block.air;

import minecraftose.client.block.ClientBlock;
import minecraftose.client.resources.GameResources;

public class Air extends ClientBlock{

    public Air(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        newState();
    }
    
}