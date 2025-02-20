package minecraftose.main.network.packet.s2c.game;

import minecraftose.client.network.ClientPacketHandler;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class S2CPacketTime extends NetPacket<ClientPacketHandler>{
    
        
    public S2CPacketTime(){}
    
    
    public long gameTimeTicks;
    
    public S2CPacketTime(long gameTimeTicks){
        this();
        this.gameTimeTicks = gameTimeTicks;
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeLong(gameTimeTicks);
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        gameTimeTicks = stream.readLong();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.time(this);
    }
    
}