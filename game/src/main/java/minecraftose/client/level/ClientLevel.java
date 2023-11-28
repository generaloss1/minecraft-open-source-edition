package minecraftose.client.level;

import jpize.math.Maths;
import minecraftose.client.Minecraft;
import minecraftose.client.block.ClientBlock;
import minecraftose.client.block.ClientBlocks;
import minecraftose.client.chunk.ClientChunk;
import minecraftose.main.biome.Biome;
import minecraftose.main.chunk.ChunkUtils;
import minecraftose.main.chunk.storage.HeightmapType;
import minecraftose.main.level.Level;

import static minecraftose.main.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

public class ClientLevel extends Level{

    private final Minecraft minecraft;
    private final ClientChunkManager chunkManager;
    private final ClientLevelConfiguration configuration;

    public ClientLevel(Minecraft minecraft, String levelName){
        this.minecraft = minecraft;
        this.chunkManager = new ClientChunkManager(this);
        this.configuration = new ClientLevelConfiguration();

        configuration.load(levelName);
    }

    public Minecraft getMinecraft(){
        return minecraft;
    }


    @Override
    public short getBlockState(int x, int y, int z){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlockState(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z));

        return ClientBlocks.VOID_AIR.getDefaultData();
    }

    @Override
    public ClientBlock getBlock(int x, int y, int z){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlock(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z));

        return ClientBlocks.VOID_AIR;
    }

    @Override
    public boolean setBlockState(int x, int y, int z, short state){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.setBlockData(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z), state);

        return false;
    }

    @Override
    public boolean setBlock(int x, int y, int z, ClientBlock block){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.setBlock(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z), block);

        return false;
    }

    @Override
    public int getHeight(HeightmapType heightmapType, int x, int z){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getHeightMap(heightmapType).getHeight(ChunkUtils.getLocalCoord(x), ChunkUtils.getLocalCoord(z));

        return 0;
    }


    @Override
    public int getSkyLight(int x, int y, int z){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getSkyLight(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z));

        return MAX_LIGHT_LEVEL;
    }

    @Override
    public void setSkyLight(int x, int y, int z, int level){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            targetChunk.setSkyLight(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z), level);
    }

    @Override
    public int getBlockLight(int x, int y, int z){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlockLight(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z));

        return MAX_LIGHT_LEVEL;
    }

    @Override
    public void setBlockLight(int x, int y, int z, int level){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            targetChunk.setBlockLight(ChunkUtils.getLocalCoord(x), y, ChunkUtils.getLocalCoord(z), level);
    }

    public int getLight(int x, int y, int z){
        final int skyLight = getSkyLight(x, y, z);
        final int blockLight = getBlockLight(x, y, z);
        final float skyBrightness = minecraft.getRenderer().getWorldRenderer().getSkyRenderer().getSkyBrightness();

        return Math.max(Maths.round(skyLight * skyBrightness), blockLight);
    }


    public Biome getBiome(int x, int z){
        final ClientChunk targetChunk = getBlockChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBiomes().getBiome(ChunkUtils.getLocalCoord(x), ChunkUtils.getLocalCoord(z));

        return Biome.VOID;
    }


    @Override
    public ClientLevelConfiguration getConfiguration(){
        return configuration;
    }

    @Override
    public ClientChunkManager getChunkManager(){
        return chunkManager;
    }

    @Override
    public ClientChunk getBlockChunk(int blockX, int blockZ){
        return chunkManager.getChunk(ChunkUtils.getChunkPos(blockX), ChunkUtils.getChunkPos(blockZ));
    }

}
