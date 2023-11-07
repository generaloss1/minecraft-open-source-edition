package minecraftose.main.network.packet.s2c.game;

import jpize.net.tcp.packet.IPacket;
import minecraftose.client.net.ClientPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class S2CPacketAbilities extends IPacket<ClientPacketHandler>{
    
    public static final int PACKET_ID = 21;
    
    public S2CPacketAbilities(){
        super(PACKET_ID);
    }
    
    
    public boolean flyEnabled;
    
    public S2CPacketAbilities(boolean flyEnabled){
        this();
        this.flyEnabled = flyEnabled;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeBoolean(flyEnabled);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        flyEnabled = stream.readBoolean();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.abilities(this);
    }
    
}
