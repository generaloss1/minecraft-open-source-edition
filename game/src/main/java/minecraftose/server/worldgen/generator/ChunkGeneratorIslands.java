package minecraftose.server.worldgen.generator;

import jpize.math.Maths;
import jpize.math.function.FastNoiseLite;
import minecraftose.client.block.ClientBlocks;
import minecraftose.main.chunk.storage.HeightmapType;
import minecraftose.server.chunk.ServerChunk;
import minecraftose.server.worldgen.ChunkGenerator;

import static minecraftose.main.chunk.ChunkUtils.HEIGHT;
import static minecraftose.main.chunk.ChunkUtils.SIZE;

public class ChunkGeneratorIslands extends ChunkGenerator{

    public static ChunkGeneratorIslands INSTANCE = new ChunkGeneratorIslands();
    
    private final FastNoiseLite continentalnessNoise, erosionNoise, peaksValleysNoise, temperatureNoise, humidityNoise;
    private final FastNoiseLite noiseLight = new FastNoiseLite();
    
    private ChunkGeneratorIslands(){
        continentalnessNoise = new FastNoiseLite();
        erosionNoise = new FastNoiseLite();
        peaksValleysNoise = new FastNoiseLite();
        temperatureNoise = new FastNoiseLite();
        humidityNoise = new FastNoiseLite();
        
        continentalnessNoise.setFrequency(0.002F);
        continentalnessNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        continentalnessNoise.setFractalOctaves(7);
        
        erosionNoise.setFrequency(0.002F);
        erosionNoise.setFractalType(FastNoiseLite.FractalType.FBm);
        erosionNoise.setFractalOctaves(5);
        
        noiseLight.setFrequency(0.03F);
    }
    
    @Override
    public void generate(ServerChunk chunk){
        final int baseX = SIZE * chunk.getPosition().x;
        final int baseZ = SIZE * chunk.getPosition().z;
        
        final int seed = chunk.getLevel().getConfiguration().getSeed();
        
        continentalnessNoise.setSeed(seed);
        erosionNoise.setSeed(seed);
        peaksValleysNoise.setSeed(seed);
        temperatureNoise.setSeed(seed);
        humidityNoise.setSeed(seed);
        
        // Stopwatch timer = new Stopwatch().start();
        for(int lx = 0; lx < SIZE; lx++){
            final int x = lx + baseX;
            for(int lz = 0; lz < SIZE; lz++){
                final int z = lz + baseZ;
                
                final float erosion = (erosionNoise.getNoise(x, z) + 1) * 0.5F;
                
                final float density = 0.1F / erosion;
                int height = Maths.round(continentalnessNoise.getNoise(x, z) * 16 + 128);
                for(int y = height; y < HEIGHT; y++){
                    
                    float continentalness3D = (continentalnessNoise.getNoise(x, y, z) + 1) * 0.5F;
                    if(continentalness3D < ((float) y / (HEIGHT - height)) * density)
                        chunk.setBlockFast(lx, y, lz, ClientBlocks.STONE);
                }
            }
        }
        
        chunk.getHeightMap(HeightmapType.SURFACE).update();
        
        for(int lx = 0; lx < SIZE; lx++){
            for(int lz = 0; lz < SIZE; lz++){
                
                int height = chunk.getHeightMap(HeightmapType.SURFACE).getHeight(lx, lz);
                if(height > 60)
                    chunk.setBlockFast(lx, height, lz, ClientBlocks.GRASS_BLOCK);
            }
        }

        // System.out.println("Gen: " + timer.getMillis());
    }

    @Override
    public byte getID(){
        return 2;
    }
    
}