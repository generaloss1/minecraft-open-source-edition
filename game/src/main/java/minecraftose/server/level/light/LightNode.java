package minecraftose.server.level.light;

import minecraftose.main.chunk.ChunkBase;

public class LightNode{

    public final ChunkBase chunk;
    public final byte lx;
    public final short y;
    public final byte lz;
    public final byte level;

    public LightNode(ChunkBase chunk, int lx, int y, int lz, int level){
        this.chunk = chunk;
        this.lx = (byte) lx;
        this.y = (short) y;
        this.lz = (byte) lz;
        this.level = (byte) level;
    }
}