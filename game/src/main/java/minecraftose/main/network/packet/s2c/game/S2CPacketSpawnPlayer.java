package minecraftose.main.network.packet.s2c.game;

import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import minecraftose.client.network.ClientPacketHandler;
import minecraftose.server.player.ServerPlayer;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;
import java.util.UUID;

public class S2CPacketSpawnPlayer extends IPacket<ClientPacketHandler>{
    
    public static final int PACKET_ID = 15;
    
    public S2CPacketSpawnPlayer(){
        super(PACKET_ID);
    }
    
    public UUID uuid;
    public Vec3f position;
    public EulerAngles rotation;
    public String playerName;
    
    public S2CPacketSpawnPlayer(ServerPlayer player){
        this();
        this.uuid = player.getUUID();
        this.position = player.getPosition();
        this.rotation = player.getRotation();
        this.playerName = player.getName();
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(uuid.toString());
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
        stream.writeUTF(playerName);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        uuid = UUID.fromString(stream.readUTF());
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
        playerName = stream.readUTF();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.spawnPlayer(this);
    }
    
}
