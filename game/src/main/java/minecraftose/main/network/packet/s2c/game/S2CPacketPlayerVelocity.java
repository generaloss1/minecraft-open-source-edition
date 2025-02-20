package minecraftose.main.network.packet.s2c.game;

import jpize.util.math.vector.Vec3f;
import minecraftose.client.network.ClientPacketHandler;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class S2CPacketPlayerVelocity extends NetPacket<ClientPacketHandler>{

    
    public S2CPacketPlayerVelocity(){}


    public Vec3f velocity;

    public S2CPacketPlayerVelocity(Vec3f velocity){
        this();
        this.velocity = velocity;
    }


    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeVec3f(velocity);
    }

    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        velocity = stream.readVec3f();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.updateVelocity(this);
    }

}