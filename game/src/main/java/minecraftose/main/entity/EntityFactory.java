package minecraftose.main.entity;

import minecraftose.main.level.Level;

public interface EntityFactory<T extends Entity>{
    
    T create(Level level);
    
}
