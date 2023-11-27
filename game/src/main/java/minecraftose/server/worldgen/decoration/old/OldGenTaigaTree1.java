package minecraftose.server.worldgen.decoration.old;

import minecraftose.client.block.ClientBlock;
import minecraftose.client.block.ClientBlocks;
import minecraftose.server.level.ServerLevel;

import java.util.Random;

public class OldGenTaigaTree1{

    public static boolean generate(ServerLevel world, Random random, int i, int j, int k){
        int length = random.nextInt(5) + 7;
        int i1 = length - random.nextInt(2) - 3;
        int j1 = length - i1;
        int k1 = 1 + random.nextInt(j1 + 1);

        if(j < 1 || j + length + 1 > 256)
            return false;

        final ClientBlock i2 = world.getBlock(i, j - 1, k);
        if(i2 != ClientBlocks.GRASS_BLOCK && i2 != ClientBlocks.DIRT && i2 != ClientBlocks.PODZOL || j >= 256 - length - 1)
            return false;

        for(int l1 = j; l1 <= j + 1 + length; l1++){
            final int j2 = (l1 - j < i1) ? 0 : k1;

            for(int l2 = i - j2; l2 <= i + j2; l2++){
                for(int k3 = k - j2; k3 <= k + j2; k3++){
                    ClientBlock j4 = world.getBlock(l2, l1, k3);
                    if(j4 != ClientBlocks.AIR && j4 != ClientBlocks.SPRUCE_LEAVES)
                        return false;
                }
            }
        }


        world.setBlock(i, j - 1, k, ClientBlocks.DIRT);
        int k2 = 0;
        for(int i3 = j + length; i3 >= j + i1; i3--){
            for(int l3 = i - k2; l3 <= i + k2; l3++){
                int k4 = l3 - i;
                for(int l4 = k - k2; l4 <= k + k2; l4++){
                    int i5 = l4 - k;
                    if((Math.abs(k4) != k2 || Math.abs(i5) != k2 || k2 <= 0) && !world.getBlock(l3, i3, l4).getState(0).isSolid())
                        world.setBlock(l3, i3, l4, ClientBlocks.SPRUCE_LEAVES);
                }

            }

            if(k2 >= 1 && i3 == j + i1 + 1){
                k2--;
                continue;
            }

            if(k2 < k1)
                k2++;
        }

        for(int j3 = 0; j3 < length - 1; j3++){
            ClientBlock i4 = world.getBlock(i, j + j3, k);
            if(i4 == ClientBlocks.AIR || i4 == ClientBlocks.SPRUCE_LEAVES)
                world.setBlock(i, j + j3, k, ClientBlocks.SPRUCE_LOG);
        }

        return true;
    }

}
