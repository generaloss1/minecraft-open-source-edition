package minecraftose.main.network.packet.s2c.game;

import minecraftose.client.network.ClientPacketHandler;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class S2CPacketPong extends NetPacket<ClientPacketHandler>{
    
        
    public S2CPacketPong(){}
    
    
    public long timeNanos;
    
    public S2CPacketPong(long timeNanos){
        this();
        this.timeNanos = timeNanos;
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeLong(timeNanos);
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        timeNanos = stream.readLong();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.pong(this);
    }
    
}

