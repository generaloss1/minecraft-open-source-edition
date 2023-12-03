package minecraftose.server.level.gen.decoration;

import jpize.math.util.JpizeRandom;
import minecraftose.main.level.structure.Structure;
import minecraftose.server.level.LevelS;

public class MiniPyramid extends Structure{

    public static void generate(LevelS level, int x, int y, int z, JpizeRandom random){
        loadTo(level, "mini_pyramid", x, y, z);
    }

}
