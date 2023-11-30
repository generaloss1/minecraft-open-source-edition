package minecraftose.main.block;

public class BlockStateBuilder{

    private final Block block;

    public BlockStateBuilder(Block block){
        this.block = block;
    }

    protected BlockState build(){
        return new BlockState(block);
    }

}
