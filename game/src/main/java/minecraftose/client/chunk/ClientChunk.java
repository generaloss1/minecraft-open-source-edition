package minecraftose.client.chunk;

import jpize.math.vecmath.matrix.Matrix4f;
import minecraftose.client.block.ClientBlock;
import minecraftose.client.block.ClientBlocks;
import minecraftose.client.chunk.mesh.ChunkMeshStack;
import minecraftose.client.control.camera.PlayerCamera;
import minecraftose.client.level.ClientLevel;
import minecraftose.main.block.ChunkBlockData;
import minecraftose.main.chunk.ChunkUtils;
import minecraftose.main.chunk.LevelChunk;
import minecraftose.main.chunk.storage.ChunkPos;
import minecraftose.main.chunk.storage.Heightmap;
import minecraftose.main.chunk.storage.HeightmapType;
import minecraftose.main.level.ChunkManagerUtils;

public class ClientChunk extends LevelChunk{

    private final ChunkMeshStack meshStack;
    private final Matrix4f translationMatrix;
    private int maxY;

    public ClientChunk(ClientLevel level, ChunkPos position){
        super(level, position);
        meshStack = new ChunkMeshStack();
        translationMatrix = new Matrix4f();
    }


    public ChunkMeshStack getMeshStack(){
        return meshStack;
    }


    public Matrix4f getTranslationMatrix(){
        return translationMatrix;
    }

    public void updateTranslationMatrix(PlayerCamera camera){
        final float relativeChunkX = position.globalX() - camera.getX();
        final float relativeChunkZ = position.globalZ() - camera.getZ();
        translationMatrix.toTranslated(relativeChunkX, 0, relativeChunkZ);
    }


    @Override
    public boolean setBlockData(int lx, int y, int lz, short blockData){
        if(!super.setBlockData(lx, y, lz, blockData) || ChunkUtils.isOutOfBounds(lx, lz))
            return false;

        final boolean blockPlaced = ChunkBlockData.getID(blockData) != ClientBlocks.AIR.getID();
        for(Heightmap heightmap: heightmaps.values())
            heightmap.update(lx, y, lz, blockPlaced);
        updateMaxY();

        rebuild(true);
        ChunkManagerUtils.rebuildNeighborChunks(this, lx, lz);

        return true;
    }

    @Override
    public boolean setBlock(int lx, int y, int lz, ClientBlock block){
        if(!super.setBlock(lx, y, lz, block) || ChunkUtils.isOutOfBounds(lx, lz))
            return false;

        final boolean blockPlaced = block != ClientBlocks.AIR;
        for(Heightmap heightmap: heightmaps.values())
            heightmap.update(lx, y, lz, blockPlaced);
        updateMaxY();

        rebuild(true);
        ChunkManagerUtils.rebuildNeighborChunks(this, lx, lz);

        return true;
    }

    @Override
    public void setSkyLight(int lx, int y, int lz, int level){
        super.setSkyLight(lx, y, lz, level);
        rebuild(true);
    }

    @Override
    public void setBlockLight(int lx, int y, int lz, int level){
        super.setSkyLight(lx, y, lz, level);
        rebuild(true);
    }


    public void rebuild(boolean important){
        getLevel().getChunkManager().rebuildChunk(this, important);
    }

    @Override
    public ClientLevel getLevel(){
        return (ClientLevel) level;
    }

    @Override
    public ClientChunk getNeighborChunk(int signX, int signZ){
        return (ClientChunk) super.getNeighborChunk(signX, signZ);
    }


    public int getMaxY(){
        return maxY;
    }

    public void updateMaxY(){
        maxY = 0;
        final Heightmap heightmapSurface = getHeightMap(HeightmapType.SURFACE);
        for(short height: heightmapSurface.getValues())
            maxY = Math.max(maxY, height);
    }

}
