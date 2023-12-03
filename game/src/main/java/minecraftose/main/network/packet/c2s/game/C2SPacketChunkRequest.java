package minecraftose.main.network.packet.c2s.game;

import jpize.net.tcp.packet.IPacket;
import minecraftose.main.chunk.storage.ChunkPos;
import minecraftose.server.network.ServerPlayerGameConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class C2SPacketChunkRequest extends IPacket<ServerPlayerGameConnection>{
    
    public static final int PACKET_ID = 12;
    
    public C2SPacketChunkRequest(){
        super(PACKET_ID);
    }
    
    public long packedChunkPos;
    
    public C2SPacketChunkRequest(long packedChunkPos){
        this();
        
        this.packedChunkPos = packedChunkPos;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeLong(packedChunkPos);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        packedChunkPos = stream.readLong();
    }
    
    @Override
    public void handle(ServerPlayerGameConnection handler){
        handler.chunkRequest(this);
    }
    
}
