package minecraftose.main.block.list;

import minecraftose.main.block.Block;
import minecraftose.main.block.BlockState;

public class AirBlock extends Block{

    public static final byte AIR_ID = 0;

    public AirBlock(){
        super(AIR_ID);
        newState(0, new BlockState(this));
    }

    public static boolean isBlockAir(BlockState state){
        return state.getBlock().getID() == AIR_ID;
    }

}
