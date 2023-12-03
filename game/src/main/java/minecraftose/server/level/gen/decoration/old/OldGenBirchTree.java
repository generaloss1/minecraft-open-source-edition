package minecraftose.server.level.gen.decoration.old;

import minecraftose.client.block.ClientBlock;
import minecraftose.client.block.ClientBlocks;
import minecraftose.server.level.LevelS;

import java.util.Random;

public class OldGenBirchTree{

    public static boolean generate(LevelS level, Random random, int x, int y, int z){
        final int length = random.nextInt(3) + 4;

        // Check Y-world-bounds
        if(y < 1 || y + length + 1 > 256)
            return false;

        // Check base block
        final ClientBlock baseBlock = level.getBlock(x, y - 1, z);
        if(baseBlock != ClientBlocks.GRASS_BLOCK && baseBlock != ClientBlocks.DIRT || y >= 256 - length - 1)
            return false;

        // Check generation possibility
        for(int cy = y; cy <= y + 1 + length; cy++){
            int radius = 1;
            if(cy == y)
                radius = 0;
            else if(cy >= y + length - 1)
                radius = 2;

            for(int cx = x - radius; cx <= x + radius; cx++){
                for(int cz = z - radius; cz <= z + radius; cz++){
                    final ClientBlock block = level.getBlock(cx, cy, cz);
                    if(block != ClientBlocks.AIR && block != ClientBlocks.OAK_LEAVES && block != ClientBlocks.BIRCH_LEAVES)
                        return false;
                }
            }
        }

        // Set base block
        level.setBlock(x, y - 1, z, ClientBlocks.DIRT);

        // Fill leaves
        for(int fy = y + (length - 3); fy < y + (length + 1); fy++){
            final int y2 = fy - (y + length); // [-3; 0]
            final int y3 = 1 - y2 / 2; // [1; 2]

            for(int fx = x - y3; fx <= x + y3; fx++){
                final int lx = Math.abs(fx - x);

                for(int fz = z - y3; fz <= z + y3; fz++){
                    final int lz = Math.abs(fz - z);

                    if((lx != y3 || lz != y3 || random.nextInt(2) != 0 && y2 != 0) && !level.getBlock(fx, fy, fz).getState(0).isSolid())
                        level.setBlock(fx, fy, fz, ClientBlocks.BIRCH_LEAVES);
                }
            }
        }

        // Fill trunk
        for(int l1 = 0; l1 < length; l1++){
            final ClientBlock block = level.getBlock(x, y + l1, z);
            if(block == ClientBlocks.AIR || block == ClientBlocks.OAK_LEAVES || block == ClientBlocks.BIRCH_LEAVES)
                level.setBlock(x, y + l1, z, ClientBlocks.BIRCH_LOG);
        }

        return true;
    }

}
