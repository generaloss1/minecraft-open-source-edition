package minecraftose.main.network.packet.s2c.game;

import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import minecraftose.client.network.ClientPacketHandler;
import minecraftose.main.audio.Sound;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;
import minecraftose.main.registry.Registry;

import java.io.IOException;

public class S2CPacketPlaySound extends IPacket<ClientPacketHandler>{

    public static final int PACKET_ID = 23;

    public S2CPacketPlaySound(){
        super(PACKET_ID);
    }


    public Sound sound;
    public float volume, pitch, x, y, z;

    public S2CPacketPlaySound(Sound sound, float volume, float pitch, float x, float y, float z){
        this();
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public S2CPacketPlaySound(Sound sound, float volume, float pitch, Vec3f position){
        this(sound, volume, pitch, position.x, position.y, position.z);
    }


    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeByte(sound.getID());
        stream.writeFloat(volume);
        stream.writeFloat(pitch);
        stream.writeFloat(x);
        stream.writeFloat(y);
        stream.writeFloat(z);
    }

    @Override
    public void read(JpizeInputStream stream) throws IOException{
        sound = Registry.sound.get(stream.readByte());
        volume = stream.readFloat();
        pitch = stream.readFloat();
        x = stream.readFloat();
        y = stream.readFloat();
        z = stream.readFloat();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.playSound(this);
    }

}
