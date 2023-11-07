package minecraftose.client.block.vanilla;

import minecraftose.client.block.BlockClient;
import minecraftose.client.block.model.BlockModel;
import minecraftose.client.block.model.Face;
import minecraftose.client.block.model.Quad;
import minecraftose.client.block.shape.BlockCursor;
import minecraftose.client.chunk.mesh.ChunkMeshType;
import minecraftose.client.resources.GameResources;
import minecraftose.main.Dir;
import minecraftose.main.audio.SoundType;

public class Grass extends BlockClient{

    public Grass(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final BlockModel model = new BlockModel(ChunkMeshType.CUSTOM)
                .face(new Face(new Quad(0, 1, 0,  0, 0, 0,  1, 0, 1,  1, 1, 1), resources.getBlockRegion("grass")).enableGrassColoring())
                .face(new Face(new Quad(1, 1, 0,  1, 0, 0,  0, 0, 1,  0, 1, 1), resources.getBlockRegion("grass")).enableGrassColoring());

        newState()
                .setSolid(false)
                .setLightLevel(0)
                .setOpacity(0)
                .setTranslucent(false)
                .setSoundPack(SoundType.GRASS)
                .setFacing(Dir.NONE)
                .setModel(model)
                .setCollide(null)
                .setCursor(BlockCursor.SOLID);
    }
    
}