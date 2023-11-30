package minecraftose.server;

import jpize.net.tcp.TcpServer;
import minecraftose.main.text.Component;
import minecraftose.main.time.GameTime;
import minecraftose.server.command.ServerCommandDispatcher;
import minecraftose.server.level.LevelManager;
import minecraftose.server.level.ServerLevel;
import minecraftose.server.network.ServerConnectionManager;
import minecraftose.server.player.PlayerList;
import minecraftose.server.player.ServerPlayer;
import jpize.util.time.TickGenerator;
import jpize.util.time.Tickable;
import minecraftose.server.worldgen.generator.ChunkGeneratorDefault;
import minecraftose.server.worldgen.generator.ChunkGeneratorTestNew;

import java.util.Collection;

public abstract class Server implements Tickable{
    
    private final ServerConfiguration configuration;
    private final TcpServer tcpServer;
    private final ServerConnectionManager connectionManager;
    private final PlayerList playerList;
    private final LevelManager levelManager;
    private final ServerCommandDispatcher commandDispatcher;
    private final ServerGameTime gameTime;
    
    public Server(){
        this.configuration = new ServerConfiguration();
        
        this.connectionManager = new ServerConnectionManager(this);
        this.tcpServer = new TcpServer(connectionManager);
        
        this.playerList = new PlayerList(this);
        this.levelManager = new LevelManager(this);
        this.commandDispatcher = new ServerCommandDispatcher(this);
        this.gameTime = new ServerGameTime(this);
    }
    
    
    public void run(){
        final long seed = 61128216; // Maths.randomSeed(8);
        getLevelManager().createLevel(getConfiguration().getDefaultLevelName(), String.valueOf(seed), ChunkGeneratorDefault.INSTANCE); // Create overworld level
        // getLevelManager().createLevel("flat-world", "FLAT", ChunkGeneratorFlat.INSTANCE); // Create flat-world level

        tcpServer.run(getConfiguration().getAddress(), getConfiguration().getPort()); // Run TCP server
        
        new TickGenerator(GameTime.TICKS_PER_SECOND).startAsync(this);
    }

    public void stop(){
        final Collection<ServerPlayer> players = getPlayerList().getPlayers();
        for(ServerPlayer player: players)
            player.disconnect();
    }

    
    @Override
    public void tick(){
        gameTime.tick();
        
        for(ServerLevel level : getLevelManager().getLoadedLevels())
            level.tick();
    }


    public void broadcast(Component component){
        playerList.broadcastServerMessage(component);
    }
    
    
    public ServerConfiguration getConfiguration(){
        return configuration;
    }
    
    public PlayerList getPlayerList(){
        return playerList;
    }
    
    public LevelManager getLevelManager(){
        return levelManager;
    }
    
    public ServerConnectionManager getConnectionManager(){
        return connectionManager;
    }
    
    public ServerCommandDispatcher getCommandDispatcher(){
        return commandDispatcher;
    }
    
    public ServerGameTime getGameTime(){
        return gameTime;
    }
    
}
