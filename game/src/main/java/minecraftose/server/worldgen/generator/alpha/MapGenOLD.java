package minecraftose.server.worldgen.generator.alpha;

import minecraftose.client.block.ClientBlock;
import minecraftose.server.level.ServerLevel;

import java.util.Random;

public class MapGenOLD{

    protected int range;
    protected Random rand;
    protected ServerLevel worldObj;

    public MapGenOLD(){
        range = 8;
        rand = new Random();
    }

    public void generate(ServerLevel world, int i, int j, ClientBlock[] blocks){
        int k = range;
        worldObj = world;
        rand.setSeed(worldObj.getConfiguration().getSeed());
        long l = (rand.nextLong() / 2L) * 2L + 1L;
        long l1 = (rand.nextLong() / 2L) * 2L + 1L;
        for(int i1 = i - k; i1 <= i + k; i1++){
            for(int j1 = j - k; j1 <= j + k; j1++){
                rand.setSeed((long) i1 * l + (long) j1 * l1 ^ worldObj.getConfiguration().getSeed());
                recursiveGenerate(worldObj, i1, j1, i, j, blocks);
            }
        }
    }

    public void populate(ServerLevel world, int i, int j){
        int k = range;
        worldObj = world;
        rand.setSeed(worldObj.getConfiguration().getSeed());
        long l = (rand.nextLong() / 2L) * 2L + 1L;
        long l1 = (rand.nextLong() / 2L) * 2L + 1L;
        for(int i1 = i - k; i1 <= i + k; i1++){
            for(int j1 = j - k; j1 <= j + k; j1++){
                rand.setSeed((long) i1 * l + (long) j1 * l1 ^ worldObj.getConfiguration().getSeed());
                double[] found = find(world, i1, j1);
                if(found != null){
                    populator(worldObj, found[0], found[1], found[2], i, j);
                }
            }
        }
    }

    protected void recursiveGenerate(ServerLevel world, int i, int j, int k, int l, ClientBlock[] blocks){ }

    protected void populator(ServerLevel world, double centerX, double centerY, double centerZ, int chunkX, int chunkY){ }

    public double[] find(ServerLevel world, int bx, int by){
        return null;
    }

}
