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

public class Gravel extends ClientBlock{

    public Gravel(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final BlockModel model = new BlockModel(ChunkMeshType.SOLID)
            .allFaces(resources.getBlockRegion("gravel"));

        newState()
            .setSolid(true)
            .setLightLevel(0)
            .setOpacity(ChunkUtils.MAX_LIGHT_LEVEL)
            .setTranslucent(false)
            .setSoundPack(SoundType.GRAVEL)
            .setFacing(Dir.NONE)
            .setModel(model)
            .setCollide(BlockCollide.SOLID)
            .setCursor(BlockCursor.SOLID);
    }

}
