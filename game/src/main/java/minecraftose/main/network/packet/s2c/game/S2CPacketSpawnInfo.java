package minecraftose.main.network.packet.s2c.game;

import jpize.util.math.vector.Vec3f;
import minecraftose.client.network.ClientPacketHandler;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class S2CPacketSpawnInfo extends NetPacket<ClientPacketHandler>{

    public S2CPacketSpawnInfo(){}
    
    public String levelName;
    public Vec3f position;
    public long gameTime;
    
    public S2CPacketSpawnInfo(String levelName, Vec3f position, long gameTime){
        this();
        
        this.levelName = levelName;
        this.position = position;
        this.gameTime = gameTime;
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeUTF(levelName);
        stream.writeVec3f(position);
        stream.writeLong(gameTime);
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        levelName = stream.readUTF();
        position = stream.readVec3f();
        gameTime = stream.readLong();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.spawnInfo(this);
    }
    
}
