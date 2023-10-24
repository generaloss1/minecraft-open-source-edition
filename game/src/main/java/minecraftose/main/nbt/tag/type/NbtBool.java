package minecraftose.main.nbt.tag.type;

import minecraftose.main.nbt.tag.NbtTag;
import minecraftose.main.nbt.tag.NbtTagID;

public class NbtBool extends NbtTag<Boolean>{

    public NbtBool(Boolean value){
        super(value);
    }

    @Override
    public byte getID(){
        return NbtTagID.BOOL;
    }

}
