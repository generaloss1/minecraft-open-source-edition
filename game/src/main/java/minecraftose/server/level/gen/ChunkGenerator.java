package minecraftose.server.level.gen;

import minecraftose.main.registry.Registry;
import minecraftose.server.level.chunk.ChunkS;

public abstract class ChunkGenerator{

    public ChunkGenerator(){
        Registry.generator.register(this.getID(), this);
    }

    public void generate(ChunkS chunk){ }

    public void decorate(ChunkS chunk){ }

    public abstract byte getID();
    
}
