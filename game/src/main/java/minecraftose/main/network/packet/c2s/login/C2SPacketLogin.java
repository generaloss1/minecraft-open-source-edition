package minecraftose.main.network.packet.c2s.login;

import jpize.net.tcp.packet.IPacket;
import minecraftose.server.network.PlayerLoginConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class C2SPacketLogin extends IPacket<PlayerLoginConnection>{
    
    public static final int PACKET_ID = 6;
    
    public C2SPacketLogin(){
        super(PACKET_ID);
    }
    
    
    public int clientVersionID;
    public String profileName;
    
    public C2SPacketLogin(int clientVersionID, String profileName){
        this();
        this.clientVersionID = clientVersionID;
        this.profileName = profileName;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeInt(clientVersionID);
        stream.writeUTF(profileName);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        clientVersionID = stream.readInt();
        profileName = stream.readUTF();
    }
    
    @Override
    public void handle(PlayerLoginConnection handler){
        handler.login(this);
    }
    
}
