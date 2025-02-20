package minecraftose.main.network.packet.s2c.login;

import jpize.util.security.PublicRSA;
import minecraftose.client.network.ClientPacketHandler;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class S2CPacketEncryptStart extends NetPacket<ClientPacketHandler>{
    
        
    public S2CPacketEncryptStart(){}
    
    
    public PublicRSA publicServerKey;
    
    public S2CPacketEncryptStart(PublicRSA publicServerKey){
        this();
        this.publicServerKey = publicServerKey;
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.write(publicServerKey.getKey().getEncoded());
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        publicServerKey = new PublicRSA(stream.readAllBytes());
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.encryptStart(this);
    }
    
}

