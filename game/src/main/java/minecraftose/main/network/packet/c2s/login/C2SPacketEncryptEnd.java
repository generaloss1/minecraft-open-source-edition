package minecraftose.main.network.packet.c2s.login;

import minecraftose.server.network.PlayerLoginConnection;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class C2SPacketEncryptEnd extends NetPacket<PlayerLoginConnection>{
    
        
    public C2SPacketEncryptEnd(){}
    
    
    public byte[] encryptedClientKey;
    
    public C2SPacketEncryptEnd(byte[] encryptedClientKey){
        this();
        this.encryptedClientKey = encryptedClientKey;
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeByteArray(encryptedClientKey);
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        encryptedClientKey = stream.readByteArray();
    }
    
    @Override
    public void handle(PlayerLoginConnection handler){
        handler.encryptEnd(this);
    }
    
}
