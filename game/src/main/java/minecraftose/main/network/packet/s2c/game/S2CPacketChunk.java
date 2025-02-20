package minecraftose.main.network.packet.s2c.game;

import minecraftose.client.chunk.ChunkC;
import minecraftose.client.level.LevelC;
import minecraftose.client.network.ClientPacketHandler;
import minecraftose.main.biome.Biome;
import minecraftose.main.chunk.ChunkBase;
import minecraftose.main.chunk.ChunkSection;
import minecraftose.main.chunk.storage.ChunkPos;
import minecraftose.main.chunk.storage.Heightmap;
import minecraftose.main.chunk.storage.HeightmapType;
import minecraftose.main.chunk.storage.SectionPos;
import minecraftose.main.registry.Registry;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

import java.io.IOException;
import java.util.*;

public class S2CPacketChunk extends NetPacket<ClientPacketHandler>{

    private ChunkPos position;
    private ChunkSection[] sections;
    private int highestSectionIndex;
    private Collection<Heightmap> heightmapsToWrite;
    private Map<HeightmapType, short[]> readHeightmaps;
    private Biome[] biomes;
    
    public S2CPacketChunk(){    
        readHeightmaps = new HashMap<>();
    }
    
    public S2CPacketChunk(ChunkBase chunk){    
        position = chunk.pos();
        sections = chunk.getSections();
        highestSectionIndex = chunk.getHighestSectionIndex();
        heightmapsToWrite = chunk.getHeightmaps();
        biomes = chunk.getBiomes().getValues();
    }
    
    
    public ChunkC getChunk(LevelC level){
        final ChunkC chunk = new ChunkC(level, position);
        chunk.setSections(sections, highestSectionIndex);
        chunk.setHeightmaps(readHeightmaps);
        chunk.updateMaxY();
        chunk.getBiomes().setValues(biomes);
        return chunk;
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        // Chunk position
        stream.writeInt(position.x);
        stream.writeInt(position.z);
        
        // Sections header
        long sectionsNum = Arrays.stream(sections).filter(Objects::nonNull).count();
        stream.writeByte((byte) sectionsNum);
        stream.writeByte((byte) highestSectionIndex);
        
        // Sections data
        for(int i = 0; i < sections.length; i++)
            if(sections[i] != null)
                writeSection(stream, i);
        
        // Heightmaps
        stream.writeByte((byte) heightmapsToWrite.size());
        
        for(Heightmap heightmap: heightmapsToWrite)
            writeHeightmap(stream, heightmap);

        // Biomes
        for(Biome biome: biomes)
            stream.writeByte(biome.ID);
    }
    
    private void writeSection(ExtDataOutputStream stream, int sectionIndex) throws IOException{
        final ChunkSection section = sections[sectionIndex];
        
        stream.writeByte(sectionIndex); // index
        stream.writeShort(section.blocksNum); // blocks num
        stream.writeShortArray(section.blocks); // blocks data
        stream.write(section.light); // light data
    }
    
    private void writeHeightmap(ExtDataOutputStream stream, Heightmap heightmap) throws IOException{
        stream.writeByte(heightmap.getType().ordinal());
        stream.writeShortArray(heightmap.getValues());
    }
    
    
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        // Chunk position
        position = new ChunkPos(
            stream.readInt(),
            stream.readInt()
        );
        
        // Sections header
        final byte sectionsNum = stream.readByte();
        highestSectionIndex = stream.readByte();
        
        if(sectionsNum == 0)
            return;
        sections = new ChunkSection[16];
        
        // Sections data
        for(int i = 0; i < sectionsNum; i++)
            readSection(stream);
        
        // Heightmaps
        final byte heightmapsNum = stream.readByte();
        for(int i = 0; i < heightmapsNum; i++)
            readHeightmap(stream);

        // Biomes
        biomes = new Biome[ChunkBase.AREA];
        for(int i = 0; i < biomes.length; i++)
            biomes[i] = Registry.biome.get(stream.readByte());
    }
    
    private void readSection(ExtDataInputStream stream) throws IOException{
        final byte sectionIndex = stream.readByte(); // index
        final short blocksNum = stream.readShort(); // blocks num
        final short[] blocks = stream.readShortArray(); // blocks data
        final byte[] light = stream.readNBytes(ChunkBase.VOLUME); // light data
        
        final ChunkSection section = new ChunkSection(
            new SectionPos(position.x, sectionIndex, position.z),
            blocks,
            light
        );
        section.blocksNum = blocksNum;
        sections[sectionIndex] = section;
    }
    
    private void readHeightmap(ExtDataInputStream stream) throws IOException{
        final HeightmapType type = HeightmapType.values()[stream.readByte()];
        final short[] values = stream.readShortArray();
        
        readHeightmaps.put(type, values);
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.chunk(this);
    }
    
}
