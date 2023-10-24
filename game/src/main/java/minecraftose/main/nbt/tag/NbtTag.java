package minecraftose.main.nbt.tag;

import java.util.Objects;

public abstract class NbtTag<T>{

    public static NbtTag<Void> NULL_TAG = new NbtTag<>(null){
        public byte getID(){
            return 0;
        }
        public void setValue(Void value){ }
    };


    protected T value;

    public NbtTag(T value){
        setValue(value);
    }


    public abstract byte getID();


    public T getValue(){
        return value;
    }

    public void setValue(T value){
        this.value = Objects.requireNonNull(value);
    }


    @Override
    public boolean equals(Object object){
        return object != null && object.getClass() == this.getClass();
    }

    @Override
    public int hashCode(){
        return value.hashCode();
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }

}
