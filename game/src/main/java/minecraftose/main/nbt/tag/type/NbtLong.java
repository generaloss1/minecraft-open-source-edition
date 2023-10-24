package minecraftose.main.nbt.tag.type;

import minecraftose.main.nbt.tag.NbtTag;
import minecraftose.main.nbt.tag.NbtTagID;

public class NbtLong extends NbtTag<Long>{

    public NbtLong(Long value){
        super(value);
    }

    @Override
    public byte getID(){
        return NbtTagID.LONG;
    }

}
