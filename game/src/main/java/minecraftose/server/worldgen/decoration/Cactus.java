package minecraftose.server.worldgen.decoration;

import jpize.math.util.JpizeRandom;
import minecraftose.client.block.ClientBlocks;
import minecraftose.server.level.ServerLevel;

public class Cactus{

    public static void generate(ServerLevel level, int x, int y, int z, JpizeRandom random){
        final int cactusHeight = random.random(1, 3);
        for(int i = 0; i < cactusHeight; i++)
            level.genBlock(x, y + i, z, ClientBlocks.CACTUS);
    }

}
