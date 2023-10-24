package minecraftose.main.nbt.tag.array;

import minecraftose.main.nbt.tag.NbtArray;
import minecraftose.main.nbt.tag.NbtTagID;

public class NbtLongArray extends NbtArray<long[]>{

    public NbtLongArray(long[] value){
        super(value);
    }

    @Override
    public byte getID(){
        return NbtTagID.LONG_ARRAY;
    }

}
