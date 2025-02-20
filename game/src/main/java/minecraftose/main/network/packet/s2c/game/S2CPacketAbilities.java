package minecraftose.main.network.packet.s2c.game;

import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;
import minecraftose.client.network.ClientPacketHandler;

import java.io.IOException;

public class S2CPacketAbilities extends NetPacket<ClientPacketHandler> {
    
    
    public S2CPacketAbilities(){ }
    
    
    public boolean flyEnabled;
    
    public S2CPacketAbilities(boolean flyEnabled){
        this();
        this.flyEnabled = flyEnabled;
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeBoolean(flyEnabled);
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        flyEnabled = stream.readBoolean();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.abilities(this);
    }
    
}
