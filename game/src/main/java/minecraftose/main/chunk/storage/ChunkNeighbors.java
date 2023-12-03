package minecraftose.main.chunk.storage;

import minecraftose.main.chunk.ChunkBase;
import minecraftose.main.level.ChunkHolder;

/**  ◯───────→ X
 *   │ [0][1][2]
 *   │ [3] 4 [5]
 *   ↓ [6][7][8]
 *   Z
 *  *  *  *  *  **/
public class ChunkNeighbors{

    private final ChunkBase chunk;
    private final ChunkHolder<?> chunkProvider;
    private final long[] neighbors;
    private final int distance, size;

    public ChunkNeighbors(ChunkBase chunk, int radius){
        this.chunk = chunk;
        this.chunkProvider = chunk.getLevel().getChunkProvider();

        this.distance = radius;
        this.size = radius * 2 + 1;

        this.neighbors = new long[size * size];
        for(int i = 0; i < neighbors.length; i++){
            final int x = i % size;
            final int z = (i - x) / size;

            neighbors[i] = chunk.pos().getNeighborPacked(x - radius, z - radius);
        }
    }

    public long[] array(){
        return neighbors;
    }

    public int index(int neighborX, int neighborZ){
        return (neighborZ + distance) * size + (neighborX + distance);
    }

    public long getNeighborPackedPos(int offsetX, int offsetZ){
        return neighbors[index(offsetX, offsetZ)];
    }

    public ChunkBase getNeighborChunk(int offsetX, int offsetZ){
        return chunkProvider.getChunk(getNeighborPackedPos(offsetX, offsetZ));
    }

    public ChunkBase[] getChunks(){
        return new ChunkBase[]{ // Rows - X, Columns - Z
            getNeighborChunk(-1, -1), getNeighborChunk(0, -1) , getNeighborChunk(1, -1),
            getNeighborChunk(-1,  0), chunk                   , getNeighborChunk(1,  0),
            getNeighborChunk(-1,  1), getNeighborChunk(0,  1) , getNeighborChunk(1,  1)
        };
    }

}
