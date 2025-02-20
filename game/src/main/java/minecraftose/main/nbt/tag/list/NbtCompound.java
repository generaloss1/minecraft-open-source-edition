package minecraftose.main.nbt.tag.list;

import minecraftose.main.nbt.tag.NbtTag;
import minecraftose.main.nbt.tag.NbtTagID;
import minecraftose.main.nbt.tag.array.NbtByteArray;
import minecraftose.main.nbt.tag.array.NbtIntArray;
import minecraftose.main.nbt.tag.array.NbtLongArray;
import minecraftose.main.nbt.tag.type.*;

import java.util.*;

public class NbtCompound extends NbtTag<HashMap<String, NbtTag<?>>> implements Iterable<HashMap.Entry<String, NbtTag<?>>>, Comparable<NbtCompound>{

    public NbtCompound(){
        super(new HashMap<>());
    }

    public NbtCompound(int initialCapacity){
        super(new HashMap<>(initialCapacity));
    }

    @Override
    public byte getID(){
        return NbtTagID.COMPOUND;
    }


    public int size(){
        return getValue().size();
    }

    public void clear(){
        getValue().clear();
    }

    public Collection<NbtTag<?>> values(){
        return getValue().values();
    }



    public NbtTag<?> remove(String key){
        return getValue().remove(key);
    }

    public boolean remove(String key, NbtTag<?> value){
        return getValue().remove(key, value);
    }


    public boolean contains(String key){
        return getValue().containsKey(key);
    }

    public boolean contains(NbtTag<?> value){
        return getValue().containsValue(value);
    }


    public NbtTag<?> get(String key){
        return getValue().get(key);
    }

    public NbtTag<?> getOrDefault(String key, NbtTag<?> defaultTag){
        return getValue().getOrDefault(key, defaultTag);
    }

    public NbtByte getByte(String key){
        return (NbtByte) get(key);
    }

    public NbtShort getShort(String key){
        return (NbtShort) get(key);
    }

    public NbtInt getInt(String key){
        return (NbtInt) get(key);
    }

    public NbtLong getLong(String key){
        return (NbtLong) get(key);
    }

    public NbtFloat getFloat(String key){
        return (NbtFloat) get(key);
    }

    public NbtDouble getDouble(String key){
        return (NbtDouble) get(key);
    }

    public NbtString getString(String key){
        return (NbtString) get(key);
    }

    public NbtBool getBool(String key){
        return (NbtBool) get(key);
    }

    public NbtByteArray getByteArr(String key){
        return (NbtByteArray) get(key);
    }

    public NbtIntArray getIntArr(String key){
        return (NbtIntArray) get(key);
    }

    public NbtLongArray getLongArr(String key){
        return (NbtLongArray) get(key);
    }

    public NbtList<?> getList(String key){
        return (NbtList<?>) get(key);
    }

    public NbtCompound getCompound(String key){
        return (NbtCompound) get(key);
    }


    public NbtTag<?> put(String key, NbtTag<?> value){
        return getValue().put(key, value);
    }

    public NbtByte putByte(String key, Byte value){
        return (NbtByte) put(key, new NbtByte(value));
    }

    public NbtShort putShort(String key, Short value){
        return (NbtShort) put(key, new NbtShort(value));
    }

    public NbtInt putInt(String key, Integer value){
        return (NbtInt) put(key, new NbtInt(value));
    }

    public NbtLong putLong(String key, Long value){
        return (NbtLong) put(key, new NbtLong(value));
    }

    public NbtFloat putFloat(String key, Float value){
        return (NbtFloat) put(key, new NbtFloat(value));
    }

    public NbtDouble putDouble(String key, Double value){
        return (NbtDouble) put(key, new NbtDouble(value));
    }

    public NbtString putString(String key, String value){
        return (NbtString) put(key, new NbtString(value));
    }

    public NbtBool putBool(String key, Boolean value){
        return (NbtBool) put(key, new NbtBool(value));
    }

    public NbtByteArray putByteArray(String key, byte... array){
        return (NbtByteArray) put(key, new NbtByteArray(array));
    }

    public NbtIntArray putIntArray(String key, int... array){
        return (NbtIntArray) put(key, new NbtIntArray(array));
    }

    public NbtLongArray putLongArray(String key, long... array){
        return (NbtLongArray) put(key, new NbtLongArray(array));
    }


    @Override
    public Iterator<Map.Entry<String, NbtTag<?>>> iterator(){
        return getValue().entrySet().iterator();
    }

    @Override
    public Spliterator<Map.Entry<String, NbtTag<?>>> spliterator(){
        return getValue().entrySet().spliterator();
    }

    @Override
    public int compareTo(NbtCompound compound){
        return Integer.compare(size(), compound.size());
    }

}
