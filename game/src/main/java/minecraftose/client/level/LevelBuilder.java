package minecraftose.client.level;

import minecraftose.client.chunk.ChunkC;
import minecraftose.client.chunk.builder.ChunkBuilder;
import minecraftose.main.Tickable;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class LevelBuilder implements Tickable{

    private final ChunkProviderC provider;
    private final ChunkBuilder builder;
    private final Deque<Long> buildDeque;

    public LevelBuilder(ChunkProviderC provider){
        this.provider = provider;
        this.builder = new ChunkBuilder(provider);
        this.buildDeque = new ConcurrentLinkedDeque<>();
    }

    public ChunkBuilder getChunkBuilder(){
        return builder;
    }


    public void buildChunk(ChunkC chunk, boolean important){
        if(chunk == null)
            return;

        if(important) buildDeque.addFirst(chunk.posPacked());
        else          buildDeque.addLast (chunk.posPacked());
    }

    @Override
    public void tick(){
        while(!buildDeque.isEmpty()){
            final long packedChunkPos = buildDeque.poll();
            if(provider.isNotSeen(packedChunkPos))
                continue;

            final ChunkC chunk = provider.getChunk(packedChunkPos);
            if(chunk != null)
                builder.build(chunk);
        }
    }

}
