package minecraftose.main.block;

import minecraftose.main.entity.Player;
import minecraftose.main.level.Level;
import minecraftose.main.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class Block{

    private final int ID;
    private final Map<Byte, BlockState> states;

    public Block(int ID){
        this.ID = ID;
        this.states = new HashMap<>();
        Registry.block.register(ID, this);
    }

    public final int getID(){
        return ID;
    }


    public final BlockState getState(int stateID){
        return states.get((byte) stateID);
    }

    public BlockState getDefaultState(){
        return getState(0);
    }


    public final Block newState(int stateID, BlockState state){
        states.put((byte) stateID, state);
        return this;
    }

    public final Block newState(int stateID, BlockStateBuildProcessor processor){
        final BlockStateBuilder builder = new BlockStateBuilder(this);
        processor.build(builder);
        return newState(stateID, builder.build());
    }


    public void onPlace(Level level, Player player, int x, int y, int z){
        // level.setBlock(x, y, z, this);
    }

}
