package minecraftose.client.chunk.builder;

import minecraftose.client.chunk.ClientChunk;

public interface ChunkCallback{

    void invoke(ClientChunk chunk, int lx, int lz);

}
