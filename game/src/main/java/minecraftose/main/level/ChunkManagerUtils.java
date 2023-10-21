package minecraftose.main.level;

import jpize.math.vecmath.vector.Vec2f;
import jpize.math.vecmath.vector.Vec3f;
import minecraftose.client.chunk.ClientChunk;
import minecraftose.main.chunk.ChunkUtils;

public class ChunkManagerUtils{
    
    public static void rebuildNeighborChunks(ClientChunk chunk){
        ClientChunk neighbor;
        
        neighbor = ChunkUtils.getNeighborChunk(chunk, -1, 0);
        if(neighbor != null)
            neighbor.rebuild(false);
        
        neighbor = ChunkUtils.getNeighborChunk(chunk, -1, 1);
        if(neighbor != null)
            neighbor.rebuild(false);
        
        neighbor = ChunkUtils.getNeighborChunk(chunk, -1, -1);
        if(neighbor != null)
            neighbor.rebuild(false);
   
        neighbor = ChunkUtils.getNeighborChunk(chunk, 1, 0);
        if(neighbor != null)
            neighbor.rebuild(false);
        
        
        neighbor = ChunkUtils.getNeighborChunk(chunk, 1, 1);
        if(neighbor != null)
            neighbor.rebuild(false);
        
        neighbor = ChunkUtils.getNeighborChunk(chunk, 1, -1);
        if(neighbor != null)
            neighbor.rebuild(false);
            
        neighbor = ChunkUtils.getNeighborChunk(chunk, 0, 1);
        if(neighbor != null)
            neighbor.rebuild(false);
        
        neighbor = ChunkUtils.getNeighborChunk(chunk, 0, -1);
        if(neighbor != null)
            neighbor.rebuild(false);
    }
    
    public static void rebuildNeighborChunks(ClientChunk chunk, int lx, int lz){
        ClientChunk neighbor;
        
        if(lx == 0){
            neighbor = ChunkUtils.getNeighborChunk(chunk, -1, 0);
            if(neighbor != null)
                neighbor.rebuild(false);
            
            if(lz == ChunkUtils.SIZE_IDX){
                neighbor = ChunkUtils.getNeighborChunk(chunk, -1, 1);
                if(neighbor != null)
                    neighbor.rebuild(false);
            }else if(lz == 0 ){
                neighbor = ChunkUtils.getNeighborChunk(chunk, -1, -1);
                if(neighbor != null)
                    neighbor.rebuild(false);
            }
        }else if(lx == ChunkUtils.SIZE_IDX){
            neighbor = ChunkUtils.getNeighborChunk(chunk, 1, 0);
            if(neighbor != null)
                neighbor.rebuild(false);
            
            if(lz == ChunkUtils.SIZE_IDX){
                neighbor = ChunkUtils.getNeighborChunk(chunk, 1, 1);
                if(neighbor != null)
                    neighbor.rebuild(false);
            }else if(lz == 0 ){
                neighbor = ChunkUtils.getNeighborChunk(chunk, 1, -1);
                if(neighbor != null)
                    neighbor.rebuild(false);
            }
        }
        
        if(lz == ChunkUtils.SIZE_IDX){
            neighbor = ChunkUtils.getNeighborChunk(chunk, 0, 1);
            if(neighbor != null)
                neighbor.rebuild(false);
        }else if(lz == 0){
            neighbor = ChunkUtils.getNeighborChunk(chunk, 0, -1);
            if(neighbor != null)
                neighbor.rebuild(false);
        }
    }
    
    
    public static float distToChunk(int x, int z, Vec3f pos){
        return Vec2f.len(x + 0.5F - pos.x / ChunkUtils.SIZE, z + 0.5F - pos.z / ChunkUtils.SIZE);
    }
    
    public static float distToChunk(int x, int z, Vec2f pos){
        return Vec2f.len(x + 0.5F - pos.x / ChunkUtils.SIZE, z + 0.5F - pos.y / ChunkUtils.SIZE);
    }
    
}
