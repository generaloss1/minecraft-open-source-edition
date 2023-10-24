package minecraftose.main.nbt.tag.type;

import minecraftose.main.nbt.tag.NbtTag;
import minecraftose.main.nbt.tag.NbtTagID;

public class NbtShort extends NbtTag<Short>{

    public NbtShort(Short value){
        super(value);
    }

    @Override
    public byte getID(){
        return NbtTagID.SHORT;
    }

}
