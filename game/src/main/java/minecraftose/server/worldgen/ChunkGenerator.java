package minecraftose.server.worldgen;

import minecraftose.main.registry.Registry;
import minecraftose.server.chunk.ServerChunk;

public abstract class ChunkGenerator{

    public ChunkGenerator(){
        Registry.generator.register(this.getID(), this);
    }

    public void generate(ServerChunk chunk){ }

    public void decorate(ServerChunk chunk){ }

    public abstract byte getID();
    
}
