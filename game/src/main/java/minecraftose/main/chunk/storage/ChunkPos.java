package minecraftose.main.chunk.storage;

import minecraftose.main.chunk.ChunkUtils;
import minecraftose.main.chunk.ChunkBase;

import java.util.Objects;

public class ChunkPos{

    public final int x, z;

    public ChunkPos(int x, int z){
        this.x = x;
        this.z = z;
    }

    public ChunkPos(long packed){
        this.x = unpackX(packed);
        this.z = unpackZ(packed);
    }
    

    public ChunkPos getNeighbor(int offsetX, int offsetZ){
        return new ChunkPos(this.x + offsetX, this.z + offsetZ);
    }

    public long getNeighborPacked(int x, int z){
        return pack(this.x + x, this.z + z);
    }


    public int globalX(){
        return x * ChunkBase.SIZE;
    }
    
    public int globalZ(){
        return z * ChunkBase.SIZE;
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

    public long pack(){
        return pack(x, z);
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, z);
    }
    
    @Override
    public String toString(){
        return x + ", " + z;
    }


    public static int fromGlobal(int xz){
        return ChunkBase.toGridPos(xz);
    }

    public static ChunkPos fromGlobal(int x, int z){
        return new ChunkPos(fromGlobal(x), fromGlobal(z));
    }

    public static long pack(int chunkX, int chunkZ){
        return ((long) chunkZ) << 32 | (chunkX & 0xFFFFFFFFL);
    }

    public static int unpackX(long packed){
        return (int) packed;
    }

    public static int unpackZ(long packed){
        return (int) (packed >> 32);
    }

    public static long getNeighborPacked(long packed, int offsetX, int offsetZ){
        return pack(offsetX + unpackX(packed), offsetZ + unpackZ(packed));
    }

}
