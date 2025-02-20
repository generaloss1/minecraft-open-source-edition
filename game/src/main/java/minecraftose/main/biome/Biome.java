package minecraftose.main.biome;

import jpize.util.color.Color;
import minecraftose.client.block.ClientBlock;

import static minecraftose.client.block.ClientBlocks.*;

public class Biome{

    public static final Biome VOID            = new BiomeBuilder(-1, "Void"           ) .blocks(AIR,                AIR) .colors(0.45F, 0.8F , 0.35F,  0, 0.5F, 0.75F) .build();
    public static final Biome SEA             = new BiomeBuilder( 0, "Sea"            ) .blocks(DIRT,              DIRT) .colors(0.4F , 0.7F , 0.4F ,  0, 0.5F, 1F   ) .build();
    public static final Biome ICE_SEA         = new BiomeBuilder( 1, "Ice Sea"        ) .blocks(DIRT,              DIRT) .colors(0.4F , 0.7F , 0.4F ,  0, 0.4F, 0.9F ) .build();
    public static final Biome RIVER           = new BiomeBuilder( 2, "River"          ) .blocks(DIRT,              DIRT) .colors(0.4F , 0.7F , 0.4F ,  0, 0.5F, 1F   ) .build();
    public static final Biome ICE_RIVER       = new BiomeBuilder( 3, "Ice River"      ) .blocks(DIRT,              DIRT) .colors(0.4F , 0.7F , 0.4F ,  0, 0.4F, 0.9F ) .build();
    public static final Biome DESERT          = new BiomeBuilder( 4, "Desert"         ) .blocks(SAND                   ) .colors(0.7F , 0.8F , 0.1F ,  1, 1F  , 0.7F ) .build();
    public static final Biome BEACH           = new BiomeBuilder( 5, "Beach"          ) .blocks(SAND                   ) .colors(0.7F , 0.8F , 0.1F ,  0, 0.5F, 0.75F) .build();
    public static final Biome SNOWY_BEACH     = new BiomeBuilder( 6, "Snowy Beach"    ) .blocks(SAND                   ) .colors(0.7F , 0.8F , 0.1F ,  0, 0.5F, 0.75F) .build();
    public static final Biome FOREST          = new BiomeBuilder( 7, "Forest"         ) .blocks(GRASS_BLOCK,       DIRT) .colors(0.45F, 0.8F , 0.35F,  0, 0.5F, 0.75F) .build();
    public static final Biome SAVANNA         = new BiomeBuilder( 8, "Savanna"        ) .blocks(GRASS_BLOCK,       DIRT) .colors(0.5F , 0.8F , 0.1F ,  0, 0.5F, 0.75F) .build();
    public static final Biome TAIGA           = new BiomeBuilder( 9, "Taiga"          ) .blocks(PODZOL,            DIRT) .colors(0.3F , 0.7F , 0.45F,  0, 0.4F, 0.8F ) .build();
    public static final Biome SNOWY_TAIGA     = new BiomeBuilder(10, "Snowy Taiga"    ) .blocks(SNOWY_GRASS_BLOCK, DIRT) .colors(0.14F, 0.55F, 0.28F,  0, 0.4F, 0.9F ) .build();
    public static final Biome WINDSWEPT_HILLS = new BiomeBuilder(11, "Windswept Hills") .blocks(GRASS_BLOCK,       DIRT) .colors(0.7F , 0.6F , 0.1F ,  0, 0.5F, 0.75F) .build();


    public final byte ID;
    public final String name;
    public final Color grassColor;
    public final Color waterColor;
    public final ClientBlock topBlock;
    public final ClientBlock fillerBlock;

    public Biome(byte ID, String name, Color grass, Color water, ClientBlock top, ClientBlock filler){
        this.ID = ID;
        this.name = name;
        this.grassColor = grass;
        this.waterColor = water;
        this.topBlock = top;
        this.fillerBlock = filler;
    }

}
