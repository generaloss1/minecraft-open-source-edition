package minecraftose.client.chunk.builder;

import minecraftose.main.chunk.ChunkBase;

public interface ChunkCallback{

    void invoke(ChunkBase chunk, int lx, int lz);

}
