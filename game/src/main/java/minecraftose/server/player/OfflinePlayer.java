package minecraftose.server.player;


import jpize.util.math.vector.Vec3f;

public class OfflinePlayer{
    
    private final String name;
    private final String worldName;
    private final Vec3f position;
    
    public OfflinePlayer(String name, String worldName, Vec3f position){
        this.name = name;
        this.worldName = worldName;
        this.position = position;
    }
    
    
    public String getName(){
        return name;
    }
    
    public String getLevelName(){
        return worldName;
    }
    
    public Vec3f getPosition(){
        return position;
    }
    
}
