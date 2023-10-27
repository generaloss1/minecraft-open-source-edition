package minecraftose.main.item;

import minecraftose.main.nbt.tag.list.NbtCompound;

public class ItemStack{

    public static final ItemStack AIR = new ItemStack(Items.AIR, 0);


    private final Item item;
    private int count;
    private final NbtCompound nbt;

    public ItemStack(Item item, int count){
        this.item = item;
        this.count = count;
        // NBT
        this.nbt = new NbtCompound();
        this.nbt.putString("displayName", item.getName());
        this.nbt.putString("description", item.getDescription());
    }

    public ItemStack(Item item){
        this(item, 1);
    }


    public Item getItem(){
        return item;
    }

    public int getCount(){
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }

    public NbtCompound getNbt(){
        return nbt;
    }

}
