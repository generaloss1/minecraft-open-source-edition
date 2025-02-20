package minecraftose.main.network.packet.c2s.game;

import minecraftose.server.network.ServerPlayerGameConnection;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class C2SPacketPing extends NetPacket<ServerPlayerGameConnection>{
    
        
    public C2SPacketPing(){}
    
    
    public long timeNanos;
    
    public C2SPacketPing(long timeNanos){
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
    public void handle(ServerPlayerGameConnection handler){
        handler.ping(this);
    }
    
}
