package minecraftose.main.registry;

import minecraftose.main.audio.Sound;
import minecraftose.main.biome.Biome;
import minecraftose.main.block.Block;
import minecraftose.main.item.BlockItem;
import minecraftose.main.item.Item;
import minecraftose.server.level.gen.ChunkGenerator;

public class Registry{

    public static final RegistryInstance<Integer, Block> block = new RegistryInstance<>();
    public static final RegistryInstance<Integer, Item> item = new RegistryInstance<>();
    public static final RegistryInstance<Block, BlockItem> block_item = new RegistryInstance<>();
    public static final RegistryInstance<Byte, Biome> biome = new RegistryInstance<>();
    public static final RegistryInstance<Byte, ChunkGenerator> generator = new RegistryInstance<>();
    public static final RegistryInstance<Byte, Sound> sound = new RegistryInstance<>();

}
