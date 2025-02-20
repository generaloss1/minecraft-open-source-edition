package minecraftose.main.network.packet.s2c.game;

import jpize.util.math.EulerAngles;
import jpize.util.math.vector.Vec3f;
import minecraftose.client.network.ClientPacketHandler;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class S2CPacketTeleportPlayer extends NetPacket<ClientPacketHandler>{

    public S2CPacketTeleportPlayer(){}
    
    public Vec3f position;
    public EulerAngles rotation;
    public String levelName;
    
    public S2CPacketTeleportPlayer(String levelName, Vec3f position, EulerAngles rotation){
        this();
        this.levelName = levelName;
        this.position = position;
        this.rotation = rotation;
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeUTF(levelName);
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        levelName = stream.readUTF();
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.teleportPlayer(this);
    }
    
}