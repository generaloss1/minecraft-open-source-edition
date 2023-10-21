package minecraftose.client.block.vanilla;

import minecraftose.client.block.Block;
import minecraftose.client.block.model.BlockModel;
import minecraftose.client.block.shape.BlockCollide;
import minecraftose.client.block.shape.BlockCursor;
import minecraftose.client.chunk.mesh.ChunkMeshType;
import minecraftose.client.resources.GameResources;
import minecraftose.main.Dir;
import minecraftose.main.audio.BlockSoundPack;
import minecraftose.main.chunk.ChunkUtils;

public class Lamp extends Block{

    public Lamp(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final BlockModel modelOn = new BlockModel(ChunkMeshType.SOLID).allFaces(resources.getBlockRegion("redstone_lamp_on"));
        final BlockModel modelOff = new BlockModel(ChunkMeshType.SOLID).allFaces(resources.getBlockRegion("redstone_lamp"));

        newState()
                .setSolid(true)
                .setLightLevel(ChunkUtils.MAX_LIGHT_LEVEL)
                .setOpacity(ChunkUtils.MAX_LIGHT_LEVEL)
                .setTranslucent(false)
                .setSoundPack(BlockSoundPack.GLASS)
                .setFacing(Dir.NONE)
                .setModel(modelOn)
                .setCollide(BlockCollide.SOLID)
                .setCursor(BlockCursor.SOLID);

        newState()
                .setSolid(true)
                .setLightLevel(0)
                .setOpacity(ChunkUtils.MAX_LIGHT_LEVEL)
                .setTranslucent(false)
                .setSoundPack(BlockSoundPack.GLASS)
                .setFacing(Dir.NONE)
                .setModel(modelOff)
                .setCollide(BlockCollide.SOLID)
                .setCursor(BlockCursor.SOLID);
    }
    
}
