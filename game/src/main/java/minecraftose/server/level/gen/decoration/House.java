package minecraftose.server.level.gen.decoration;

import jpize.math.util.JpizeRandom;
import minecraftose.main.level.structure.Structure;
import minecraftose.server.level.LevelS;

public class House extends Structure{

    public static void generate(LevelS level, int x, int y, int z, JpizeRandom random){
        loadTo(level, "house", x, y, z);
    }

}
