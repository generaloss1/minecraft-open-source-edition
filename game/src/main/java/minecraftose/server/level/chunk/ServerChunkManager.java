package minecraftose.server.level.chunk;

import jpize.graphics.util.batch.TextureBatch;
import jpize.math.vecmath.vector.Vec2f;
import minecraftose.main.chunk.storage.ChunkPos;
import minecraftose.main.chunk.storage.HeightmapType;
import minecraftose.main.entity.Entity;
import minecraftose.main.level.ChunkManager;
import minecraftose.main.network.packet.s2c.game.S2CPacketChunk;
import minecraftose.server.chunk.ServerChunk;
import minecraftose.server.worldgen.ChunkGenerator;
import minecraftose.server.level.ServerLevel;
import minecraftose.server.player.ServerPlayer;
import jpize.util.time.FpsCounter;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Predicate;

import static minecraftose.main.chunk.ChunkUtils.getChunkPos;
import static minecraftose.main.level.ChunkManagerUtils.distToChunk;

public class ServerChunkManager extends ChunkManager{
    
    private final ServerLevel level;
    
    private final Map<ChunkPos, ServerPlayer> requestedChunks;
    private final CopyOnWriteArrayList<ChunkPos> newFrontiers, frontiers;
    private final Map<ChunkPos, ServerChunk> generatedChunks, decoratedChunks, illuminatedChunks, loadedChunks;
    private final Queue<ChunkPos> loadQueue;

    public final FpsCounter tps;
    
    private final ExecutorService executorService;
    
    public ServerChunkManager(ServerLevel level){
        this.level = level;
        
        this.requestedChunks = new HashMap<>();
        this.frontiers = new CopyOnWriteArrayList<>();
        this.newFrontiers = new CopyOnWriteArrayList<>();
        this.loadQueue = new LinkedBlockingQueue<>();
        // gen steps
        this.generatedChunks = new ConcurrentHashMap<>();
        this.decoratedChunks = new ConcurrentHashMap<>();
        this.illuminatedChunks = new ConcurrentHashMap<>();
        this.loadedChunks = new ConcurrentHashMap<>();

        this.tps = new FpsCounter();

        this.executorService = Executors.newSingleThreadExecutor(runnable->{
            final Thread thread = new Thread(runnable, "ServerChunkManager-Thread");
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.setDaemon(true);
            return thread;
        });
    }
    
    public void start(){
        executorService.submit(()->{
            while(!Thread.interrupted()){
                tps.count();

                try{
                    findChunks();
                    loadChunks();
                    unloadChunks();
                }catch(Exception e){
                    throw new RuntimeException(e);
                }
                
                Thread.yield();
            }
        });
    }
    
    public ServerLevel getLevel(){
        return level;
    }


    public void sendChunkIsRequired(ServerChunk chunk){
        // getLevel().getServer().getPlayerList().broadcastPacket(new S2CPacketChunk(chunk));

        if(requestedChunks.containsKey(chunk.getPosition())){
            requestedChunks.get(chunk.getPosition()).sendPacket(new S2CPacketChunk(chunk));
            requestedChunks.remove(chunk.getPosition());
        }
    }

    public void requestedChunk(ServerPlayer player, ChunkPos chunkPos){
        final ServerChunk chunk = loadedChunks.get(chunkPos);

        if(chunk != null)
            player.sendPacket(new S2CPacketChunk(chunk));
        else
            requestedChunks.put(chunkPos, player);
    }

    
    public void loadInitChunkForPlayer(ServerPlayer player){
        final ChunkPos chunkPos = new ChunkPos(
            getChunkPos(player.getPosition().xFloor()),
            getChunkPos(player.getPosition().zFloor())
        );
        
        ensureFrontier(chunkPos);
    }
    
    private void findChunks(){
        // Load spawn chunks
        if(frontiers.isEmpty()){
            final Vec2f spawn = level.getConfiguration().getWorldSpawn();
            ensureFrontier(new ChunkPos(
                getChunkPos(spawn.xf()),
                getChunkPos(spawn.yf())
            ));
        }
        
        // Load players chunks
        for(ServerPlayer player: level.getServer().getPlayerList().getPlayers()){
            ensureFrontier(new ChunkPos(
                getChunkPos(player.getPosition().xFloor()),
                getChunkPos(player.getPosition().zFloor())
            ));
        }
        
        // Fast flood fill
        for(final ChunkPos frontierPos: frontiers){
            ensureFrontier(frontierPos.getNeighbor(-1,  0));
            ensureFrontier(frontierPos.getNeighbor( 1,  0));
            ensureFrontier(frontierPos.getNeighbor( 0, -1));
            ensureFrontier(frontierPos.getNeighbor( 0,  1));
        }
    
        frontiers.removeIf(chunkPos -> isOffTheGrid(chunkPos, 0));
        newFrontiers.removeIf(chunkPos -> isOffTheGrid(chunkPos, 0));
        if(newFrontiers.isEmpty())
            return;
        
        // Load new chunks
        loadQueue.addAll(newFrontiers);
        newFrontiers.clear();
    }
    
