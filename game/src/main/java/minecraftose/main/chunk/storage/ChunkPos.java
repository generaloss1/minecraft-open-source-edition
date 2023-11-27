package minecraftose.main.chunk.storage;

import minecraftose.main.chunk.ChunkUtils;

import java.util.Objects;

public class ChunkPos{

    public final int x, z;

    public ChunkPos(int x, int z){
        this.x = x;
        this.z = z;
    }
    

    public ChunkPos getNeighbor(int x, int z){
        return new ChunkPos(this.x + x, this.z + z);
    }
    
    public int globalX(){
        return x * ChunkUtils.SIZE;
    }
    
    public int globalZ(){
        return z * ChunkUtils.SIZE;
    }


    @Override
    public boolean equals(Object object){
        if(object == this)
            return true;
        if(object == null || object.getClass() != getClass())
            return false;
        ChunkPos chunkPos = (ChunkPos) object;
        return x == chunkPos.x && z == chunkPos.z;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, z);
    }
    
    @Override
    public String toString(){
        return x + ", " + z;
    }


    public static ChunkPos fromGlobalCoords(int x, int z){
        return new ChunkPos(ChunkUtils.getChunkPos(x), ChunkUtils.getChunkPos(z));
    }

}
