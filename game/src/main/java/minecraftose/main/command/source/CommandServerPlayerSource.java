package minecraftose.main.command.source;

import jpize.util.math.vector.Vec3f;
import minecraftose.main.text.Component;
import minecraftose.server.Server;
import minecraftose.server.level.LevelS;
import minecraftose.server.player.ServerPlayer;

public class CommandServerPlayerSource extends CommandServerSource{
    
    private final ServerPlayer player;
    
    public CommandServerPlayerSource(ServerPlayer player){
        this.player = player;
    }

    public ServerPlayer getPlayer(){
        return player;
    }

    @Override
    @SuppressWarnings("unchecked")
    public LevelS getLevel(){
        return player.getLevel();
    }

    @Override
    public Vec3f getPosition(){
        return player.getPosition();
    }
    
    @Override
    public void sendMessage(Component message){
        player.sendMessage(message);
    }

    @Override
    public Server getServer(){
        return player.getServer();
    }

}
