package minecraftose.main.chunk;

import minecraftose.main.chunk.storage.SectionPos;

import static minecraftose.main.chunk.ChunkUtils.getIndex;

public class ChunkSection{

    public final SectionPos position;
    public final short[] blocks;
    public final byte[] light;
    public int blocksNum;
    
    public ChunkSection(SectionPos position, short[] blocks, byte[] light){
        this.position = position;
        this.blocks = blocks;
        this.light = light;
    }
    
    public ChunkSection(SectionPos position){
        this(
            position,
            new short[ChunkBase.VOLUME],
            new byte[ChunkBase.VOLUME]
        );
    }


    public SectionPos getPosition(){
        return position;
    }
    
    
    public short getBlockState(int lx, int ly, int lz){
        return blocks[ChunkUtils.getIndex(lx, ly, lz)];
    }
    
    protected void setBlockState(int lx, int ly, int lz, short blockState){
        blocks[ChunkUtils.getIndex(lx, ly, lz)] = blockState;
    }
    
    
    public int getSkyLight(int lx, int ly, int lz){
        return light[ChunkUtils.getIndex(lx, ly, lz)] & 0xF;
    }

    protected void setSkyLight(int lx, int ly, int lz, int level){
        final int blockLight = light[ChunkUtils.getIndex(lx, ly, lz)] >> 4;
        light[ChunkUtils.getIndex(lx, ly, lz)] = (byte) ((blockLight << 4) | level);
    }

    public int getBlockLight(int lx, int ly, int lz){
        return (light[ChunkUtils.getIndex(lx, ly, lz)] >> 4) & 0xF;
    }

    protected void setBlockLight(int lx, int ly, int lz, int level){
        final int skyLight = light[ChunkUtils.getIndex(lx, ly, lz)] & 0xF;
        light[ChunkUtils.getIndex(lx, ly, lz)] = (byte) ((level << 4) | skyLight);
    }

    
    public int getBlocksNum(){
        return blocksNum;
    }

}
