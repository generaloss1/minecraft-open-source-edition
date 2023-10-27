package minecraftose.main.inventory;

import minecraftose.main.item.ItemStack;

public abstract class Container{

    protected final int slots;

    public Container(int slots){
        this.slots = slots;
    }


    public abstract ItemStack getItemStack(int slot);

    public abstract void setItemStack(int slot, ItemStack stack);

}
