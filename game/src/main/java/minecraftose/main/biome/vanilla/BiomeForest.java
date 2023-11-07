package minecraftose.main.biome.vanilla;

import minecraftose.main.biome.BiomeProperties;

public class BiomeForest extends BiomeProperties{

    public BiomeForest(){
        grassColor.set3(0.45, 0.8, 0.35);
        waterColor.set3(0, 0.5, 0.75);
        hillsMul = 1.4F;
        erosionMul = 1.2F;
    }

}
