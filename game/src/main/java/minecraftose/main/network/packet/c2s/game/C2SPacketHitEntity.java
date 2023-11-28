package minecraftose.main.network.packet.c2s.game;

import jpize.net.tcp.packet.IPacket;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;
import minecraftose.server.network.ServerPlayerGameConnection;

import java.io.IOException;
import java.util.UUID;

public class C2SPacketHitEntity extends IPacket<ServerPlayerGameConnection>{

    public static final int PACKET_ID = 26;

    public C2SPacketHitEntity(){
        super(PACKET_ID);
    }


    public UUID entityUUID;

    public C2SPacketHitEntity(UUID entityUUID){
        this();
        this.entityUUID = entityUUID;
    }


    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeUUID(entityUUID);
    }

    @Override
    public void read(JpizeInputStream stream) throws IOException{
        entityUUID = stream.readUUID();
    }

    @Override
    public void handle(ServerPlayerGameConnection handler){
        handler.hitEntity(this);
    }

}