    private void ensureFrontier(ChunkPos chunkPos){
        if(frontiers.contains(chunkPos) || isOffTheGrid(chunkPos, 0))
            return;
        
        frontiers.add(chunkPos);
        newFrontiers.add(chunkPos);
    }


    public void render(TextureBatch batch, float size){
        for(ServerChunk chunk: generatedChunks.values()){
            final ChunkPos position = chunk.getPosition();
            final float alpha = 1;//Math.min(chunk.getLifeTimeMillis() / 500F, 1);
            batch.drawRect(0.5, 0.3, 0.3, alpha,  position.x * size, position.z * size,  size, size);
        }
        for(ServerChunk chunk: decoratedChunks.values()){
            final ChunkPos position = chunk.getPosition();
            batch.drawRect(1, 0.7, 0.3, 1,  position.x * size, position.z * size,  size, size);
        }
        for(ServerChunk chunk: illuminatedChunks.values()){
            final ChunkPos position = chunk.getPosition();
            batch.drawRect(1, 1, 0.2, 1,  position.x * size, position.z * size,  size, size);
        }
        for(ServerChunk chunk: loadedChunks.values()){
            final ChunkPos position = chunk.getPosition();
            batch.drawRect(0.2, 0.9, 0, 1,  position.x * size, position.z * size,  size, size);
        }
    }
    
    
    private void loadChunks(){
        // Load
        for(ChunkPos chunkPos: loadQueue){
            loadQueue.remove(chunkPos);
            if(isOffTheGrid(chunkPos, 2))
                continue;

            // [In Future]: ServerChunk chunk = loadChunk(chunkPos); // Load
            // [In Future]: if(chunk == null)
            generateChunk(chunkPos); // Start generate
        }
        // [Debug]: System.out.println("generateChunk.size() => " + generateChunks.size() + "; allChunks.size() => " + allChunks.size() + ";");

        handleAroundGeneratedChunks(this::decorateChunk);
        handleAroundDecoratedChunksIn(this::illuminateChunk);
        handleAroundIlluminatedChunksIn(this::finalizeChunkGen);
    }


    private void handleAroundGeneratedChunks(ChunkHandler handler){
        iterateFor:
        for(ServerChunk chunk: generatedChunks.values()){
            for(ChunkPos neighborPos: chunk.getNeighbors())
                if(!loadedChunks.containsKey(neighborPos) &&
                   !illuminatedChunks.containsKey(neighborPos) &&
                   !decoratedChunks.containsKey(neighborPos) &&
                   !generatedChunks.containsKey(neighborPos))
                    continue iterateFor;

            handler.handle(chunk);
        }
    }

    private void handleAroundDecoratedChunksIn(ChunkHandler handler){
        iterateFor:
        for(ServerChunk chunk: decoratedChunks.values()){
            for(ChunkPos neighborPos: chunk.getNeighbors())
                if(!loadedChunks.containsKey(neighborPos) &&
                   !illuminatedChunks.containsKey(neighborPos) &&
                   !decoratedChunks.containsKey(neighborPos))
                    continue iterateFor;

            handler.handle(chunk);
        }
    }

    private void handleAroundIlluminatedChunksIn(ChunkHandler handler){
        iterateFor:
        for(ServerChunk chunk: illuminatedChunks.values()){
            for(ChunkPos neighborPos: chunk.getNeighbors())
                if(!loadedChunks.containsKey(neighborPos) &&
                   !illuminatedChunks.containsKey(neighborPos))
                    continue iterateFor;

            handler.handle(chunk);
        }
    }


