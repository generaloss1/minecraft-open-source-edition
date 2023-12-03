package minecraftose.main.level;

import minecraftose.main.chunk.ChunkBase;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChunkMap<C extends ChunkBase> extends ChunkHolder<C>{

    private final Map<Long, C> chunks;

    public ChunkMap(){
        this.chunks = new ConcurrentHashMap<>();
    }


    public Collection<C> getChunks(){
        return chunks.values();
    }

    public Set<Long> getPositions(){
        return chunks.keySet();
    }


    @Override
    public C getChunk(long packedChunkPos){
        return chunks.get(packedChunkPos);
    }


    @Override
    public boolean hasChunk(long packedChunkPos){
        return chunks.containsKey(packedChunkPos);
    }


    @Override
    public boolean putChunk(C chunk){
        if(chunk == null)
            return false;

        final long packedChunkPos = chunk.pos().pack();
        if(hasChunk(packedChunkPos))
            return false;

        chunks.put(packedChunkPos, chunk);
        return true;
    }

    @Override
    public boolean removeChunk(long packedChunkPos){
        if(!hasChunk(packedChunkPos))
            return false;

        chunks.remove(packedChunkPos);
        return true;
    }


    @Override
    public void removeAllChunks(){
        chunks.clear();
    }


    public void clear(){
        chunks.clear();
    }

}
