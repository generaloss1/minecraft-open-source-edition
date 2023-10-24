package minecraftose.main.nbt.tag.list;

import minecraftose.main.nbt.tag.NbtTag;
import minecraftose.main.nbt.tag.NbtTagID;
import minecraftose.main.nbt.tag.array.NbtByteArray;
import minecraftose.main.nbt.tag.array.NbtIntArray;
import minecraftose.main.nbt.tag.array.NbtLongArray;
import minecraftose.main.nbt.tag.type.*;

import java.util.*;
import java.util.function.Consumer;

public class NbtList<T extends NbtTag<?>> extends NbtTag<List<T>> implements Iterable<T>, Comparable<NbtList<T>>{

    @SafeVarargs
    public NbtList(T... elements){
        super(List.of(elements));
    }

    public NbtList(int initialCapacity){
        super(new ArrayList<>(initialCapacity));
    }

    public NbtList(){
        this(3);
    }

    @Override
    public byte getID(){
        return NbtTagID.LIST;
    }


    public NbtTag<?> get(int index){
        return getValue().get(index);
    }

    public int indexOf(T tag){
        return getValue().indexOf(tag);
    }

    public T set(int index, T element){
        return getValue().set(index, Objects.requireNonNull(element));
    }

    public boolean add(T element){
        return getValue().add(Objects.requireNonNull(element));
    }

    public void add(int index, T element){
        getValue().add(index, Objects.requireNonNull(element));
    }

    public boolean addAll(Collection<T> collection){
        return getValue().addAll(collection);
    }

    public boolean addAll(int index, Collection<T> collection){
        return getValue().addAll(index, collection);
    }

    public boolean contains(T tag){
        return getValue().contains(tag);
    }

    public boolean containsAll(Collection<T> collection){
        return getValue().containsAll(collection);
    }

    public boolean remove(T tag){
        return getValue().remove(tag);
    }

    public void remove(int index){
        getValue().remove(index);
    }


    public int size(){
        return getValue().size();
    }

    public void clear(){
        getValue().clear();
    }

    public void sort(Comparator<T> comparator){
        getValue().sort(comparator);
    }


    @SuppressWarnings("unchecked")
    private boolean addUnchecked(NbtTag<?> tag){
        return add((T) tag);
    }

    public boolean addByte(Byte value){
        return addUnchecked(new NbtByte(value));
    }

    public boolean addShort(Short value){
        return addUnchecked(new NbtShort(value));
    }

    public boolean addInt(Integer value){
        return addUnchecked(new NbtInt(value));
    }

    public boolean addLong(Long value){
        return addUnchecked(new NbtLong(value));
    }

    public boolean addFloat(Float value){
        return addUnchecked(new NbtFloat(value));
    }

    public boolean addDouble(Double value){
        return addUnchecked(new NbtDouble(value));
    }

    public boolean addString(String value){
        return addUnchecked(new NbtString(value));
    }

    public boolean addBool(Boolean value){
        return addUnchecked(new NbtBool(value));
    }

    public boolean addByteArray(byte... array){
        return addUnchecked(new NbtByteArray(array));
    }

    public boolean addIntArray(int... array){
        return addUnchecked(new NbtIntArray(array));
    }

    public boolean addLongArray(long... array){
        return addUnchecked(new NbtLongArray(array));
    }


    @SuppressWarnings("unchecked")
    public NbtList<NbtByte> asByteList(){
        return (NbtList<NbtByte>) this;
    }

    @SuppressWarnings("unchecked")
    public NbtList<NbtShort> asShortList(){
        return (NbtList<NbtShort>) this;
    }

    @SuppressWarnings("unchecked")
    public NbtList<NbtInt> asIntList(){
        return (NbtList<NbtInt>) this;
    }

    @SuppressWarnings("unchecked")
    public NbtList<NbtLong> asLongList(){
        return (NbtList<NbtLong>) this;
    }

    @SuppressWarnings("unchecked")
    public NbtList<NbtFloat> asFloatList(){
        return (NbtList<NbtFloat>) this;
    }

    @SuppressWarnings("unchecked")
    public NbtList<NbtDouble> asDoubleList(){
        return (NbtList<NbtDouble>) this;
    }

    @SuppressWarnings("unchecked")
    public NbtList<NbtString> asStringList(){
        return (NbtList<NbtString>) this;
    }

    @SuppressWarnings("unchecked")
    public NbtList<NbtBool> asBoolList(){
        return (NbtList<NbtBool>) this;
    }

    @SuppressWarnings("unchecked")
    public NbtList<NbtByteArray> asByteArrayList(){
        return (NbtList<NbtByteArray>) this;
    }

    @SuppressWarnings("unchecked")
    public NbtList<NbtIntArray> asIntArrayList(){
        return (NbtList<NbtIntArray>) this;
    }

    @SuppressWarnings("unchecked")
    public NbtList<NbtLongArray> asLongArrayList(){
        return (NbtList<NbtLongArray>) this;
    }

    @SuppressWarnings("unchecked")
    public NbtList<NbtList<?>> asListList(){
        return (NbtList<NbtList<?>>) this;
    }

    @SuppressWarnings("unchecked")
    public NbtList<NbtCompound> asCompoundList(){
        return (NbtList<NbtCompound>) this;
    }


    @Override
    public Iterator<T> iterator(){
        return getValue().iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action){
        getValue().forEach(action);
    }

    @Override
    public Spliterator<T> spliterator(){
        return getValue().spliterator();
    }

    @Override
    public int compareTo(NbtList<T> list){
        return Integer.compare(size(), list.size());
    }

}
