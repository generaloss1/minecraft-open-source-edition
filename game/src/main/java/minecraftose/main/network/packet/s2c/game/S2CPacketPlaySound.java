package minecraftose.main.network.packet.s2c.game;

import jpize.util.math.vector.Vec3f;
import minecraftose.client.network.ClientPacketHandler;
import minecraftose.main.audio.Sound;
import minecraftose.main.registry.Registry;

import java.io.IOException;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class S2CPacketPlaySound extends NetPacket<ClientPacketHandler>{

    
    public S2CPacketPlaySound(){}


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
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeByte(sound.getID());
        stream.writeFloat(volume);
        stream.writeFloat(pitch);
        stream.writeFloat(x);
        stream.writeFloat(y);
        stream.writeFloat(z);
    }

    @Override
    public void read(ExtDataInputStream stream) throws IOException{
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
