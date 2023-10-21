package minecraftose.main.registry;

import minecraftose.client.Minecraft;
import minecraftose.client.block.Block;
import minecraftose.client.resources.GameResources;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BlockRegistry{

    private final Map<Byte, Block> blocks;

    public BlockRegistry(){
        blocks = new HashMap<>();
    }

    public void loadBlocks(Minecraft session){
        System.out.println("[Resources] Load Blocks");
        final GameResources resources = session.getRenderer().getSession().getResources();
        for(Block block: blocks.values())
            block.load(resources);
    }

    public Collection<Block> collection(){
        return blocks.values();
    }

    public Block get(byte ID){
        return blocks.get(ID);
    }

    public void register(Block properties){
        blocks.put(properties.getID(), properties);
    }

}
