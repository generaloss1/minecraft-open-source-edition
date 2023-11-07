package minecraftose.main.block;

import minecraftose.main.registry.Registry;

public class BlockBuilder{

    private final int ID;

    public BlockBuilder(int ID){
        this.ID = ID;
    }


    public Block register(){
        final Block block = new Block();
        Registry.block.register(ID, block);
        return block;
    }

}
