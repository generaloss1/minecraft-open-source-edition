package minecraftose.main.network.packet.c2s.login;

import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;
import minecraftose.server.network.PlayerLoginConnection;

import java.io.IOException;

public class C2SPacketLogin extends NetPacket<PlayerLoginConnection>{
    
        
    public C2SPacketLogin(){}
    
    
    public int clientVersionID;
    public String profileName;
    
    public C2SPacketLogin(int clientVersionID, String profileName){
        this();
        this.clientVersionID = clientVersionID;
        this.profileName = profileName;
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeInt(clientVersionID);
        stream.writeUTF(profileName);
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        clientVersionID = stream.readInt();
        profileName = stream.readUTF();
    }
    
    @Override
    public void handle(PlayerLoginConnection handler){
        handler.login(this);
    }
    
}
