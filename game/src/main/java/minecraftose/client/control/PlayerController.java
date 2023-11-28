package minecraftose.client.control;

import jpize.graphics.camera.controller.Rotation3DController;
import jpize.sdl.input.Key;
import minecraftose.client.Minecraft;
import minecraftose.client.control.camera.GameCamera;
import minecraftose.client.control.camera.HorizontalMoveController;
import minecraftose.client.control.camera.PerspectiveType;
import minecraftose.client.entity.LocalPlayer;
import minecraftose.client.level.ClientLevel;
import minecraftose.client.options.KeyMapping;
import minecraftose.client.options.Options;
import minecraftose.main.Tickable;
import minecraftose.main.network.packet.c2s.game.C2SPacketPlayerSneaking;
import jpize.util.time.Stopwatch;

public class PlayerController implements Tickable{

    private final LocalPlayer player;

    private final Rotation3DController rotationController;
    private final HorizontalMoveController horizontalMoveController;
    private final Stopwatch prevJumpTime;
    
    public PlayerController(LocalPlayer player){
        this.player = player;

        this.rotationController = new Rotation3DController();
        this.rotationController.setSmoothness(0);
        this.rotationController.setSpeed(player.getMinecraft().getOptions().getMouseSensitivity());

        this.horizontalMoveController = new HorizontalMoveController(this);
        this.prevJumpTime = new Stopwatch();
    }
    
    public LocalPlayer getPlayer(){
        return player;
    }

    public void update(){
        if(player == null)
            return;
        final ClientLevel level = player.getLevel();
        if(level == null)
            return;
        if(level.getMinecraft().getChat().isOpened())
            return;

        final Minecraft minecraft = level.getMinecraft();
        final Options options = minecraft.getOptions();

        // Rotation
        rotationController.update();
        player.getRotation().set(rotationController.getRotation());

        // Horizontal motion
        horizontalMoveController.update();

        if(Key.X.isDown()){
            player.setWalkSpeedFactor(10);
            player.setJumpHeightFactor(10);
        }else if(Key.X.isReleased()){
            player.setWalkSpeedFactor(1);
            player.setJumpHeightFactor(1);
        }

        if(options.getKey(KeyMapping.SPRINT).isPressed() && options.getKey(KeyMapping.FORWARD).isPressed() ||
            options.getKey(KeyMapping.SPRINT).isPressed() && options.getKey(KeyMapping.FORWARD).isDown())
            player.setSprinting(true);
        else if(options.getKey(KeyMapping.FORWARD).isReleased())
            player.setSprinting(false);

        if(options.getKey(KeyMapping.SNEAK).isDown()){
            player.setSneaking(true);
            minecraft.getConnection().sendPacket(new C2SPacketPlayerSneaking(player));
        }else if(options.getKey(KeyMapping.SNEAK).isReleased()){
            player.setSneaking(false);
            minecraft.getConnection().sendPacket(new C2SPacketPlayerSneaking(player));
        }

        // Jump, Sprint, Sneak
        if(options.getKey(KeyMapping.JUMP).isDown()){
            player.setJumping(true);

            // Activate Flying
            if(player.isFlyEnabled()){
                if(prevJumpTime.getMillis() < 350)
                    player.setFlying(!player.isFlying());
                prevJumpTime.stop().reset().start();
            }
        }else if(options.getKey(KeyMapping.JUMP).isReleased())
            player.setJumping(false);

        // Toggle perspective
        final GameCamera camera = minecraft.getCamera();

        if(options.getKey(KeyMapping.TOGGLE_PERSPECTIVE).isDown()){
            switch(camera.getPerspective()){

                case FIRST_PERSON -> camera.setPerspective(PerspectiveType.THIRD_PERSON_BACK);
                case THIRD_PERSON_BACK -> camera.setPerspective(PerspectiveType.THIRD_PERSON_FRONT);
                case THIRD_PERSON_FRONT -> camera.setPerspective(PerspectiveType.FIRST_PERSON);
            }
        }

        // Boost
        if(Key.Y.isDown())
            player.getVelocity().mul(2, 1.4, 2);
        if(Key.H.isDown())
            player.getVelocity().add(camera.getRotation().getDirection().mul(2));
    }

    public void tick(){

    }
    
    
    public Rotation3DController getRotationController(){
        return rotationController;
    }

    public HorizontalMoveController getHorizontalMoveController(){
        return horizontalMoveController;
    }

}
