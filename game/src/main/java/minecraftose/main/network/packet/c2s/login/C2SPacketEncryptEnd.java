package minecraftose.main.network.packet.c2s.login;

import jpize.net.tcp.packet.IPacket;
import minecraftose.server.net.PlayerLoginConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class C2SPacketEncryptEnd extends IPacket<PlayerLoginConnection>{
    
    public static final int PACKET_ID = 4;
    
    public C2SPacketEncryptEnd(){
        super(PACKET_ID);
    }
    
    
    public byte[] encryptedClientKey;
    
    public C2SPacketEncryptEnd(byte[] encryptedClientKey){
        this();
        this.encryptedClientKey = encryptedClientKey;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeByteArray(encryptedClientKey);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        encryptedClientKey = stream.readByteArray();
    }
    
    @Override
    public void handle(PlayerLoginConnection handler){
        handler.encryptEnd(this);
    }
    
}
