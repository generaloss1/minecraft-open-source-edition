package minecraftose.main.network.packet.s2c.game;

import jpize.net.tcp.packet.IPacket;
import minecraftose.client.net.ClientPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class S2CPacketBlockUpdate extends IPacket<ClientPacketHandler>{
    
    public static final int PACKET_ID = 10;
    
    public S2CPacketBlockUpdate(){
        super(PACKET_ID);
    }
    
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
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeInt(x);
        stream.writeInt(y);
        stream.writeInt(z);
        stream.writeShort(blockData);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
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
