package minecraftose.client.entity.model;

import jpize.util.math.EulerAngles;
import jpize.util.math.Quaternion;
import jpize.util.math.matrix.Matrix4f;
import jpize.util.math.vector.Vec3f;
import minecraftose.client.control.camera.PlayerCamera;

public class Pose{
    
    private final Vec3f position, scale;
    private final EulerAngles rotation;
    
    private final Matrix4f translateMatrix, scaleMatrix, poseModelMatrix, modelMatrix;
    
    public Pose(){
        position = new Vec3f();
        scale = new Vec3f(1, 1, 1);
        rotation = new EulerAngles();
        
        translateMatrix = new Matrix4f();
        scaleMatrix = new Matrix4f();
        poseModelMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();
    }
    
    public void updateMatrices(PlayerCamera camera, Pose initial){
        translateMatrix.setTranslate(position);
        scaleMatrix.setScale(scale);
        final Matrix4f rotationMatrix = new Matrix4f().setQuaternion(new Quaternion().setRotation(rotation));
        
        poseModelMatrix
            .identity()
        
            .mul(initial.poseModelMatrix)
            .mul(translateMatrix).mul(scaleMatrix).mul(rotationMatrix)
        ;
        
        modelMatrix
            .set(new Matrix4f().setTranslate(-camera.getX(), 0, -camera.getZ()))
            .mul(poseModelMatrix);
    }
    
    public void updateMatrices(PlayerCamera camera, Pose initial, Pose parent){
        translateMatrix.setTranslate(position);
        scaleMatrix.setScale(scale);
        final Matrix4f rotationMatrix = new Matrix4f().setQuaternion(new Quaternion().setRotation(rotation));
        
        poseModelMatrix
            .identity()
            
            .mul(parent.poseModelMatrix)
            .mul(initial.poseModelMatrix)
            .mul(translateMatrix).mul(scaleMatrix).mul(rotationMatrix)
        ;
        
        modelMatrix
            .set(new Matrix4f().setTranslate(-camera.getX(), 0, -camera.getZ()))
            .mul(poseModelMatrix);
    }
    
    
    public void set(Vec3f position, EulerAngles rotation){
        this.position.set(position);
        this.rotation.set(rotation);
    }
    
    public void set(Pose pose){
        set(pose.position, pose.rotation);
    }
    
    
    public Matrix4f getModelMatrix(){
        return modelMatrix;
    }
    
    public Vec3f getPosition(){
        return position;
    }
    
    public Vec3f getScale(){
        return scale;
    }
    
    public EulerAngles getRotation(){
        return rotation;
    }
    
}
