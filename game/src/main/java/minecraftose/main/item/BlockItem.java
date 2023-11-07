package minecraftose.main.item;

import minecraftose.main.block.Block;

public class BlockItem extends Item{

    private final Block block;

    public BlockItem(Block block, int maxStack, int maxDamage, ItemRarity rarity, String name, String description){
        super(maxStack, maxDamage, rarity, name, description);
        this.block = block;
    }

    public Block getBlock(){
        return block;
    }

}
