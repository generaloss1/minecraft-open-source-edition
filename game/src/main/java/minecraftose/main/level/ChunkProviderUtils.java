package minecraftose.main.level;

import minecraftose.client.chunk.ChunkC;
import minecraftose.main.chunk.ChunkBase;
import minecraftose.main.chunk.ChunkUtils;

public class ChunkProviderUtils{
    
    public static void rebuildNeighborChunks(ChunkC chunk){
        ChunkC neighbor;
        
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
    
    public static void rebuildNeighborChunks(ChunkC chunk, int lx, int lz){
        ChunkC neighbor;
        
        if(lx == 0){
            neighbor = ChunkUtils.getNeighborChunk(chunk, -1, 0);
            if(neighbor != null)
                neighbor.rebuild(false);
            
            if(lz == ChunkBase.SIZE_IDX){
                neighbor = ChunkUtils.getNeighborChunk(chunk, -1, 1);
                if(neighbor != null)
                    neighbor.rebuild(false);
            }else if(lz == 0 ){
                neighbor = ChunkUtils.getNeighborChunk(chunk, -1, -1);
                if(neighbor != null)
                    neighbor.rebuild(false);
            }
        }else if(lx == ChunkBase.SIZE_IDX){
            neighbor = ChunkUtils.getNeighborChunk(chunk, 1, 0);
            if(neighbor != null)
                neighbor.rebuild(false);
            
            if(lz == ChunkBase.SIZE_IDX){
                neighbor = ChunkUtils.getNeighborChunk(chunk, 1, 1);
                if(neighbor != null)
                    neighbor.rebuild(false);
            }else if(lz == 0 ){
                neighbor = ChunkUtils.getNeighborChunk(chunk, 1, -1);
                if(neighbor != null)
                    neighbor.rebuild(false);
            }
        }
        
        if(lz == ChunkBase.SIZE_IDX){
            neighbor = ChunkUtils.getNeighborChunk(chunk, 0, 1);
            if(neighbor != null)
                neighbor.rebuild(false);
        }else if(lz == 0){
            neighbor = ChunkUtils.getNeighborChunk(chunk, 0, -1);
            if(neighbor != null)
                neighbor.rebuild(false);
        }
    }
    
}
