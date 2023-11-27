package minecraftose.main.chunk;

import minecraftose.client.block.ClientBlock;
import minecraftose.client.block.BlockProps;
import minecraftose.client.block.ClientBlocks;
import minecraftose.main.biome.chunk.BiomeMap;
import minecraftose.main.block.ChunkBlockData;
import minecraftose.main.chunk.neighbors.LevelChunkNeighbors;
import minecraftose.main.chunk.storage.ChunkPos;
import minecraftose.main.chunk.storage.Heightmap;
import minecraftose.main.chunk.storage.HeightmapType;
import minecraftose.main.chunk.storage.SectionPos;
import minecraftose.main.level.Level;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LevelChunk{

    protected final Level level;
    protected final ChunkPos position;
    protected final LevelChunkNeighbors neighbors;
    protected final LevelChunkSection[] sections;
    protected int highestSectionIndex;
    protected final Map<HeightmapType, Heightmap> heightmaps;
    protected final BiomeMap biomes;
    private final long spawnTimeMillis;

    public LevelChunk(Level level, ChunkPos position){
        this.level = level;
        this.position = position;

        this.spawnTimeMillis = System.currentTimeMillis();
        this.neighbors = new LevelChunkNeighbors(this, 1);
        this.sections = new LevelChunkSection[16];
        this.highestSectionIndex = -1;
        
        // Heightmaps
        this.heightmaps = new HashMap<>();
        for(HeightmapType type: HeightmapType.values())
            heightmaps.put(type, new Heightmap(this, type));

        this.biomes = new BiomeMap();
    }
    
    public Level getLevel(){
        return level;
    }


    public long getLifeTimeMillis(){
        return System.currentTimeMillis() - spawnTimeMillis;
    }
    
    
    public short getBlockState(int lx, int y, int lz){
        if(ChunkUtils.isOutOfBounds(lx, y, lz))
            return ClientBlocks.AIR.getDefaultData();
        
        // Находим по Y нужную секцию
        final int sectionIndex = ChunkUtils.getSectionIndex(y);
        final LevelChunkSection section = getSection(sectionIndex);
        if(section == null)
            return ClientBlocks.AIR.getDefaultData();
        
        // Возвращаем блок
        return section.getBlockState(lx, ChunkUtils.getLocalCoord(y), lz);
    }
    
    public boolean setBlockData(int lx, int y, int lz, short blockData){
        if(ChunkUtils.isOutOfBounds(lx, y, lz))
            return false;
        
        // Проверяем является ли устанавливаемый блок воздухом
        final int airID = ClientBlocks.AIR.getID();
        final int blockID = ChunkBlockData.getID(blockData);
        final boolean isBlockAir = (blockID == airID);
        
        // Находим по Y нужную секцию
        final int sectionIndex = ChunkUtils.getSectionIndex(y);
        LevelChunkSection section = getSection(sectionIndex);
        
        if(section != null){
            // Если устанавливаемый блок воздух, и в секции остался один блок
            // - удаляем секцию, чтобы не загружать память секцией с 4096 блоками воздуха, и завершаем метод
            if(section.getBlocksNum() == 1 && isBlockAir && (getSection(sectionIndex - 1) == null || getSection(sectionIndex + 1) == null)){
                removeSection(sectionIndex);
                return true;
            }
            
        // Если секция равна null
        }else{
            // Если устанавливаемый блок воздух - ничего не делаем и завершаем метод
            if(isBlockAir)
                return false;
            
            // Если устанавливаемый блок не воздух - создаем секцию
            section = createSection(sectionIndex);
        }
        
        // Проверка на равенство устанавливаемого блока и текущего
        final int ly = ChunkUtils.getLocalCoord(y);
        final int oldBlockID = ChunkBlockData.getID(section.getBlockState(lx, ly, lz));
        if(blockID == oldBlockID)
            return false;
        
        // Подсчет блоков в секции
        if(isBlockAir)
            section.blocksNum--; // Если блок был заменен воздухом - кол-во блоков в секции уменьшаем на 1
        else
            section.blocksNum++; // Если воздух был заменен блоком - увеличиваем на 1
        
        // УСТАНАВЛИВАЕМ БЛОК
        section.setBlockState(lx, ly, lz, blockData);
        return true;
    }
    
    
    public LevelChunkSection[] getSections(){
        return sections;
    }
    
    public LevelChunkSection getSection(int index){
        return sections[index];
    }

    public LevelChunkSection getBlockSection(int y){
        return getSection(ChunkUtils.getSectionIndex(y));
    }
    
    public int getHighestSectionIndex(){
        return highestSectionIndex;
    }
    
    protected void removeSection(int index){
        sections[index] = null;
        if(highestSectionIndex != index)
            return;
        
        for(int i = sections.length - 1; i >= 0; i--)
            if(getSection(i) != null){
                highestSectionIndex = i;
                return;
            }
        
        highestSectionIndex = -1;
    }
    
    protected LevelChunkSection createSection(int index){
        highestSectionIndex = Math.max(highestSectionIndex, index);
        return sections[index] = new LevelChunkSection(new SectionPos(position.x, index, position.z));
    }
    
    public void setSections(LevelChunkSection[] sections, int highestSectionIndex){
        System.arraycopy(sections, 0, this.sections, 0, sections.length);
        this.highestSectionIndex = highestSectionIndex;
    }


    public ClientBlock getBlock(int lx, int y, int lz){
        return ChunkBlockData.getBlock(getBlockState(lx, y, lz));
    }

    public BlockProps getBlockProps(int lx, int y, int lz){
        return ChunkBlockData.getProps(getBlockState(lx, y, lz));
    }

    public boolean setBlock(int lx, int y, int lz, ClientBlock block){
        return setBlockData(lx, y, lz, block.getDefaultData());
    }


    public boolean isEmpty(){
        return highestSectionIndex == -1;
    }
    
    
    public Collection<Heightmap> getHeightmaps(){
        return heightmaps.values();
    }
    
    public void setHeightmaps(Map<HeightmapType, short[]> heightmaps){
        for(Map.Entry<HeightmapType, short[]> heightmapEntry: heightmaps.entrySet()){
            final Heightmap heightmap = new Heightmap(this, heightmapEntry.getKey(), heightmapEntry.getValue());
            this.heightmaps.put(heightmap.getType(), heightmap);
        }
    }
    
    public Heightmap getHeightMap(HeightmapType type){
        return heightmaps.get(type);
    }
    
    public ChunkPos getPosition(){
        return position;
    }
    
    
    public int getSkyLight(int lx, int y, int lz){
        if(ChunkUtils.isOutOfBounds(y))
            return ChunkUtils.MAX_LIGHT_LEVEL;
        
        // Находим по Y нужную секцию
        final int sectionIndex = ChunkUtils.getSectionIndex(y);
        final LevelChunkSection section = getSection(sectionIndex);
        if(section == null)
            return ChunkUtils.MAX_LIGHT_LEVEL;
        
        // Возвращаем блок
        return section.getSkyLight(lx, ChunkUtils.getLocalCoord(y), lz);
    }

    public int getBlockLight(int lx, int y, int lz){
        if(ChunkUtils.isOutOfBounds(y))
            return ChunkUtils.MAX_LIGHT_LEVEL;

        // Находим по Y нужную секцию
        final int sectionIndex = ChunkUtils.getSectionIndex(y);
        final LevelChunkSection section = getSection(sectionIndex);
        if(section == null)
            return ChunkUtils.MAX_LIGHT_LEVEL;

        // Возвращаем блок
        return section.getBlockLight(lx, ChunkUtils.getLocalCoord(y), lz);
    }
    
    public void setSkyLight(int lx, int y, int lz, int level){
        if(ChunkUtils.isOutOfBounds(lx, y, lz))
            return;
        
        // Находим по Y нужную секцию
        final int sectionIndex = ChunkUtils.getSectionIndex(y);
        LevelChunkSection section = getSection(sectionIndex);
        if(section == null)
            section = createSection(sectionIndex);
        
        section.setSkyLight(lx, ChunkUtils.getLocalCoord(y), lz, level);

        // if(getLevel() instanceof ServerLevel serverLevel)
        //     serverLevel.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(section));
    }

    public void setBlockLight(int lx, int y, int lz, int level){
        if(ChunkUtils.isOutOfBounds(lx, y, lz))
            return;

        // Находим по Y нужную секцию
        final int sectionIndex = ChunkUtils.getSectionIndex(y);
        LevelChunkSection section = getSection(sectionIndex);
        if(section == null)
            section = createSection(sectionIndex);

        section.setBlockLight(lx, ChunkUtils.getLocalCoord(y), lz, level);

        // if(getLevel() instanceof ServerLevel serverLevel)
        //     serverLevel.getServer().getPlayerList().broadcastPacket(new CBPacketLightUpdate(section));
    }


    public BiomeMap getBiomes(){
        return biomes;
    }


    public ChunkPos[] getNeighbors(){
        return neighbors.array();
    }

    public ChunkPos getNeighborPos(int neighborX, int neighborZ){
        return neighbors.getNeighborPos(neighborX, neighborZ);
    }

    public LevelChunk getNeighborChunk(int neighborX, int neighborZ){
        return neighbors.getNeighborChunk(neighborX, neighborZ);
    }
    
    
    @Override
    public boolean equals(Object object){
        if(object == this)
            return true;
        if(object == null || object.getClass() != getClass())
            return false;
        final LevelChunk chunk = (LevelChunk) object;
        return position.equals(chunk.position);
    }

    @Override
    public int hashCode(){
        return position.hashCode();
    }
    
}
