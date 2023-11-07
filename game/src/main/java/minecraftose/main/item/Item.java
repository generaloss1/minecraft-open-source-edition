package minecraftose.main.item;

public class Item{

    private final int maxStack;
    private final int maxDamage;
    private final ItemRarity rarity;
    private final String name;
    private final String description;

    public Item(int maxStack, int maxDamage, ItemRarity rarity, String name, String description){
        this.maxStack = maxStack;
        this.maxDamage = maxDamage;
        this.rarity = rarity;
        this.name = name;
        this.description = description;
    }


    public int maxStack(){
        return maxStack;
    }

    public int maxDamage(){
        return maxDamage;
    }

    public ItemRarity getRarity(){
        return rarity;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public BlockItem asBlockItem(){
        return (BlockItem) this;
    }

}
