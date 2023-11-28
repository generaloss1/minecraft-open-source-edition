package minecraftose.server.player;

import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.TcpConnection;
import jpize.net.tcp.packet.IPacket;
import minecraftose.main.audio.Sound;
import minecraftose.main.entity.Entity;
import minecraftose.main.entity.Player;
import minecraftose.main.inventory.PlayerInventory;
import minecraftose.main.level.Level;
import minecraftose.main.network.packet.s2c.game.S2CPacketAbilities;
import minecraftose.main.network.packet.s2c.game.S2CPacketChatMessage;
import minecraftose.main.network.packet.s2c.game.S2CPacketPlaySound;
import minecraftose.main.network.packet.s2c.game.S2CPacketTeleportPlayer;
import minecraftose.main.text.Component;
import minecraftose.server.Server;
import minecraftose.server.level.ServerLevel;
import minecraftose.server.network.ServerPlayerGameConnection;

public class ServerPlayer extends Player{
    
    private final Server server;
    private final ServerPlayerGameConnection connectionAdapter;
    private final PlayerInventory inventory;
    
    private int renderDistance;

    public ServerPlayer(ServerLevel level, TcpConnection connection, String name){
        super(level, name);
        this.server = level.getServer();
        this.connectionAdapter = new ServerPlayerGameConnection(this, connection);
        this.renderDistance = server.getConfiguration().getMaxRenderDistance(); //: 0
        this.inventory = new PlayerInventory();
    }
    
    public Server getServer(){
        return server;
    }

    @Override
    public ServerLevel getLevel(){
        return (ServerLevel) super.getLevel();
    }

    public void playSound(Sound sound, float volume, float pitch){
        sendPacket(new S2CPacketPlaySound(sound, volume, pitch, this.getPosition()));
    }
    
    
    public void teleport(Level level, Vec3f position, EulerAngles rotation){
        sendPacket(new S2CPacketTeleportPlayer(level.getConfiguration().getName(), position, rotation));
        
        final Level oldLevel = getLevel();
        if(level != oldLevel){
            setLevel(level);
            oldLevel.removeEntity(this);
            level.addEntity(this);
        }
        getPosition().set(position);
        getRotation().set(rotation);
    }
    
    public void teleport(Entity entity){
        teleport(entity.getLevel(), entity.getPosition(), entity.getRotation());
    }
    
    public void teleport(Vec3f position, EulerAngles rotation){
        teleport(getLevel(), position, rotation);
    }
    
    public void teleport(Level level, Vec3f position){
        teleport(level, position, getRotation());
    }
    
    public void teleport(Vec3f position){
        teleport(getLevel(), position, getRotation());
    }
    
    
    public void setFlyEnabled(boolean flyEnabled){
        sendPacket(new S2CPacketAbilities(flyEnabled));
        super.setFlyEnabled(flyEnabled);
    }


    public void disconnect(){
        getConnectionAdapter().getConnection().close();
    }
    
    public void sendToChat(Component message){
        server.getPlayerList().broadcastPlayerMessage(this, message);
    }
    
    public void sendMessage(Component message){
        sendPacket(new S2CPacketChatMessage(message.toFlatList()));
    }
    
    
    public ServerPlayerGameConnection getConnectionAdapter(){
        return connectionAdapter;
    }
    
    public void sendPacket(IPacket<?> packet){
        connectionAdapter.sendPacket(packet);
    }
    
    public int getRenderDistance(){
        return renderDistance;
    }
    
    public void setRenderDistance(int renderDistance){
        this.renderDistance = renderDistance;
    }


    public PlayerInventory getInventory(){
        return inventory;
    }
    
}
