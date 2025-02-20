package minecraftose.main.network.packet.s2c.game;

import minecraftose.client.network.ClientPacketHandler;
import minecraftose.main.entity.Entity;

import java.io.IOException;
import java.util.UUID;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class S2CPacketRemoveEntity extends NetPacket<ClientPacketHandler>{
    
        
    public S2CPacketRemoveEntity(){}
    
    
    public UUID uuid;
    
    public S2CPacketRemoveEntity(Entity entity){
        this();
        uuid = entity.getUUID();
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeUTF(uuid.toString());
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        uuid = UUID.fromString(stream.readUTF());
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.removeEntity(this);
    }
    
}