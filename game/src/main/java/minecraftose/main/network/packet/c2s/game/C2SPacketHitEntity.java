package minecraftose.main.network.packet.c2s.game;

import minecraftose.server.network.ServerPlayerGameConnection;

import java.io.IOException;
import java.util.UUID;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class C2SPacketHitEntity extends NetPacket<ServerPlayerGameConnection>{

    
    public C2SPacketHitEntity(){}


    public UUID entityUUID;

    public C2SPacketHitEntity(UUID entityUUID){
        this();
        this.entityUUID = entityUUID;
    }


    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeUUID(entityUUID);
    }

    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        entityUUID = stream.readUUID();
    }

    @Override
    public void handle(ServerPlayerGameConnection handler){
        handler.hitEntity(this);
    }

}