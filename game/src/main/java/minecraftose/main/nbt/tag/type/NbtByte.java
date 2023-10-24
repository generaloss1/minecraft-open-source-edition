package minecraftose.main.nbt.tag.type;

import minecraftose.main.nbt.tag.NbtTag;
import minecraftose.main.nbt.tag.NbtTagID;

public class NbtByte extends NbtTag<Byte>{

    public NbtByte(Byte value){
        super(value);
    }

    @Override
    public byte getID(){
        return NbtTagID.BYTE;
    }

}
