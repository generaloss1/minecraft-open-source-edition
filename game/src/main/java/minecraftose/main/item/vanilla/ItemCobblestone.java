package minecraftose.main.item.vanilla;

import minecraftose.client.block.Block;
import minecraftose.client.block.Blocks;
import minecraftose.main.item.BlockItem;

public class ItemCobblestone extends BlockItem{

    @Override
    public int maxStack(){
        return 64;
    }

    @Override
    public int getID(){
        return 1;
    }

    @Override
    public String getName(){
        return "Cobblestone";
    }

    @Override
    public String getDescription(){
        return "";
    }

    @Override
    public Block getBlock(){
        return Blocks.COBBLESTONE;
    }
}
