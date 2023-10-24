package minecraftose.main.nbt.tag.type;

import minecraftose.main.nbt.tag.NbtTag;
import minecraftose.main.nbt.tag.NbtTagID;

public class NbtFloat extends NbtTag<Float>{

    public NbtFloat(Float value){
        super(value);
    }

    @Override
    public byte getID(){
        return NbtTagID.FLOAT;
    }

}
