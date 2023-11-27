package minecraftose.main.network.packet.s2c.game;

import jpize.net.tcp.packet.IPacket;
import minecraftose.client.network.ClientPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class S2CPacketPong extends IPacket<ClientPacketHandler>{
    
    public static final int PACKET_ID = 7;
    
    public S2CPacketPong(){
        super(PACKET_ID);
    }
    
    
    public long timeNanos;
    
    public S2CPacketPong(long timeNanos){
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
    public void handle(ClientPacketHandler handler){
        handler.pong(this);
    }
    
}

