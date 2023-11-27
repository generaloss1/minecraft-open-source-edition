package minecraftose.main.network.packet.s2c.game;

import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import minecraftose.client.network.ClientPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class S2CPacketSpawnInfo extends IPacket<ClientPacketHandler>{
    
    public static final int PACKET_ID = 13;
    
    public S2CPacketSpawnInfo(){
        super(PACKET_ID);
    }
    
    
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
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(levelName);
        stream.writeVec3f(position);
        stream.writeLong(gameTime);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        levelName = stream.readUTF();
        position = stream.readVec3f();
        gameTime = stream.readLong();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.spawnInfo(this);
    }
    
}
