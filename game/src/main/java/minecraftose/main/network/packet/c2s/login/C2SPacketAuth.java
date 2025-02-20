package minecraftose.main.network.packet.c2s.login;

import minecraftose.server.network.PlayerLoginConnection;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class C2SPacketAuth extends NetPacket<PlayerLoginConnection>{
    
        
    public C2SPacketAuth(){}
    
    
    public String accountSessionToken;
    
    public C2SPacketAuth(String accountSessionToken){
        this();
        this.accountSessionToken = accountSessionToken;
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeUTF(accountSessionToken);
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        accountSessionToken = stream.readUTF();
    }
    
    @Override
    public void handle(PlayerLoginConnection handler){
        handler.auth(this);
    }
    
}
