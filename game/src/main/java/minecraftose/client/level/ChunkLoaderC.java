package minecraftose.client.level;

import minecraftose.main.Tickable;

public class ChunkLoaderC implements Tickable{

    private final ChunkProviderC provider;

    public ChunkLoaderC(ChunkProviderC provider){
        this.provider = provider;
    }

    @Override
    public void tick(){
        provider.getChunks().getChunks().removeIf(chunk -> provider.isNotSeen(chunk.posPacked()));
    }

}
