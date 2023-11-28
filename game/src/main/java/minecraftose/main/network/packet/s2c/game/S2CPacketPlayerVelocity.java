package minecraftose.main.network.packet.s2c.game;

import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;
import minecraftose.client.network.ClientPacketHandler;

import java.io.IOException;

public class S2CPacketPlayerVelocity extends IPacket<ClientPacketHandler>{

    public static final int PACKET_ID = 27;

    public S2CPacketPlayerVelocity(){
        super(PACKET_ID);
    }


    public Vec3f velocity;

    public S2CPacketPlayerVelocity(Vec3f velocity){
        this();
        this.velocity = velocity;
    }


    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeVec3f(velocity);
    }

    @Override
    public void read(JpizeInputStream stream) throws IOException{
        velocity = stream.readVec3f();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.updateVelocity(this);
    }

}