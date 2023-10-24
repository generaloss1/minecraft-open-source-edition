package minecraftose.main.nbt.tag.type;

import minecraftose.main.nbt.tag.NbtTag;
import minecraftose.main.nbt.tag.NbtTagID;

public class NbtInt extends NbtTag<Integer>{

    public NbtInt(Integer value){
        super(value);
    }

    @Override
    public byte getID(){
        return NbtTagID.INT;
    }

}
