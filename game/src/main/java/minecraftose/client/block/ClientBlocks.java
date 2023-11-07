package minecraftose.client.block;

import minecraftose.client.Minecraft;
import minecraftose.client.block.air.Air;
import minecraftose.client.block.air.VoidAir;
import minecraftose.client.resources.GameResources;
import minecraftose.main.registry.Registry;
import minecraftose.client.block.vanilla.*;

import java.util.HashMap;
import java.util.Map;

public class ClientBlocks{

    public static final BlockClient VOID_AIR              = new VoidAir            (-1);
    public static final BlockClient AIR                   = new Air                ( 0);

    public static final BlockClient DIRT                  = new Dirt(1 );
    public static final BlockClient GRASS_BLOCK           = new GrassBlock         (2 );
    public static final BlockClient STONE                 = new Stone(3 );

    public static final BlockClient OAK_LOG               = new OakLog             (4 );
    public static final BlockClient OAK_LEAVES            = new OakLeaves          (5 );
    public static final BlockClient OAK_PLANKS            = new OakPlanks          (29);
    public static final BlockClient OAK_PLANKS_STAIRS     = new OakPlanksStairs    (30);
    public static final BlockClient SPRUCE_LOG            = new SpruceLog          (6 );
    public static final BlockClient SPRUCE_LEAVES         = new SpruceLeaves       (7 );
    public static final BlockClient BIRCH_LOG             = new BirchLog           (8 );
    public static final BlockClient BIRCH_LEAVES          = new BirchLeaves        (9 );

    public static final BlockClient CACTUS                = new Cactus             (10);
    public static final BlockClient GRASS                 = new Grass              (11);
    public static final BlockClient SAND                  = new Sand               (12);
    public static final BlockClient WATER                 = new Water              (13);
    public static final BlockClient ICE                   = new Ice                (14);

    public static final BlockClient SNOWY_GRASS_BLOCK     = new SnowyGrassBlock    (15);
    public static final BlockClient MYCELIUM              = new Mycelium           (16);
    public static final BlockClient PODZOL                = new Podzol             (17);

    public static final BlockClient GLASS                 = new Glass              (18);
    public static final BlockClient LAMP                  = new Lamp               (19);

    public static final BlockClient STONE_BRICKS          = new StoneBricks        (20);
    public static final BlockClient COBBLESTONE           = new Cobblestone        (21);
    public static final BlockClient MOSSY_COBBLESTONE     = new MossyCobblestone   (22);
    public static final BlockClient GRAVEL                = new Gravel             (23);

    public static final BlockClient POLISHED_DEEPSLATE    = new PolishedDeepslate  (24);
    public static final BlockClient DEEPSLATE_TILES       = new DeepslateTiles     (25);

    public static final BlockClient COARSE_DIRT           = new CoarseDirt         (26);
    public static final BlockClient CHISELED_STONE_BRICKS = new ChiseledStoneBricks(27);
    public static final BlockClient SANDSTONE             = new Sandstone          (28);



    private static final Map<Byte, BlockClient> blocks = new HashMap<>();

    public static void loadBlocks(Minecraft session){
        System.out.println("[Resources] Load Blocks");
        final GameResources resources = session.getRenderer().getSession().getResources();
        for(BlockClient block: blocks.values())
            block.load(resources);
    }

    private static void reg(BlockClient block){
        blocks.put(block.getID(), block);
    }

    public static BlockClient get(byte ID){
        return blocks.get(ID);
    }


    public static void register(){
        reg(VOID_AIR             );
        reg(AIR                  );
        reg(DIRT                 );
        reg(GRASS_BLOCK          );
        reg(STONE                );
        reg(OAK_LOG              );
        reg(OAK_LEAVES           );
        reg(OAK_PLANKS           );
        reg(OAK_PLANKS_STAIRS    );
        reg(SPRUCE_LOG           );
        reg(SPRUCE_LEAVES        );
        reg(BIRCH_LOG            );
        reg(BIRCH_LEAVES         );
        reg(CACTUS               );
        reg(GRASS                );
        reg(SAND                 );
        reg(WATER                );
        reg(ICE                  );
        reg(SNOWY_GRASS_BLOCK    );
        reg(MYCELIUM             );
        reg(PODZOL               );
        reg(GLASS                );
        reg(LAMP                 );
        reg(STONE_BRICKS         );
        reg(COBBLESTONE          );
        reg(MOSSY_COBBLESTONE    );
        reg(GRAVEL               );
        reg(POLISHED_DEEPSLATE   );
        reg(DEEPSLATE_TILES      );
        reg(COARSE_DIRT          );
        reg(CHISELED_STONE_BRICKS);
        reg(SANDSTONE            );
    }

}
