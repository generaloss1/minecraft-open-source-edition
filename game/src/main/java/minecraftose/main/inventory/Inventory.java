package minecraftose.main.inventory;

import minecraftose.main.item.ItemStack;

public abstract class Inventory extends Container{

    private final ItemStack[] items;

    public Inventory(int slots){
        super(slots);
        this.items = new ItemStack[slots];

        // Fill with air
        for(int i = 0; i < slots; i++)
            items[i] = ItemStack.AIR;
    }

    @Override
    public ItemStack getItemStack(int slot){
        return items[slot];
    }

    @Override
    public void setItemStack(int slot, ItemStack stack){
        items[slot] = stack;
    }

}
