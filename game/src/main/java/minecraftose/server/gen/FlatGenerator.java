package minecraftose.server.gen;

import jpize.math.util.Random;
import minecraftose.client.block.ClientBlocks;
import minecraftose.server.chunk.ServerChunk;
import minecraftose.server.gen.pool.BlockPool;
import minecraftose.server.gen.structure.*;

import java.util.Objects;

import static minecraftose.main.chunk.ChunkUtils.SIZE;

public class FlatGenerator extends ChunkGenerator{

    private final Random random;

    public FlatGenerator(){
        this.random = new Random();
    }


    @Override
    public void generate(ServerChunk chunk){
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++)
                for(int y = 0; y < 5; y++){
                    if(y == 0)
                        chunk.setBlockFast(i, y, j, ClientBlocks.STONE);
                    else if(y == 4)
                        chunk.setBlockFast(i, y, j, ClientBlocks.GRASS_BLOCK);
                    else
                        chunk.setBlockFast(i, y, j, ClientBlocks.DIRT);
                }
    }

    @Override
    public void decorate(ServerChunk chunk){
        final BlockPool pool = chunk.getLevel().getBlockPool();
        pool.setGeneratingChunk(chunk);

        final int seed = chunk.getLevel().getConfiguration().getSeed();
        final int baseX = chunk.getPosition().x * SIZE;
        final int baseZ = chunk.getPosition().z * SIZE;

        random.setSeed(Objects.hash(seed, baseX, baseZ));

        int x = random.random(0, 15);
        int z = random.random(0, 15);

        if(random.randomBoolean(1F / (10 * 10)))
            House.generate(pool, x + baseX, 4, z + baseZ, random);
        else if(random.randomBoolean(1F / (10 * 10)))
            DesertPyramid.generate(pool, x + baseX, 3, z + baseZ, random);
        else if(random.randomBoolean(1F / (10 * 10)))
            MiniPyramid.generate(pool, x + baseX, 4, z + baseZ, random);
        else if(random.randomBoolean(1F / (10 * 10)))
            Tower.generate(pool, x + baseX, 4, z + baseZ, random);
        else if(random.randomBoolean(1F / (10 * 10)))
            Tree.generateOakTree(pool, x + baseX, 4, z + baseZ, random);
        else if(random.randomBoolean(1F / (10 * 10)))
            Tree.generateSpruceTree(pool, x + baseX, 4, z + baseZ, random);
        else if(random.randomBoolean(1F / (10 * 10)))
            Tree.generateBirchTree(pool, x + baseX, 4, z + baseZ, random);
        else if(random.randomBoolean(1F / (10 * 10)))
            Cactus.generate(pool, x + baseX, 5, z + baseZ, random);
    }


    private static FlatGenerator instance;
    
    public static FlatGenerator getInstance(){
        if(instance == null)
            instance = new FlatGenerator();
        
        return instance;
    }

    @Override
    public String getID(){
        return "flat";
    }

}