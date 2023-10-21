package minecraftose.server.gen.structure;

import jpize.math.util.Random;
import minecraftose.main.level.structure.Structure;
import minecraftose.server.gen.pool.BlockPool;

public class House extends Structure{

    public static void generate(BlockPool pool, int x, int y, int z, Random random){
        loadTo(pool, "house", x, y, z);
    }

}
