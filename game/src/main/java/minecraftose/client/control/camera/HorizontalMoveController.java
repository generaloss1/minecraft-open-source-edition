package minecraftose.client.control.camera;

import jpize.util.math.vector.Vec3f;
import minecraftose.client.Minecraft;
import minecraftose.client.control.PlayerInput;
import minecraftose.client.options.KeyMapping;
import minecraftose.client.options.Options;

public class HorizontalMoveController {

    private static final Vec3f FORWARD = new Vec3f(1, 0, 0);
    private static final Vec3f LEFT = new Vec3f(0, 0, 1);
    
    private final PlayerInput playerController;
    private final Vec3f motion;

    public HorizontalMoveController(PlayerInput playerInput){
        this.playerController = playerInput;
        motion = new Vec3f();
    }
    
    public void update(){
        motion.zero();
        
        final Minecraft minecraft = playerController.getPlayer().getMinecraft();
        final Options options = minecraft.getOptions();
        final Vec3f forward = minecraft.getPlayer().getRotation().getDirectionHorizontal(new Vec3f());
        final Vec3f left = forward.copy().rotateY(90);

        if(options.getKey(KeyMapping.FORWARD).pressed()) motion.add(forward);
        if(options.getKey(KeyMapping.BACK).pressed())    motion.sub(forward);
        if(options.getKey(KeyMapping.LEFT).pressed())    motion.add(left);
        if(options.getKey(KeyMapping.RIGHT).pressed())   motion.sub(left);
        
        motion.nor();
    }
    
    public Vec3f getMotion(){
        return motion;
    }
    
}
