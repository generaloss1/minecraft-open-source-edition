package minecraftose.server.level;

import minecraftose.server.Server;
import minecraftose.server.level.gen.ChunkGenerator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LevelManager{
    
    public final Server server;
    public final Map<String, LevelS> loadedLevels;
    
    public LevelManager(Server server){
        this.server = server;
        this.loadedLevels = new HashMap<>();
    }
    
    public Server getServer(){
        return server;
    }
    
    
    public Collection<LevelS> getLoadedLevels(){
        return loadedLevels.values();
    }
    
    public LevelS getLevel(String worldName){
        return loadedLevels.get(worldName);
    }
    
    public LevelS getDefaultLevel(){
        return getLevel(server.getConfiguration().getDefaultLevelName());
    }
    
    public void loadLevel(String levelName){
        if(levelName == null || isLevelExists(levelName) || isLevelLoaded(levelName))
            return;
        
        // final ServerLevel level = new ServerLevel(server);
        // level.getConfiguration().load(levelName, seed, generator);
        // loadedLevels.put(levelName, level);
        // level.getChunkProvider().start();
        
        System.out.println("[Server]: Loaded level '" + levelName + "'");
    }
    
    public void createLevel(String levelName, String seed, ChunkGenerator generator){
        if(levelName == null || !isLevelExists(levelName) || isLevelLoaded(levelName))
            return;
        
        final LevelS level = new LevelS(server);
        level.getConfiguration().load(levelName, seed, generator);
        loadedLevels.put(levelName, level);
        level.getChunkProvider().getLoader().run();
        
        System.out.println("[Server]: Created level '" + levelName + "'");
    }
    
    public boolean isLevelLoaded(String levelName){
        return loadedLevels.containsKey(levelName);
    }
    
    public boolean isLevelExists(String levelName){
        return true; //: SHIZA.
    }
    
}
