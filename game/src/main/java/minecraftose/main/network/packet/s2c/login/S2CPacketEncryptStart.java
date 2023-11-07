package minecraftose.main.network.packet.s2c.login;

import jpize.net.security.PublicRSA;
import jpize.net.tcp.packet.IPacket;
import minecraftose.client.net.ClientPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class S2CPacketEncryptStart extends IPacket<ClientPacketHandler>{
    
    public static final int PACKET_ID = 5;
    
    public S2CPacketEncryptStart(){
        super(PACKET_ID);
    }
    
    
    public PublicRSA publicServerKey;
    
    public S2CPacketEncryptStart(PublicRSA publicServerKey){
        this();
        this.publicServerKey = publicServerKey;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.write(publicServerKey.getKey().getEncoded());
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        publicServerKey = new PublicRSA(stream.readAllBytes());
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.encryptStart(this);
    }
    
}

