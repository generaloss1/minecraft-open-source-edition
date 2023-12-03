package minecraftose.main.network.packet.s2c.game;

import jpize.net.tcp.packet.IPacket;
import minecraftose.client.network.ClientPacketHandler;
import minecraftose.main.chunk.ChunkBase;
import minecraftose.main.chunk.ChunkSection;
import minecraftose.main.chunk.storage.SectionPos;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class S2CPacketLightUpdate extends IPacket<ClientPacketHandler>{

    public static final int PACKET_ID = 24;

    public S2CPacketLightUpdate(){
        super(PACKET_ID);
    }

    public SectionPos position;
    public byte[] light;

    public S2CPacketLightUpdate(ChunkSection section){
        this();
        position = section.getPosition();
        light = section.light;
    }


    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeVec3i(position);
        stream.write(light);
    }

    @Override
    public void read(JpizeInputStream stream) throws IOException{
        position = new SectionPos(stream.readVec3i());
        light = stream.readNBytes(ChunkBase.VOLUME);
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.lightUpdate(this);
    }

}