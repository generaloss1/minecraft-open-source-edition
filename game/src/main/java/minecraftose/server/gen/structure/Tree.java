package minecraftose.server.gen.structure;

import jpize.math.util.Random;
import minecraftose.client.block.ClientBlocks;
import minecraftose.main.level.structure.Structure;
import minecraftose.server.gen.pool.BlockPool;

public class Tree{

    public static void generateSpruceTree(BlockPool pool, int x, int y, int z, Random random){
        final int logHeight = random.random(10, 16);
        final int peak = y + logHeight;
        final int leavesHeight = random.random(2, 5);

        for(int ly = 0; ly < logHeight; ly++){
            final int height = y + ly;

            if(ly >= leavesHeight){
                final float leavesNorY = (float) ly / (logHeight - leavesHeight);

                final float radius;
                if(leavesNorY < 0.3)
                    radius = random.random(2.2F, 3.8F);
                else if(leavesNorY < 0.7)
                    radius = random.random(0.8F, 3.2F);
                else
                    radius = random.random(0.8F, 2.2F);

                Structure.circleFilledXZ(pool, x, height, z, radius, ClientBlocks.SPRUCE_LEAVES);
            }

            pool.genBlock(x, height, z, ClientBlocks.SPRUCE_LOG);
        }

        pool.genBlock(x, peak, z, ClientBlocks.SPRUCE_LEAVES);
    }


    public static void generateOakTree(BlockPool pool, int x, int y, int z, Random random){
        final int logHeight = random.random(4, 9);
        final int peak = y + logHeight;

        // Верхний 1
        pool.genBlock(x  , peak  , z  , ClientBlocks.OAK_LEAVES);

        // Окружающие ствол дерева 1х4
        pool.genBlock(x-1, peak  , z  , ClientBlocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-1, z  , ClientBlocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-2, z  , ClientBlocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-3, z  , ClientBlocks.OAK_LEAVES);

        pool.genBlock(x+1, peak  , z  , ClientBlocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-1, z  , ClientBlocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-2, z  , ClientBlocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-3, z  , ClientBlocks.OAK_LEAVES);

        pool.genBlock(x  , peak  , z-1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x  , peak-1, z-1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x  , peak-2, z-1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x  , peak-3, z-1, ClientBlocks.OAK_LEAVES);

        pool.genBlock(x  , peak  , z+1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x  , peak-1, z+1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x  , peak-2, z+1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x  , peak-3, z+1, ClientBlocks.OAK_LEAVES);

        // Другие 1х3
        pool.genBlock(x-1, peak-1, z-1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-2, z-1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-3, z-1, ClientBlocks.OAK_LEAVES);

        pool.genBlock(x-1, peak-1, z+1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-2, z+1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-3, z+1, ClientBlocks.OAK_LEAVES);

        pool.genBlock(x+1, peak-1, z-1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-2, z-1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-3, z-1, ClientBlocks.OAK_LEAVES);

        pool.genBlock(x+1, peak-1, z+1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-2, z+1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-3, z+1, ClientBlocks.OAK_LEAVES);

        // Другие по краям 3х2
        pool.genBlock(x-2, peak-2, z-1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x-2, peak-2, z  , ClientBlocks.OAK_LEAVES);
        pool.genBlock(x-2, peak-2, z+1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x-2, peak-3, z-1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x-2, peak-3, z  , ClientBlocks.OAK_LEAVES);
        pool.genBlock(x-2, peak-3, z+1, ClientBlocks.OAK_LEAVES);

        pool.genBlock(x+2, peak-2, z-1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x+2, peak-2, z  , ClientBlocks.OAK_LEAVES);
        pool.genBlock(x+2, peak-2, z+1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x+2, peak-3, z-1, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x+2, peak-3, z  , ClientBlocks.OAK_LEAVES);
        pool.genBlock(x+2, peak-3, z+1, ClientBlocks.OAK_LEAVES);

        pool.genBlock(x-1, peak-2, z-2, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x  , peak-2, z-2, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-2, z-2, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-3, z-2, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x  , peak-3, z-2, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-3, z-2, ClientBlocks.OAK_LEAVES);

        pool.genBlock(x-1, peak-2, z+2, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x  , peak-2, z+2, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-2, z+2, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-3, z+2, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x  , peak-3, z+2, ClientBlocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-3, z+2, ClientBlocks.OAK_LEAVES);

        for(int ly = 0; ly < logHeight; ly++)
            pool.genBlock(x, y + ly, z, ClientBlocks.OAK_LOG);
    }


    public static void generateBirchTree(BlockPool pool, int x, int y, int z, Random random){
        final int logHeight = random.random(5, 10);
        final int peak = y + logHeight;

        // Верхний 1
        pool.genBlock(x  , peak  , z  , ClientBlocks.BIRCH_LEAVES);

        // Окружающие ствол дерева 1х4
        pool.genBlock(x-1, peak  , z  , ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-1, z  , ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-2, z  , ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-3, z  , ClientBlocks.BIRCH_LEAVES);

        pool.genBlock(x+1, peak  , z  , ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-1, z  , ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-2, z  , ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-3, z  , ClientBlocks.BIRCH_LEAVES);

        pool.genBlock(x  , peak  , z-1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-1, z-1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-2, z-1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-3, z-1, ClientBlocks.BIRCH_LEAVES);

        pool.genBlock(x  , peak  , z+1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-1, z+1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-2, z+1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-3, z+1, ClientBlocks.BIRCH_LEAVES);

        // Другие 1х3
        pool.genBlock(x-1, peak-1, z-1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-2, z-1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-3, z-1, ClientBlocks.BIRCH_LEAVES);

        pool.genBlock(x-1, peak-1, z+1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-2, z+1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-3, z+1, ClientBlocks.BIRCH_LEAVES);

        pool.genBlock(x+1, peak-1, z-1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-2, z-1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-3, z-1, ClientBlocks.BIRCH_LEAVES);

        pool.genBlock(x+1, peak-1, z+1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-2, z+1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-3, z+1, ClientBlocks.BIRCH_LEAVES);

        // Другие по краям 3х2
        pool.genBlock(x-2, peak-2, z-1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x-2, peak-2, z  , ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x-2, peak-2, z+1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x-2, peak-3, z-1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x-2, peak-3, z  , ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x-2, peak-3, z+1, ClientBlocks.BIRCH_LEAVES);

        pool.genBlock(x+2, peak-2, z-1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x+2, peak-2, z  , ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x+2, peak-2, z+1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x+2, peak-3, z-1, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x+2, peak-3, z  , ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x+2, peak-3, z+1, ClientBlocks.BIRCH_LEAVES);

        pool.genBlock(x-1, peak-2, z-2, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-2, z-2, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-2, z-2, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-3, z-2, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-3, z-2, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-3, z-2, ClientBlocks.BIRCH_LEAVES);

        pool.genBlock(x-1, peak-2, z+2, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-2, z+2, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-2, z+2, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-3, z+2, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-3, z+2, ClientBlocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-3, z+2, ClientBlocks.BIRCH_LEAVES);

        for(int ly = 0; ly < logHeight; ly++)
            pool.genBlock(x, y + ly, z, ClientBlocks.BIRCH_LOG);
    }

}
