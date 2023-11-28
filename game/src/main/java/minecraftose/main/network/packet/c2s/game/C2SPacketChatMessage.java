package minecraftose.main.network.packet.c2s.game;

import jpize.net.tcp.packet.IPacket;
import minecraftose.server.network.ServerPlayerGameConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class C2SPacketChatMessage extends IPacket<ServerPlayerGameConnection>{
    
    public static final int PACKET_ID = 19;
    
    public C2SPacketChatMessage(){
        super(PACKET_ID);
    }
    
    
    public String message;
    
    public C2SPacketChatMessage(String message){
        this();
        this.message = message;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(message);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        message = stream.readUTF();
    }
    
    @Override
    public void handle(ServerPlayerGameConnection handler){
        handler.chatMessage(this);
    }
    
}