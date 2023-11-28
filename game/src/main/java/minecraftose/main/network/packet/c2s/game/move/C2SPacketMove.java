package minecraftose.main.network.packet.c2s.game.move;

import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import minecraftose.client.entity.LocalPlayer;
import minecraftose.server.network.ServerPlayerGameConnection;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;
import jpize.physic.utils.Velocity3f;

import java.io.IOException;

public class C2SPacketMove extends IPacket<ServerPlayerGameConnection>{
    
    public static final int PACKET_ID = 9;
    
    public C2SPacketMove(){
        super(PACKET_ID);
    }
    
    
    public Vec3f position;
    public Velocity3f velocity;
    
    public C2SPacketMove(LocalPlayer localPlayer){
        this();
        position = localPlayer.getPosition();
        velocity = localPlayer.getVelocity();
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeVec3f(position);
        stream.writeVec3f(velocity);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        position = stream.readVec3f();
        velocity = new Velocity3f(stream.readVec3f());
    }
    
    @Override
    public void handle(ServerPlayerGameConnection handler){
        handler.move(this);
    }
    
}