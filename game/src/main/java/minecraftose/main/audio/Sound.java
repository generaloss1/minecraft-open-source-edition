package minecraftose.main.audio;

import minecraftose.main.registry.Registry;

public class Sound{

    // Random
    public static final Sound CLICK    = new Sound("random/click");

    public static final Sound EXPLODE_1  = new Sound("random/explode1");
    public static final Sound EXPLODE_2  = new Sound("random/explode2");
    public static final Sound EXPLODE_3  = new Sound("random/explode3");
    public static final Sound EXPLODE_4  = new Sound("random/explode4");

    public static final Sound GLASS_1  = new Sound("random/glass1", 0.75F);
    public static final Sound GLASS_2  = new Sound("random/glass2", 0.75F);
    public static final Sound GLASS_3  = new Sound("random/glass3", 0.75F);

    public static final Sound LEVEL_UP = new Sound("random/levelup");

    // Hit
    public static final Sound FALL_BIG   = new Sound("damage/fallbig");
    public static final Sound FALL_SMALL = new Sound("damage/fallsmall");

    public static final Sound HIT_1 = new Sound("damage/hit1");
    public static final Sound HIT_2 = new Sound("damage/hit2");
    public static final Sound HIT_3 = new Sound("damage/hit3");

    // Dig
    public static final Sound DIG_CLOTH_1 = new Sound("dig/cloth1"); //:n
    public static final Sound DIG_CLOTH_2 = new Sound("dig/cloth2"); //:n
    public static final Sound DIG_CLOTH_3 = new Sound("dig/cloth3"); //:n
    public static final Sound DIG_CLOTH_4 = new Sound("dig/cloth4"); //:n

    public static final Sound DIG_GRASS_1 = new Sound("dig/grass1", 0.75F);
    public static final Sound DIG_GRASS_2 = new Sound("dig/grass2", 0.75F);
    public static final Sound DIG_GRASS_3 = new Sound("dig/grass3", 0.75F);
    public static final Sound DIG_GRASS_4 = new Sound("dig/grass4", 0.75F);

    public static final Sound DIG_GRAVEL_1 = new Sound("dig/gravel1"); //:n
    public static final Sound DIG_GRAVEL_2 = new Sound("dig/gravel2"); //:n
    public static final Sound DIG_GRAVEL_3 = new Sound("dig/gravel3"); //:n
    public static final Sound DIG_GRAVEL_4 = new Sound("dig/gravel4"); //:n

    public static final Sound DIG_SAND_1  = new Sound("dig/sand1",  0.75F);
    public static final Sound DIG_SAND_2  = new Sound("dig/sand2",  0.75F);
    public static final Sound DIG_SAND_3  = new Sound("dig/sand3",  0.75F);
    public static final Sound DIG_SAND_4  = new Sound("dig/sand4",  0.75F);

    public static final Sound DIG_SNOW_1 = new Sound("dig/snow1"); //:n
    public static final Sound DIG_SNOW_2 = new Sound("dig/snow2"); //:n
    public static final Sound DIG_SNOW_3 = new Sound("dig/snow3"); //:n
    public static final Sound DIG_SNOW_4 = new Sound("dig/snow4"); //:n

    public static final Sound DIG_STONE_1 = new Sound("dig/stone1", 0.75F);
    public static final Sound DIG_STONE_2 = new Sound("dig/stone2", 0.75F);
    public static final Sound DIG_STONE_3 = new Sound("dig/stone3", 0.75F);
    public static final Sound DIG_STONE_4 = new Sound("dig/stone4", 0.75F);

    public static final Sound DIG_WOOD_1  = new Sound("dig/wood1",  0.75F);
    public static final Sound DIG_WOOD_2  = new Sound("dig/wood2",  0.75F);
    public static final Sound DIG_WOOD_3  = new Sound("dig/wood3",  0.75F);
    public static final Sound DIG_WOOD_4  = new Sound("dig/wood4",  0.75F);

    // Step
    public static final Sound STEP_CLOTH_1 = new Sound("step/cloth1"); //:n
    public static final Sound STEP_CLOTH_2 = new Sound("step/cloth2"); //:n
    public static final Sound STEP_CLOTH_3 = new Sound("step/cloth3"); //:n
    public static final Sound STEP_CLOTH_4 = new Sound("step/cloth4"); //:n

    public static final Sound STEP_GRASS_1 = new Sound("step/grass1"); //:n
    public static final Sound STEP_GRASS_2 = new Sound("step/grass2"); //:n
    public static final Sound STEP_GRASS_3 = new Sound("step/grass3"); //:n
    public static final Sound STEP_GRASS_4 = new Sound("step/grass4"); //:n

    public static final Sound STEP_GRAVEL_1 = new Sound("step/gravel1"); //:n
    public static final Sound STEP_GRAVEL_2 = new Sound("step/gravel2"); //:n
    public static final Sound STEP_GRAVEL_3 = new Sound("step/gravel3"); //:n
    public static final Sound STEP_GRAVEL_4 = new Sound("step/gravel4"); //:n

    public static final Sound STEP_LADDER_1 = new Sound("step/ladder1"); //:n
    public static final Sound STEP_LADDER_2 = new Sound("step/ladder2"); //:n
    public static final Sound STEP_LADDER_3 = new Sound("step/ladder3"); //:n
    public static final Sound STEP_LADDER_4 = new Sound("step/ladder4"); //:n

    public static final Sound STEP_SAND_1 = new Sound("step/sand1"); //:n
    public static final Sound STEP_SAND_2 = new Sound("step/sand2"); //:n
    public static final Sound STEP_SAND_3 = new Sound("step/sand3"); //:n
    public static final Sound STEP_SAND_4 = new Sound("step/sand4"); //:n

    public static final Sound STEP_SNOW_1 = new Sound("step/snow1"); //:n
    public static final Sound STEP_SNOW_2 = new Sound("step/snow2"); //:n
    public static final Sound STEP_SNOW_3 = new Sound("step/snow3"); //:n
    public static final Sound STEP_SNOW_4 = new Sound("step/snow4"); //:n

    public static final Sound STEP_STONE_1 = new Sound("step/stone1"); //:n
    public static final Sound STEP_STONE_2 = new Sound("step/stone2"); //:n
    public static final Sound STEP_STONE_3 = new Sound("step/stone3"); //:n
    public static final Sound STEP_STONE_4 = new Sound("step/stone4"); //:n

    public static final Sound STEP_WOOD_1 = new Sound("step/wood1"); //:n
    public static final Sound STEP_WOOD_2 = new Sound("step/wood2"); //:n
    public static final Sound STEP_WOOD_3 = new Sound("step/wood3"); //:n
    public static final Sound STEP_WOOD_4 = new Sound("step/wood4"); //:n


    private static byte nextID;

    private final byte ID;
    private final String name;
    private final float pitch;

    Sound(String name, float pitch){
        this.ID = nextID++;
        this.name = name;
        this.pitch = pitch;
        Registry.sound.register(ID, this);
    }

    Sound(String name){
        this(name, 1F);
    }

    public int getID(){
        return ID;
    }

    public String name(){
        return name;
    }

    public float pitch(){
        return pitch;
    }

}
