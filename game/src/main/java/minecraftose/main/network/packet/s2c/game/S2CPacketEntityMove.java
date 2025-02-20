package minecraftose.main.network.packet.s2c.game;

import jpize.util.math.EulerAngles;
import jpize.util.math.vector.Vec3f;
import minecraftose.client.network.ClientPacketHandler;
import minecraftose.server.player.ServerPlayer;

import java.io.IOException;
import java.util.UUID;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class S2CPacketEntityMove extends NetPacket<ClientPacketHandler>{

    public S2CPacketEntityMove(){}

    public UUID uuid;
    public Vec3f position;
    public EulerAngles rotation;
    public Vec3f velocity;

    public S2CPacketEntityMove(ServerPlayer serverPlayer){
        this();
        uuid = serverPlayer.getUUID();
        position = serverPlayer.getPosition();
        rotation = serverPlayer.getRotation();
        velocity = serverPlayer.getVelocity();
    }


    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeUUID(uuid);
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
        stream.writeVec3f(velocity);
    }

    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        uuid = stream.readUUID();
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
        velocity = new Vec3f(stream.readVec3f());
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.entityMove(this);
    }

}