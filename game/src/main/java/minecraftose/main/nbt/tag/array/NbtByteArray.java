package minecraftose.main.nbt.tag.array;

import minecraftose.main.nbt.tag.NbtArray;
import minecraftose.main.nbt.tag.NbtTagID;

public class NbtByteArray extends NbtArray<byte[]>{

    public NbtByteArray(byte[] value){
        super(value);
    }

    @Override
    public byte getID(){
        return NbtTagID.BYTE_ARRAY;
    }

}
