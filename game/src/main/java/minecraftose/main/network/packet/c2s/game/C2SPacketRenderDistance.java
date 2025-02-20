package minecraftose.main.network.packet.c2s.game;

import minecraftose.server.network.ServerPlayerGameConnection;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class C2SPacketRenderDistance extends NetPacket<ServerPlayerGameConnection>{
    
        
    public C2SPacketRenderDistance(){}
    
    
    public int renderDistance;
    
    public C2SPacketRenderDistance(int renderDistance){
        this();
        this.renderDistance = renderDistance;
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeInt(renderDistance);
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        renderDistance = stream.readInt();
    }
    
    @Override
    public void handle(ServerPlayerGameConnection handler){
        handler.renderDistance(this);
    }
    
}
