package minecraftose.main.network.packet.s2c.game;

import minecraftose.client.network.ClientPacketHandler;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class S2CPacketDisconnect extends NetPacket<ClientPacketHandler>{
    
        
    public S2CPacketDisconnect(){}
    
    
    public String reasonComponent;
    
    public S2CPacketDisconnect(String reasonComponent){
        this();
        this.reasonComponent = reasonComponent;
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeUTF(reasonComponent);
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        reasonComponent = stream.readUTF();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.disconnect(this);
    }
    
}
