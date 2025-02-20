package minecraftose.main.network.packet.c2s.game;

import minecraftose.server.network.ServerPlayerGameConnection;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class C2SPacketChunkRequest extends NetPacket<ServerPlayerGameConnection>{
    
        
    public C2SPacketChunkRequest(){}
    
    public long packedChunkPos;
    
    public C2SPacketChunkRequest(long packedChunkPos){
        this();
        
        this.packedChunkPos = packedChunkPos;
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeLong(packedChunkPos);
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        packedChunkPos = stream.readLong();
    }
    
    @Override
    public void handle(ServerPlayerGameConnection handler){
        handler.chunkRequest(this);
    }
    
}
