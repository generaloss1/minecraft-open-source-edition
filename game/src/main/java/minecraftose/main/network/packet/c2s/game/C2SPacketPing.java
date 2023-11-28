package minecraftose.main.network.packet.c2s.game;

import jpize.net.tcp.packet.IPacket;
import minecraftose.server.network.ServerPlayerGameConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class C2SPacketPing extends IPacket<ServerPlayerGameConnection>{
    
    public static final int PACKET_ID = 7;
    
    public C2SPacketPing(){
        super(PACKET_ID);
    }
    
    
    public long timeNanos;
    
    public C2SPacketPing(long timeNanos){
        this();
        this.timeNanos = timeNanos;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeLong(timeNanos);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        timeNanos = stream.readLong();
    }

    @Override
    public void handle(ServerPlayerGameConnection handler){
        handler.ping(this);
    }
    
}
