package minecraftose.main.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RegistryInstance<K, V>{

    private final Map<K, V> map;

    public RegistryInstance(){
        this.map = new HashMap<>();
    }


    public Collection<V> collection(){
        return map.values();
    }

    public V get(K key){
        return map.get(key);
    }

    public void register(K key, V value){
        map.put(key, value);
    }

}
