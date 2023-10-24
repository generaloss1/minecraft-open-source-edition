package minecraftose.main.nbt.tag.array;

import minecraftose.main.nbt.tag.NbtArray;
import minecraftose.main.nbt.tag.NbtTagID;

public class NbtIntArray extends NbtArray<int[]>{

    public NbtIntArray(int[] value){
        super(value);
    }

    @Override
    public byte getID(){
        return NbtTagID.INT_ARRAY;
    }

}
