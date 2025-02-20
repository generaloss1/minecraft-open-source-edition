package minecraftose.main.network.packet.c2s.game.move;

import jpize.util.math.vector.Vec3f;
import minecraftose.client.entity.LocalPlayer;
import minecraftose.server.network.ServerPlayerGameConnection;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class C2SPacketMove extends NetPacket<ServerPlayerGameConnection>{
    
        
    public C2SPacketMove(){}
    
    
    public Vec3f position;
    public Vec3f velocity;
    
    public C2SPacketMove(LocalPlayer localPlayer){
        this();
        position = localPlayer.getPosition();
        velocity = localPlayer.getVelocity();
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeVec3f(position);
        stream.writeVec3f(velocity);
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        position = stream.readVec3f();
        velocity = new Vec3f(stream.readVec3f());
    }
    
    @Override
    public void handle(ServerPlayerGameConnection handler){
        handler.move(this);
    }
    
}