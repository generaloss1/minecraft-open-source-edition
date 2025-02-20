package minecraftose.main.network.packet.s2c.game;

import minecraftose.client.network.ClientPacketHandler;
import minecraftose.main.entity.Player;

import java.io.IOException;
import java.util.UUID;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class S2CPacketPlayerSneaking extends NetPacket<ClientPacketHandler>{
    
        
    public S2CPacketPlayerSneaking(){}
    
    
    public UUID playerUUID;
    public boolean sneaking;
    
    public S2CPacketPlayerSneaking(Player player){
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
    public void handle(ClientPacketHandler handler){
        handler.playerSneaking(this);
    }
    
}