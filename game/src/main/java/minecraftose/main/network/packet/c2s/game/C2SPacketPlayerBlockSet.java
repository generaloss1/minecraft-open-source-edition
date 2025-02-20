package minecraftose.main.network.packet.c2s.game;

import minecraftose.server.network.ServerPlayerGameConnection;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class C2SPacketPlayerBlockSet extends NetPacket<ServerPlayerGameConnection>{
    
        
    public C2SPacketPlayerBlockSet(){}
    
    public int x, y, z;
    public short blockData;
    
    public C2SPacketPlayerBlockSet(int x, int y, int z, short blockData){
        this();
        
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockData = blockData;
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeInt(x);
        stream.writeInt(y);
        stream.writeInt(z);
        stream.writeShort(blockData);
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        x = stream.readInt();
        y = stream.readInt();
        z = stream.readInt();
        blockData = stream.readShort();
    }
    
    @Override
    public void handle(ServerPlayerGameConnection handler){
        handler.playerBlockSet(this);
    }
    
}