    private void generateChunk(ChunkPos chunkPos){
        final ChunkGenerator generator = level.getConfiguration().getGenerator();
        if(generator == null)
            return;

        // If not generated
        if(generatedChunks.containsKey(chunkPos))
            return;
        if(loadedChunks.containsKey(chunkPos))
            return;

        // Generate terrain
        final ServerChunk chunk = new ServerChunk(level, chunkPos);
        generator.generate(chunk);

        // Move
        generatedChunks.put(chunkPos, chunk);
    }

    private void decorateChunk(ServerChunk chunk){
        final ChunkGenerator generator = level.getConfiguration().getGenerator();
        if(generator == null)
            return;

        // Decorate neighbors
        for(ChunkPos neighborPos: chunk.getNeighbors()){
            final ServerChunk neighbor = getChunk(neighborPos);
            if(neighbor == null)
                return;
            // if(neighbor.decorated)
            //     return; // continue;

            // generator.decorate(neighbor);
            // neighbor.decorated = true;
        }
        // Decorate chunk
        generator.decorate(chunk);

        // Move
        generatedChunks.remove(chunk.getPosition());
        decoratedChunks.put(chunk.getPosition(), chunk);
        chunk.decorated = true;
    }

    private void illuminateChunk(ServerChunk chunk){
        // Update heightmap
        chunk.getHeightMap(HeightmapType.LIGHT_SURFACE).update();
        chunk.getHeightMap(HeightmapType.SURFACE).update();

        // Update skylight
        chunk.getLevel().getSkyLight().updateSkyLight(chunk);

        // Move
        decoratedChunks.remove(chunk.getPosition());
        illuminatedChunks.put(chunk.getPosition(), chunk);
    }

    private void finalizeChunkGen(ServerChunk chunk){
        // Move
        illuminatedChunks.remove(chunk.getPosition());
        loadedChunks.put(chunk.getPosition(), chunk);

        // Send
        sendChunkIsRequired(chunk);
    }


    private boolean isChunkExists(ChunkPos chunkPosition){
        return loadedChunks.containsKey(chunkPosition) || decoratedChunks.containsKey(chunkPosition) || generatedChunks.containsKey(chunkPosition);
    }

    public void unloadChunks(){
        for(ServerChunk chunk: loadedChunks.values())
            if(isOffTheGrid(chunk.getPosition(), 0))
                unloadChunk(chunk);

        for(ServerChunk chunk: decoratedChunks.values())
            if(isOffTheGrid(chunk.getPosition(), 1))
                decoratedChunks.remove(chunk.getPosition());

        for(ServerChunk chunk: generatedChunks.values())
            if(isOffTheGrid(chunk.getPosition(), 2))
                generatedChunks.remove(chunk.getPosition());

        for(ServerChunk chunk: illuminatedChunks.values())
            if(isOffTheGrid(chunk.getPosition(), 3))
                illuminatedChunks.remove(chunk.getPosition());
    }

    private ServerChunk loadChunk(ChunkPos chunkPos){
        return null;
    }


    public void unloadChunk(ServerChunk chunk){
        loadedChunks.remove(chunk.getPosition());
    }

    @Override
    public ServerChunk getChunk(int chunkX, int chunkZ){
        ServerChunk chunk = loadedChunks.get(new ChunkPos(chunkX, chunkZ));
        if(chunk == null) chunk = illuminatedChunks.get(new ChunkPos(chunkX, chunkZ));
        if(chunk == null) chunk = decoratedChunks.get(new ChunkPos(chunkX, chunkZ));
        if(chunk == null) chunk = generatedChunks.get(new ChunkPos(chunkX, chunkZ));
        return chunk;
    }

    @Override
    public ServerChunk getChunk(ChunkPos chunkPos){
        return getChunk(chunkPos.x, chunkPos.z);
    }

    
    private boolean isOffTheGrid(ChunkPos chunkPos){
        return isOffTheGrid(chunkPos, 0);
    }
    
    private boolean isOffTheGrid(ChunkPos chunkPos, float renderDistanceIncrease){
        final Vec2f spawn = level.getServer().getLevelManager().getDefaultLevel().getConfiguration().getWorldSpawn();
        if(distToChunk(chunkPos.x, chunkPos.z, spawn) <= level.getServer().getConfiguration().getMaxRenderDistance() + renderDistanceIncrease)
            return false;
        
        for(Entity entity: level.getEntities())
            if(entity instanceof ServerPlayer player)
                if(distToChunk(chunkPos.x, chunkPos.z, player.getPosition()) <= player.getRenderDistance() + renderDistanceIncrease)
                    return false;
        
        return true;
    }

}
