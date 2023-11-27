package minecraftose.client.control.camera.perspective;

import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import minecraftose.client.entity.LocalPlayer;

public class FirstPersonPlayerCameraTarget implements CameraTarget{
    
    private final LocalPlayer targetPlayer;
    private final Vec3f position;
    private final EulerAngles rotation;
    
    public FirstPersonPlayerCameraTarget(LocalPlayer targetPlayer){
        this.targetPlayer = targetPlayer;
        position = new Vec3f();
        rotation = new EulerAngles();
    }
    
    @Override
    public Vec3f getPosition(){
        position.set(targetPlayer.getLerpPosition());
        return position;
    }
    
    @Override
    public EulerAngles getRotation(){
        rotation.set(targetPlayer.getRotation());
        return rotation;
    }
    
}
