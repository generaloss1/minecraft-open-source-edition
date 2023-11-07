package minecraftose.main.chunk.storage;

import minecraftose.client.block.BlockProps;
import minecraftose.client.block.ClientBlocks;

import java.util.function.Predicate;

public enum HeightmapType{

    SURFACE(blockProps -> blockProps.getBlock() == ClientBlocks.AIR),
    UNDERWATER_SURFACE(blockProps -> blockProps.getBlock() == ClientBlocks.AIR || blockProps.getBlock() == ClientBlocks.WATER),
    LIGHT_SURFACE(BlockProps::isLightTransparent);

    
    public final Predicate<BlockProps> isOpaque;
    
    HeightmapType(Predicate<BlockProps> isOpaque){
        this.isOpaque = isOpaque;
    }
    
}
