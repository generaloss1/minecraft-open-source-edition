package minecraftose.client.control.camera;

import jpize.math.vecmath.vector.Vec3f;
import minecraftose.client.Minecraft;
import minecraftose.client.options.KeyMapping;
import minecraftose.client.options.Options;
import minecraftose.client.control.PlayerController;
import minecraftose.client.entity.LocalPlayer;

public class HorizontalMoveController{
    
    private final PlayerController playerController;
    private final Vec3f motion;

    public HorizontalMoveController(PlayerController playerController){
        this.playerController = playerController;
        motion = new Vec3f();
    }
    
    public void update(){
        motion.zero();
        
        final Minecraft minecraft = playerController.getPlayer().getMinecraft();
        final Options options = minecraft.getOptions();
        
        final LocalPlayer player = minecraft.getPlayer();
        final Vec3f dir = player.getRotation().getDirectionHorizontal();
        
        if(options.getKey(KeyMapping.FORWARD).isPressed())
            motion.add(dir);
        if(options.getKey(KeyMapping.BACK).isPressed())
            motion.sub(dir);
        if(options.getKey(KeyMapping.LEFT).isPressed())
            motion.sub(dir.z, 0, -dir.x);
        if(options.getKey(KeyMapping.RIGHT).isPressed())
            motion.add(dir.z, 0, -dir.x);
        
        motion.nor();
    }
    
    public Vec3f getMotion(){
        return motion;
    }
    
}
