package minecraftose.main.network.packet.c2s.game.move;

import jpize.util.math.EulerAngles;
import jpize.util.math.vector.Vec3f;
import minecraftose.client.entity.LocalPlayer;
import minecraftose.server.network.ServerPlayerGameConnection;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class C2SPacketRot extends NetPacket<ServerPlayerGameConnection>{

    
    public C2SPacketRot(){}


    public Vec3f position;
    public EulerAngles rotation;
    public Vec3f velocity;

    public C2SPacketRot(LocalPlayer localPlayer){
        this();
        rotation = localPlayer.getRotation();
    }


    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeEulerAngles(rotation);
    }

    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        rotation = stream.readEulerAngles();
    }

    @Override
    public void handle(ServerPlayerGameConnection handler){
        handler.rotate(this);
    }

}
