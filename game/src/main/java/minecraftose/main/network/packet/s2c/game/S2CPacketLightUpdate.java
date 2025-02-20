package minecraftose.main.network.packet.s2c.game;

import minecraftose.client.network.ClientPacketHandler;
import minecraftose.main.chunk.ChunkBase;
import minecraftose.main.chunk.ChunkSection;
import minecraftose.main.chunk.storage.SectionPos;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class S2CPacketLightUpdate extends NetPacket<ClientPacketHandler>{

    
    public S2CPacketLightUpdate(){}

    public SectionPos position;
    public byte[] light;

    public S2CPacketLightUpdate(ChunkSection section){
        this();
        position = section.getPosition();
        light = section.light;
    }


    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeVec3i(position);
        stream.write(light);
    }

    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        position = new SectionPos(stream.readVec3i());
        light = stream.readNBytes(ChunkBase.VOLUME);
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.lightUpdate(this);
    }

}