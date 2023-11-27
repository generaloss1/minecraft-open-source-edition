package minecraftose.server.worldgen.decoration;

import jpize.math.util.JpizeRandom;
import minecraftose.main.level.structure.Structure;
import minecraftose.server.worldgen.pool.BlockPool;

public class MiniPyramid extends Structure{

    public static void generate(BlockPool pool, int x, int y, int z, JpizeRandom random){
        loadTo(pool, "mini_pyramid", x, y, z);
    }

}
