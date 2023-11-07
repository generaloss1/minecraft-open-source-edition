package minecraftose.client.block.vanilla;

import minecraftose.client.block.BlockClient;
import minecraftose.client.block.model.BlockModel;
import minecraftose.client.block.shape.BlockCollide;
import minecraftose.client.block.shape.BlockCursor;
import minecraftose.client.chunk.mesh.ChunkMeshType;
import minecraftose.client.resources.GameResources;
import minecraftose.main.Dir;
import minecraftose.main.audio.SoundType;

public class Glass extends BlockClient{

    public Glass(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final BlockModel model = new BlockModel(ChunkMeshType.SOLID)
                .allFaces(resources.getBlockRegion("glass"));

        newState()
                .setSolid(true)
                .setLightLevel(0)
                .setOpacity(4)
                .setTranslucent(false)
                .setSoundPack(SoundType.GLASS)
                .setFacing(Dir.NONE)
                .setModel(model)
                .setCollide(BlockCollide.SOLID)
                .setCursor(BlockCursor.SOLID);
    }

}