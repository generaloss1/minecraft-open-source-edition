package minecraftose.main.level;

import minecraftose.main.chunk.ChunkBase;
import minecraftose.main.chunk.storage.ChunkPos;

public abstract class ChunkHolder<C extends ChunkBase>{

    public abstract C getChunk(long packedChunkPos);

    public final C getChunk(int chunkX, int chunkZ){
        return getChunk(ChunkPos.pack(chunkX, chunkZ));
    }

    public final C getChunk(ChunkPos chunkPos){
        return getChunk(chunkPos.pack());
    }

    public final C getChunkGlobal(int x, int z){
        return getChunk(ChunkBase.toGridPos(x), ChunkBase.toGridPos(z));
    }


    public abstract boolean hasChunk(long packedChunkPos);

    public boolean hasChunk(int chunkX, int chunkZ){
        return hasChunk(ChunkPos.pack(chunkX, chunkZ));
    }

    public final boolean hasChunk(ChunkPos chunkPos){
        return hasChunk(chunkPos.x, chunkPos.z);
    }

    public final boolean hasChunk(C chunk){
        return hasChunk(chunk.pos());
    }


    public abstract boolean putChunk(C chunk);


    public abstract boolean removeChunk(long packedChunkPos);

    public final boolean removeChunk(int chunkX, int chunkZ){
        return removeChunk(ChunkPos.pack(chunkX, chunkZ));
    }

    public final boolean removeChunk(ChunkPos chunkPos){
        return removeChunk(chunkPos.x, chunkPos.z);
    }

    public final boolean removeChunk(C chunk){
        return removeChunk(chunk.pos());
    }


    public abstract void removeAllChunks();

}
