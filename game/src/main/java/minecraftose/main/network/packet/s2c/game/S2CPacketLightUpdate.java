package minecraftose.main.network.packet.s2c.game;

import jpize.net.tcp.packet.IPacket;
import minecraftose.client.network.ClientPacketHandler;
import minecraftose.main.chunk.ChunkUtils;
import minecraftose.main.chunk.LevelChunkSection;
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

    public S2CPacketLightUpdate(LevelChunkSection section){
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
        light = stream.readNBytes(ChunkUtils.VOLUME);
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.lightUpdate(this);
    }

}