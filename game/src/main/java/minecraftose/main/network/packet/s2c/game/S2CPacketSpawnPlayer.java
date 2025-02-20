package minecraftose.main.network.packet.s2c.game;

import jpize.util.math.EulerAngles;
import jpize.util.math.vector.Vec3f;
import minecraftose.client.network.ClientPacketHandler;
import minecraftose.server.player.ServerPlayer;

import java.io.IOException;
import java.util.UUID;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class S2CPacketSpawnPlayer extends NetPacket<ClientPacketHandler>{

    public S2CPacketSpawnPlayer(){}
    
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
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeUTF(uuid.toString());
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
        stream.writeUTF(playerName);
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
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
