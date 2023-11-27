package minecraftose.main.network.packet.c2s.game.move;

import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import jpize.physic.utils.Velocity3f;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;
import minecraftose.client.entity.LocalPlayer;
import minecraftose.server.network.PlayerGameConnection;

import java.io.IOException;

public class C2SPacketMoveAndRot extends IPacket<PlayerGameConnection>{

    public static final int PACKET_ID = 24;

    public C2SPacketMoveAndRot(){
        super(PACKET_ID);
    }


    public Vec3f position;
    public Velocity3f velocity;
    public EulerAngles rotation;

    public C2SPacketMoveAndRot(LocalPlayer localPlayer){
        this();
        position = localPlayer.getPosition();
        velocity = localPlayer.getVelocity();
        rotation = localPlayer.getRotation();
    }


    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeVec3f(position);
        stream.writeVec3f(velocity);
        stream.writeEulerAngles(rotation);
    }

    @Override
    public void read(JpizeInputStream stream) throws IOException{
        position = stream.readVec3f();
        velocity = new Velocity3f(stream.readVec3f());
        rotation = stream.readEulerAngles();
    }

    @Override
    public void handle(PlayerGameConnection handler){
        handler.moveAndRotate(this);
    }

}
