package minecraftose.server.gen.structure;

import jpize.math.util.Random;
import minecraftose.main.level.structure.Structure;
import minecraftose.server.gen.pool.BlockPool;

public class DesertPyramid extends Structure{

    public static void generate(BlockPool pool, int x, int y, int z, Random random){
        loadTo(pool, "desert_pyramid", x, y, z);
    }

}
