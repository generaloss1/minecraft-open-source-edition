package minecraftose.main.network.packet.c2s.game;

import minecraftose.main.entity.Player;
import minecraftose.server.network.ServerPlayerGameConnection;

import java.io.IOException;
import java.util.UUID;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class C2SPacketPlayerSneaking extends NetPacket<ServerPlayerGameConnection>{
    
        
    public C2SPacketPlayerSneaking(){}
    
    
    public UUID playerUUID;
    public boolean sneaking;
    
    public C2SPacketPlayerSneaking(Player player){
        this();
        this.playerUUID = player.getUUID();
        this.sneaking = player.isSneaking();
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeUUID(playerUUID);
        stream.writeBoolean(sneaking);
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        playerUUID = stream.readUUID();
        sneaking = stream.readBoolean();
    }
    
    @Override
    public void handle(ServerPlayerGameConnection handler){
        handler.sneaking(this);
    }
    
}