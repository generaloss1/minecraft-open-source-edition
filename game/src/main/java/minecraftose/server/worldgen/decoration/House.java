package minecraftose.server.worldgen.decoration;

import jpize.math.util.JpizeRandom;
import minecraftose.main.level.structure.Structure;
import minecraftose.server.worldgen.pool.BlockPool;

public class House extends Structure{

    public static void generate(BlockPool pool, int x, int y, int z, JpizeRandom random){
        loadTo(pool, "house", x, y, z);
    }

}
