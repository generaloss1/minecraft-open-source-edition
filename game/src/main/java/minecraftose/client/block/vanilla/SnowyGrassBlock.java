package minecraftose.client.block.vanilla;

import minecraftose.client.block.ClientBlock;
import minecraftose.client.block.model.BlockModel;
import minecraftose.client.block.shape.BlockCollide;
import minecraftose.client.block.shape.BlockCursor;
import minecraftose.client.chunk.mesh.ChunkMeshType;
import minecraftose.client.resources.GameResources;
import minecraftose.main.Dir;
import minecraftose.main.audio.SoundType;
import minecraftose.main.chunk.ChunkUtils;

public class SnowyGrassBlock extends ClientBlock{

    public SnowyGrassBlock(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final BlockModel model = new BlockModel(ChunkMeshType.SOLID)
                .sideXZFaces(resources.getBlockRegion("grass_block_snow"))
                .pyFace(resources.getBlockRegion("snow"))
                .nyFace(resources.getBlockRegion("dirt"));

        newState()
                .setSolid(true)
                .setLightLevel(0)
                .setOpacity(ChunkUtils.MAX_LIGHT_LEVEL)
                .setTranslucent(false)
                .setSoundPack(SoundType.SNOWY_GRASS_BLOCK)
                .setFacing(Dir.NONE)
                .setModel(model)
                .setCollide(BlockCollide.SOLID)
                .setCursor(BlockCursor.SOLID);
    }

}