package minecraftose.main.network.packet.s2c.game;

import jpize.net.tcp.packet.IPacket;
import minecraftose.client.net.ClientPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class S2CPacketTime extends IPacket<ClientPacketHandler>{
    
    public static final int PACKET_ID = 22;
    
    public S2CPacketTime(){
        super(PACKET_ID);
    }
    
    
    public long gameTimeTicks;
    
    public S2CPacketTime(long gameTimeTicks){
        this();
        this.gameTimeTicks = gameTimeTicks;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeLong(gameTimeTicks);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        gameTimeTicks = stream.readLong();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.time(this);
    }
    
}