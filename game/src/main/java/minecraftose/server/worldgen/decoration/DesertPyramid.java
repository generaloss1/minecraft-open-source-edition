package minecraftose.server.worldgen.decoration;

import jpize.math.util.JpizeRandom;
import minecraftose.main.level.structure.Structure;
import minecraftose.server.level.ServerLevel;

public class DesertPyramid extends Structure{

    public static void generate(ServerLevel level, int x, int y, int z, JpizeRandom random){
        loadTo(level, "desert_pyramid", x, y, z);
    }

}
