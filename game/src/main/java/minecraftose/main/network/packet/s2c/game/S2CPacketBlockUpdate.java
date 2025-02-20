package minecraftose.main.network.packet.s2c.game;

import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;
import minecraftose.client.network.ClientPacketHandler;

import java.io.IOException;

public class S2CPacketBlockUpdate extends NetPacket<ClientPacketHandler> {
    
        
    public S2CPacketBlockUpdate(){}
    
    public int x, y, z;
    public short blockData;
    
    public S2CPacketBlockUpdate(int x, int y, int z, short blockData){
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
    public void handle(ClientPacketHandler handler){
        handler.blockUpdate(this);
    }

}
