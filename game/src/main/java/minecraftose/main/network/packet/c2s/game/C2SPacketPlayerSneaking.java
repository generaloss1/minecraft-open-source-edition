package minecraftose.main.network.packet.c2s.game;

import jpize.net.tcp.packet.IPacket;
import minecraftose.main.entity.Player;
import minecraftose.server.network.ServerPlayerGameConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;
import java.util.UUID;

public class C2SPacketPlayerSneaking extends IPacket<ServerPlayerGameConnection>{
    
    public static final int PACKET_ID = 16;
    
    public C2SPacketPlayerSneaking(){
        super(PACKET_ID);
    }
    
    
    public UUID playerUUID;
    public boolean sneaking;
    
    public C2SPacketPlayerSneaking(Player player){
        this();
        this.playerUUID = player.getUUID();
        this.sneaking = player.isSneaking();
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeUUID(playerUUID);
        stream.writeBoolean(sneaking);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        playerUUID = stream.readUUID();
        sneaking = stream.readBoolean();
    }
    
    @Override
    public void handle(ServerPlayerGameConnection handler){
        handler.sneaking(this);
    }
    
}