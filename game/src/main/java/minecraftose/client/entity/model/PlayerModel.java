package minecraftose.client.entity.model;

import jpize.app.Jpize;
import jpize.util.math.Mathc;
import jpize.util.math.Maths;
import jpize.util.math.vector.Vec3f;
import minecraftose.client.Minecraft;
import minecraftose.client.control.camera.PlayerCamera;
import minecraftose.client.entity.AbstractClientPlayer;
import minecraftose.client.level.LevelC;
import minecraftose.client.options.Options;

public class PlayerModel extends HumanoidModel{
    
    private static final float t = 1 / 64F; // Pixel size on texture
    private static final float w = 1.8F / 32; // Pixel size in world
    
    
    private final ModelPart jacket, hat, leftPants, rightPants, leftSleeve, rightSleeve;
    
    public PlayerModel(AbstractClientPlayer player){
        super(player);
        
        final float scale = 1 + t;
        
        hat = new ModelPart(new BoxBuilder(-4 * w, 0 * w, -4 * w,  4 * w, 8 * w, 4 * w)
            .nx(1, 1, 1, 1, 56 * t, 8  * t, 64 * t, 16 * t)
            .px(1, 1, 1, 1, 40 * t, 8  * t, 48 * t, 16 * t)
            .ny(1, 1, 1, 1, 48 * t, 0  * t, 56 * t, 8  * t)
            .py(1, 1, 1, 1, 40 * t, 0  * t, 48 * t, 8  * t)
            .pz(1, 1, 1, 1, 48 * t, 8  * t, 56 * t, 16 * t)
            .nz(1, 1, 1, 1, 32 * t, 8  * t, 40 * t, 16 * t)
            .end());
        hat.setParent(head);
        hat.getPose().getScale().set(scale * 1.05);
        
        jacket = new ModelPart(new BoxBuilder(-2 * w, -6 * w, -4 * w,  2 * w, 6 * w, 4 * w)
            .nx(1, 1, 1, 1, 32 * t, 36 * t, 40 * t, 48 * t)
            .px(1, 1, 1, 1, 20 * t, 36 * t, 28 * t, 48 * t)
            .ny(1, 1, 1, 1, 28 * t, 32 * t, 36 * t, 36 * t)
            .py(1, 1, 1, 1, 20 * t, 32 * t, 28 * t, 36 * t)
            .pz(1, 1, 1, 1, 28 * t, 36 * t, 32 * t, 48 * t)
            .nz(1, 1, 1, 1, 16 * t, 36 * t, 20 * t, 48 * t)
        .end());
        jacket.setParent(torso);
        jacket.getPose().getScale().set(scale * 1.04);
        
        leftPants = new ModelPart(new BoxBuilder(-2 * w, -10 * w, -2 * w,  2 * w, 2 * w, 2 * w)
            .nx(1, 1, 1, 1, 12 * t, 52 * t, 16 * t, 64 * t)
            .px(1, 1, 1, 1, 4  * t, 52 * t, 8  * t, 64 * t)
            .ny(1, 1, 1, 1, 8  * t, 48 * t, 12 * t, 52 * t)
            .py(1, 1, 1, 1, 4  * t, 48 * t, 8  * t, 52 * t)
            .pz(1, 1, 1, 1, 8  * t, 52 * t, 12 * t, 64 * t)
            .nz(1, 1, 1, 1, 0  * t, 52 * t, 4  * t, 64 * t)
        .end());
        leftPants.setParent(leftLeg);
        leftPants.getPose().getScale().set(scale * 1.03);
        
        rightPants = new ModelPart(new BoxBuilder(-2 * w, -10 * w, -2 * w,  2 * w, 2 * w, 2 * w)
            .nx(1, 1, 1, 1, 12 * t, 36 * t, 16 * t, 48 * t)
            .px(1, 1, 1, 1, 4  * t, 36 * t, 8  * t, 48 * t)
            .ny(1, 1, 1, 1, 8  * t, 32 * t, 12 * t, 36 * t)
            .py(1, 1, 1, 1, 4  * t, 32 * t, 8  * t, 36 * t)
            .pz(1, 1, 1, 1, 8  * t, 36 * t, 12 * t, 48 * t)
            .nz(1, 1, 1, 1, 0  * t, 36 * t, 4  * t, 48 * t)
        .end());
        rightPants.setParent(rightLeg);
        rightPants.getPose().getScale().set(scale * 1.02);
        
        leftSleeve = new ModelPart(new BoxBuilder(-2 * w, -10 * w, -2 * w,  2 * w, 2 * w, 2 * w)
            .nx(1, 1, 1, 1, 60 * t, 52 * t, 64 * t, 64 * t)
            .px(1, 1, 1, 1, 52 * t, 52 * t, 56 * t, 64 * t)
            .ny(1, 1, 1, 1, 56 * t, 48 * t, 60 * t, 52 * t)
            .py(1, 1, 1, 1, 52 * t, 48 * t, 56 * t, 52 * t)
            .pz(1, 1, 1, 1, 56 * t, 52 * t, 60 * t, 64 * t)
            .nz(1, 1, 1, 1, 48 * t, 52 * t, 52 * t, 64 * t)
        .end());
        leftSleeve.setParent(leftArm);
        leftSleeve.getPose().getScale().set(scale * 1.01);
        
        rightSleeve = new ModelPart(new BoxBuilder(-2 * w, -10 * w, -2 * w,  2 * w, 2 * w, 2 * w)
            .nx(1, 1, 1, 1, 52 * t, 36 * t, 56 * t, 48 * t)
            .px(1, 1, 1, 1, 44 * t, 36 * t, 48 * t, 48 * t)
            .ny(1, 1, 1, 1, 48 * t, 32 * t, 52 * t, 36 * t)
            .py(1, 1, 1, 1, 44 * t, 32 * t, 48 * t, 36 * t)
            .pz(1, 1, 1, 1, 48 * t, 36 * t, 52 * t, 48 * t)
            .nz(1, 1, 1, 1, 40 * t, 36 * t, 44 * t, 48 * t)
        .end());
        rightSleeve.setParent(rightArm);
        rightSleeve.getPose().getScale().set(scale * 1.01);
    }
    
    
    public void render(PlayerCamera camera){
        super.render(camera);

        jacket.render(camera, shader, "u_model");
        hat.render(camera, shader, "u_model");
        leftPants.render(camera, shader, "u_model");
        rightPants.render(camera, shader, "u_model");
        leftSleeve.render(camera, shader, "u_model");
        rightSleeve.render(camera, shader, "u_model");
    }
    
    
    public void animate(){
        final LevelC level = player.getLevel();
        if(level == null)
            return;

        // Position & Rotation
        torso.getPosition().set(player.getLerpPosition());
        head.getPosition().set(player.getLerpPosition());
        
        final Minecraft minecraft = level.getMinecraft();
        final Options options = minecraft.getOptions();
        final PlayerCamera camera = minecraft.getCamera();
        if(options.isFirstPersonModel() && camera.getPerspective().isFirstPerson()){
            final Vec3f offset = player.getRotation().getDirectionHorizontal(new Vec3f()).mul(-4 * w);
            torso.getPosition().add(offset);
            head.setShow(false);
        }else
            head.setShow(true);

        if(Float.isNaN(player.getLerpRotation().yaw) || Float.isNaN(torso.getRotation().yaw))
            return;

        torso.getRotation().yaw += (player.getLerpRotation().yaw - torso.getRotation().yaw) * Jpize.getDeltaTime() * 6;

        head.getRotation().yaw = player.getLerpRotation().yaw;
        head.getRotation().roll = player.getLerpRotation().pitch;

        // Sneaking
        if(player.isSneaking()){
            leftLeg.getRotation().roll = 45;
            rightLeg.getRotation().roll = 45;
            torso.getRotation().pitch = -30;
            torso.getPosition().add(0, -w * 2, 0);
            head.getPosition().add(
                3 * w * Mathc.cos(torso.getRotation().yaw * Maths.TO_RAD),
                -w * 3,
                3 * w * Mathc.sin(torso.getRotation().yaw * Maths.TO_RAD)
            );
        }else{
            torso.getRotation().pitch = 0;
            leftLeg.getRotation().roll = 0;
            rightLeg.getRotation().roll = 0;
        }

        // Animation
        final float lerpFactor = player.getLevel().getMinecraft().getTime().getTickLerpFactor();
        final float moveDist = player.getWalkDist(lerpFactor) * 4;
        final float bobbing = Math.min(1, player.getModelBobbing() * 10);

        rightArm.getRotation().roll = -60 * Mathc.sin(moveDist) * bobbing;
        leftArm.getRotation().roll = 60 * Mathc.sin(moveDist) * bobbing;
        rightLeg.getRotation().roll = 60 * Mathc.sin(moveDist) * bobbing;
        leftLeg.getRotation().roll = -60 * Mathc.sin(moveDist) * bobbing;
    }


    public ModelPart getRightSleeve(){
        return rightSleeve;
    }
    
    
    public void dispose(){
        super.dispose();
    }
    
}
