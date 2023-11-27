package minecraftose.server.worldgen.generator;

import minecraftose.server.chunk.ServerChunk;
import minecraftose.server.worldgen.ChunkGenerator;

public class ChunkTestNewGenerator extends ChunkGenerator{

    public static final ChunkTestNewGenerator INSTANCE = new ChunkTestNewGenerator();

    @Override
    public void generate(ServerChunk chunk){
        super.generate(chunk);
    }

    @Override
    public byte getID(){
        return 3;
    }

}
