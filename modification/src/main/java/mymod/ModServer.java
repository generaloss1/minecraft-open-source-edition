package mymod;

import minecraftose.main.modification.api.DedicatedServerModInitializer;

public class ModServer implements DedicatedServerModInitializer{
    
    @Override
    public void onInitializeServer(){
        System.out.println("[Voxel Game Mod]: server point initialized");
    }
    
}
