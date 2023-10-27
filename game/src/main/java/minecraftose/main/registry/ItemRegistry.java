package minecraftose.main.registry;

import minecraftose.client.block.Block;
import minecraftose.main.item.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ItemRegistry{

    private final Map<Integer, Item> items;

    public ItemRegistry(){
        items = new HashMap<>();
    }


    public Collection<Item> collection(){
        return items.values();
    }

    public Item get(int ID){
        return items.get(ID);
    }

    public void register(Item item){
        items.put(item.getID(), item);
    }

}
