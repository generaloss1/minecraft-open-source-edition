package minecraftose.main.nbt.tag;

public abstract class NbtArray<T> extends NbtTag<T>{

    public NbtArray(T value){
        super(value);
        if(!value.getClass().isArray())
            throw new IllegalArgumentException("Nbt array value must be array");
    }

}
