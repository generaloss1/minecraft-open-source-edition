package minecraftose.main.registry;

import minecraftose.main.block.Block;
import minecraftose.main.item.BlockItem;
import minecraftose.main.item.Item;

public class Registry{

    public static final RegistryInstance<Integer, Block> block = new RegistryInstance<>();
    public static final RegistryInstance<Integer, Item> item = new RegistryInstance<>();
    public static final RegistryInstance<Block, BlockItem> block_item = new RegistryInstance<>();

}
