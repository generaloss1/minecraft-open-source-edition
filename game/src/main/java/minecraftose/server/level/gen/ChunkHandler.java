package minecraftose.server.level.gen;

import minecraftose.main.chunk.ChunkBase;

public interface ChunkHandler<C extends ChunkBase>{

    void handle(C chunk);

}
