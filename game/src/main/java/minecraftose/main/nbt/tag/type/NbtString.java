package minecraftose.main.nbt.tag.type;

import minecraftose.main.nbt.tag.NbtTag;
import minecraftose.main.nbt.tag.NbtTagID;

public class NbtString extends NbtTag<String>{

    public NbtString(String value){
        super(value);
    }

    @Override
    public byte getID(){
        return NbtTagID.STRING;
    }

}
