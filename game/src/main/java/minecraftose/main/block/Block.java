package minecraftose.main.block;

import minecraftose.main.entity.Player;
import minecraftose.main.level.Level;

import java.util.HashMap;
import java.util.Map;

public class Block{

    public final Map<Byte, BlockState> states;

    public Block(){
        this.states = new HashMap<>();
    }


    public BlockState getDefaultState(){
        return states.get((byte) 0);
    }

    public void onPlace(Level level, Player player, int x, int y, int z){ }

}
