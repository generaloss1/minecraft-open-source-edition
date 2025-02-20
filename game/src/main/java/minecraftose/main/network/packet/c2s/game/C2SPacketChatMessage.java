package minecraftose.main.network.packet.c2s.game;

import minecraftose.server.network.ServerPlayerGameConnection;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class C2SPacketChatMessage extends NetPacket<ServerPlayerGameConnection>{
    
        
    public C2SPacketChatMessage(){}
    
    
    public String message;
    
    public C2SPacketChatMessage(String message){
        this();
        this.message = message;
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeUTF(message);
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        message = stream.readUTF();
    }
    
    @Override
    public void handle(ServerPlayerGameConnection handler){
        handler.chatMessage(this);
    }
    
}