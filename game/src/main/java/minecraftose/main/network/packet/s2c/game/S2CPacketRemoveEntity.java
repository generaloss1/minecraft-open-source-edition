package minecraftose.main.network.packet.s2c.game;

import jpize.net.tcp.packet.IPacket;
import minecraftose.client.network.ClientPacketHandler;
import minecraftose.main.entity.Entity;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;
import java.util.UUID;

public class S2CPacketRemoveEntity extends IPacket<ClientPacketHandler>{
    
    public static final int PACKET_ID = 16;
    
    public S2CPacketRemoveEntity(){
        super(PACKET_ID);
    }
    
    
    public UUID uuid;
    
    public S2CPacketRemoveEntity(Entity entity){
        this();
        uuid = entity.getUUID();
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(uuid.toString());
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        uuid = UUID.fromString(stream.readUTF());
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.removeEntity(this);
    }
    
}