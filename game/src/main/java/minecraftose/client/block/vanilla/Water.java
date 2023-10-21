package minecraftose.client.block.vanilla;

import minecraftose.client.block.Block;
import minecraftose.client.block.model.BlockModel;
import minecraftose.client.chunk.mesh.ChunkMeshType;
import minecraftose.client.resources.GameResources;
import minecraftose.main.Dir;

public class Water extends Block{

    public Water(int id){
        super(id);
    }

    @Override
    public void load(GameResources resources){
        final BlockModel model = new BlockModel(ChunkMeshType.TRANSLUCENT)
                .allFaces( resources.getBlockRegion("water"), (byte) 2);

        newState()
                .setSolid(true)
                .setLightLevel(0)
                .setOpacity(1)
                .setTranslucent(false)
                .setSoundPack(null)
                .setFacing(Dir.NONE)
                .setModel(model)
                .setCollide(null)
                .setCursor(null);
    }

}
