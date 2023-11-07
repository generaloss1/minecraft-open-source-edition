package minecraftose.main.item;

import minecraftose.main.block.Blocks;

public class Items{

    public static final Item AIR              = new ItemBuilder(0, "").stack(0).register();

    public static final BlockItem COBBLESTONE = new ItemBuilder(1, "Cobblestone", Blocks.COBBLESTONE).register();
    public static final BlockItem GRASS_BLOCK = new ItemBuilder(2, "Grass Block", Blocks.GRASS_BLOCK).register();
    public static final BlockItem DIRT        = new ItemBuilder(3, "Dirt",        Blocks.DIRT       ).register();
    public static final BlockItem STONE       = new ItemBuilder(4, "Stone",       Blocks.STONE      ).register();
    public static final BlockItem LAMP        = new ItemBuilder(5, "Lamp",        Blocks.LAMP       ).register();
    public static final BlockItem SAND        = new ItemBuilder(6, "Sand",        Blocks.SAND       ).register();
    public static final BlockItem OAK_LOG     = new ItemBuilder(7, "Oak Log",     Blocks.OAK_LOG    ).register();

}
