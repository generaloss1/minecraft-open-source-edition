package minecraftose.main.network.packet.s2c.game;

import jpize.util.math.EulerAngles;
import jpize.util.math.vector.Vec3f;
import minecraftose.client.network.ClientPacketHandler;
import minecraftose.main.entity.Entity;
import minecraftose.main.entity.EntityType;

import java.io.IOException;
import java.util.UUID;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

public class S2CPacketSpawnEntity extends NetPacket<ClientPacketHandler>{

    public S2CPacketSpawnEntity(){}
    
    public EntityType<?> type;
    public UUID uuid;
    public Vec3f position;
    public EulerAngles rotation;

    public S2CPacketSpawnEntity(Entity entity){
        this();
        type = entity.getEntityType();
        uuid = entity.getUUID();
        position = entity.getPosition();
        rotation = entity.getRotation();
    }


    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        stream.writeInt(type.getID());
        stream.writeUTF(uuid.toString());
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
    }

    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        type = EntityType.fromEntityID(stream.readInt());
        uuid = UUID.fromString(stream.readUTF());
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.spawnEntity(this);
    }
    
}