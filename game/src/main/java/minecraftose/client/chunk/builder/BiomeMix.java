package minecraftose.client.chunk.builder;

import jpize.util.color.Color;
import minecraftose.main.biome.Biome;

public class BiomeMix{

    public final Biome biome0, biome1, biome2, biome3;

    public BiomeMix(Biome biome0, Biome biome1, Biome biome2, Biome biome3){
        this.biome0 = biome0;
        this.biome1 = biome1;
        this.biome2 = biome2;
        this.biome3 = biome3;
    }

    public Color getGrassColor(){
        final Color color = new Color(0, 0, 0);
        int n = 0;
        if(biome0 != null){
            color.addRGB(biome0.grassColor);
            n++;
        }
        if(biome1 != null){
            color.addRGB(biome1.grassColor);
            n++;
        }
        if(biome2 != null){
            color.addRGB(biome2.grassColor);
            n++;
        }
        if(biome3 != null){
            color.addRGB(biome3.grassColor);
            n++;
        }
        return color.divRGB(n);
    }

    public Color getWaterColor(){
        final Color color = new Color(0, 0, 0);
        int n = 0;
        if(biome0 != null){
            color.addRGB(biome0.waterColor);
            n++;
        }
        if(biome1 != null){
            color.addRGB(biome1.waterColor);
            n++;
        }
        if(biome2 != null){
            color.addRGB(biome2.waterColor);
            n++;
        }
        if(biome3 != null){
            color.addRGB(biome3.waterColor);
            n++;
        }
        return color.divRGB(n);
    }

}
