package minecraftose.main.inventory;

import minecraftose.main.item.ItemStack;

public class PlayerInventory extends Inventory{

    private int selectedSlot;

    public PlayerInventory(){
        super(32);
    }

    public int getSelectedSlot(){
        return selectedSlot;
    }

    public void setSelectedSlot(int selectedSlot){
        this.selectedSlot = selectedSlot;
    }

    public ItemStack getSelectedItemStack(){
        return getItemStack(selectedSlot);
    }

    public void setSelectedItemStack(ItemStack stack){
        setItemStack(selectedSlot, stack);
    }

}
