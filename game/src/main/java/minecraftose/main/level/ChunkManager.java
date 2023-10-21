package minecraftose.main.level;

import minecraftose.main.chunk.LevelChunk;
import minecraftose.main.chunk.storage.ChunkPos;

public abstract class ChunkManager{
    
    public abstract LevelChunk getChunk(ChunkPos chunkPos);
    
    public abstract LevelChunk getChunk(int chunkX, int chunkZ);
    
}
