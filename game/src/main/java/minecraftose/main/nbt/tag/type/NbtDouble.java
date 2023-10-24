package minecraftose.main.nbt.tag.type;

import minecraftose.main.nbt.tag.NbtTag;
import minecraftose.main.nbt.tag.NbtTagID;

public class NbtDouble extends NbtTag<Double>{

    public NbtDouble(Double value){
        super(value);
    }

    @Override
    public byte getID(){
        return NbtTagID.DOUBLE;
    }

}
