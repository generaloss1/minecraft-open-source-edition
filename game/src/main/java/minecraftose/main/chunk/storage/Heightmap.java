package minecraftose.main.chunk.storage;

import minecraftose.main.chunk.ChunkBase;
import minecraftose.main.chunk.ChunkUtils;

public class Heightmap{
    
    private final ChunkBase chunk;
    private final HeightmapType type;
    private final short[] values;

    public Heightmap(ChunkBase chunk, HeightmapType type){
        this.chunk = chunk;
        this.type = type;
        this.values = new short[ChunkBase.AREA];
    }
    
    public Heightmap(ChunkBase chunk, HeightmapType type, short[] values){
        this.chunk = chunk;
        this.type = type;
        this.values = values;
    }
    
    public ChunkBase getChunk(){
        return chunk;
    }
    
    public HeightmapType getType(){
        return type;
    }
    
    
    public short[] getValues(){
        return values;
    }
    
    public int getHeight(int lx, int lz){
        return values[ChunkUtils.getIndex(lx, lz)];
    }
    
    public void setHeight(int lx, int lz, int height){
        values[ChunkUtils.getIndex(lx, lz)] = (short) height;
    }
    
    
    public void update(int lx, int y, int lz, boolean blockPlaced){
        int height = getHeight(lx, lz);
        
        if(y == height && !blockPlaced)
            for(height--; type.isOpaque.test(chunk.getBlockProps(lx, height, lz)) && height >= 0; height--);
        else if(y > height && blockPlaced)
            height = y;
        
        setHeight(lx, lz, height);
    }
    
    public void update(int lx, int lz){
        int height = ChunkBase.HEIGHT;
        for(height--; type.isOpaque.test(chunk.getBlockProps(lx, height, lz)) && height >= 0; height--);
        setHeight(lx, lz, height);
    }
    
    public void update(){
        for(int lx = 0; lx < ChunkBase.SIZE; lx++)
            for(int lz = 0; lz < ChunkBase.SIZE; lz++)
                update(lx, lz);
    }

    public void updateFrom(Heightmap heightmap){
        System.arraycopy(heightmap.values, 0, values, 0, heightmap.values.length);
    }
    
}
