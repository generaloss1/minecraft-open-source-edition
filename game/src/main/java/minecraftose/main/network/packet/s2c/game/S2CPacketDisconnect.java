package minecraftose.main.network.packet.s2c.game;

import jpize.net.tcp.packet.IPacket;
import minecraftose.client.net.ClientPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class S2CPacketDisconnect extends IPacket<ClientPacketHandler>{
    
    public static final int PACKET_ID = 3;
    
    public S2CPacketDisconnect(){
        super(PACKET_ID);
    }
    
    
    public String reasonComponent;
    
    public S2CPacketDisconnect(String reasonComponent){
        this();
        this.reasonComponent = reasonComponent;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(reasonComponent);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        reasonComponent = stream.readUTF();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.disconnect(this);
    }
    
}
