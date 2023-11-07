package minecraftose.main.network.packet.c2s.login;

import jpize.net.tcp.packet.IPacket;
import minecraftose.server.net.PlayerLoginConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class C2SPacketAuth extends IPacket<PlayerLoginConnection>{
    
    public static final int PACKET_ID = 1;
    
    public C2SPacketAuth(){
        super(PACKET_ID);
    }
    
    
    public String accountSessionToken;
    
    public C2SPacketAuth(String accountSessionToken){
        this();
        this.accountSessionToken = accountSessionToken;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(accountSessionToken);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        accountSessionToken = stream.readUTF();
    }
    
    @Override
    public void handle(PlayerLoginConnection handler){
        handler.auth(this);
    }
    
}
