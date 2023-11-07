package minecraftose.main.item;

import minecraftose.main.block.Block;
import minecraftose.main.registry.Registry;

public class ItemBuilder{

    private final int ID;

    private int maxStack = 64;
    private int maxDamage = 0;
    private ItemRarity rarity = ItemRarity.COMMON;
    private final String name;
    private String description = "";

    private Block block;

    public ItemBuilder(int ID, String name){
        this.ID = ID;
        this.name = name;
    }

    public ItemBuilder(int ID, String name, Block block){
        this(ID, name);
        this.block = block;
    }


    public ItemBuilder stack(int maxStack){
        this.maxStack = maxStack;
        return this;
    }

    public ItemBuilder damage(int maxDamage){
        this.maxDamage = maxDamage;
        return this;
    }

    public ItemBuilder damage(ItemRarity rarity){
        this.rarity = rarity;
        return this;
    }

    public ItemBuilder desc(String description){
        this.description = description;
        return this;
    }


    @SuppressWarnings("unchecked cast")
    public <I extends Item> I register(){
        final I item;

        if(block != null){
            // Block-Item
            final BlockItem blockItem = new BlockItem(block, maxStack, maxDamage, rarity, name, description);

            Registry.block_item.register(block, blockItem);
            item = (I) blockItem;
        }else{
            // Common Item
            item = (I) new Item(maxStack, maxDamage, rarity, name, description);
        }

        // Register
        Registry.item.register(ID, item);

        return item;
    }

}
