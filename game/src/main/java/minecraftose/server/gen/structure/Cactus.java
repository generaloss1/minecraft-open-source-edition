package minecraftose.server.gen.structure;

import jpize.math.util.Random;
import minecraftose.client.block.ClientBlocks;
import minecraftose.server.gen.pool.BlockPool;

public class Cactus{

    public static void generate(BlockPool pool, int x, int y, int z, Random random){
        final int cactusHeight = random.random(1, 3);
        for(int i = 0; i < cactusHeight; i++)
            pool.genBlock(x, y + i, z, ClientBlocks.CACTUS);
    }

}
