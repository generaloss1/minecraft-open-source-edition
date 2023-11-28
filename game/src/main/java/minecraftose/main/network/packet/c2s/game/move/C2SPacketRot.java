package minecraftose.main.network.packet.c2s.game.move;

import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import jpize.physic.utils.Velocity3f;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;
import minecraftose.client.entity.LocalPlayer;
import minecraftose.server.network.ServerPlayerGameConnection;

import java.io.IOException;

public class C2SPacketRot extends IPacket<ServerPlayerGameConnection>{

    public static final int PACKET_ID = 25;

    public C2SPacketRot(){
        super(PACKET_ID);
    }


    public Vec3f position;
    public EulerAngles rotation;
    public Velocity3f velocity;

    public C2SPacketRot(LocalPlayer localPlayer){
        this();
        rotation = localPlayer.getRotation();
    }


    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeEulerAngles(rotation);
    }

    @Override
    public void read(JpizeInputStream stream) throws IOException{
        rotation = stream.readEulerAngles();
    }

    @Override
    public void handle(ServerPlayerGameConnection handler){
        handler.rotate(this);
    }

}
