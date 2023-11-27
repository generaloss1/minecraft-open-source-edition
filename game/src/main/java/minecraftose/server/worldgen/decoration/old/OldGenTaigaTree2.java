package minecraftose.server.worldgen.decoration.old;

import minecraftose.client.block.ClientBlock;
import minecraftose.client.block.ClientBlocks;
import minecraftose.server.level.ServerLevel;

import java.util.Random;

public class OldGenTaigaTree2{

    public static boolean generate(ServerLevel world, Random random, int i, int j, int k){
        int lenght = random.nextInt(4) + 8; // 4 6 => 6 8
        int i1 = 1 + random.nextInt(2);
        int j1 = lenght - i1;
        int k1 = 2 + random.nextInt(2);

        if(j < 1 || j + lenght + 1 > 128)
            return false;

        final ClientBlock i2 = world.getBlock(i, j - 1, k);
        if(i2 != ClientBlocks.GRASS_BLOCK && i2 != ClientBlocks.DIRT && i2 != ClientBlocks.PODZOL || j >= 128 - lenght - 1)
            return false;

        for(int l1 = j; l1 <= j + 1 + lenght; l1++){
            final int j2 = (l1 - j < i1) ? 0 : k1;

            for(int l2 = i - j2; l2 <= i + j2; l2++){
                for(int j3 = k - j2; j3 <= k + j2; j3++){
                    ClientBlock k3 = world.getBlock(l2, l1, j3);
                    if(k3 != ClientBlocks.AIR && k3 != ClientBlocks.SPRUCE_LEAVES)
                        return false;
                }
            }
        }

        world.setBlock(i, j - 1, k, ClientBlocks.DIRT);
        int k2 = random.nextInt(2);
        int i3 = 1;
        boolean flag1 = false;
        for(int l3 = 0; l3 <= j1; l3++){
            int j4 = (j + lenght) - l3;
            for(int l4 = i - k2; l4 <= i + k2; l4++){
                int j5 = l4 - i;
                for(int k5 = k - k2; k5 <= k + k2; k5++){
                    int l5 = k5 - k;
                    if((Math.abs(j5) != k2 || Math.abs(l5) != k2 || k2 <= 0) && !world.getBlock(l4, j4, k5)
                            .getState(0)
                            .isSolid()){
                        world.setBlock(l4, j4, k5, ClientBlocks.SPRUCE_LEAVES);
                    }
                }

            }

            if(k2 >= i3){
                k2 = ((flag1) ? 1 : 0);
                flag1 = true;
                if(++i3 > k1)
                    i3 = k1;
            }else
                k2++;
        }

        final int i4 = random.nextInt(3);
        for(int k4 = 0; k4 < lenght - i4; k4++){
            ClientBlock i5 = world.getBlock(i, j + k4, k);
            if(i5 == ClientBlocks.AIR || i5 == ClientBlocks.SPRUCE_LEAVES)
                world.setBlock(i, j + k4, k, ClientBlocks.SPRUCE_LOG);
        }

        return true;
    }

}
