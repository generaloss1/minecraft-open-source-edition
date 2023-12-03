package minecraftose.client.control;

import jpize.graphics.camera.controller.Rotation3DController;
import jpize.sdl.input.Key;
import minecraftose.client.Minecraft;
import minecraftose.client.control.camera.PlayerCamera;
import minecraftose.client.control.camera.HorizontalMoveController;
import minecraftose.client.entity.LocalPlayer;
import minecraftose.client.level.LevelC;
import minecraftose.client.options.KeyMapping;
import minecraftose.client.options.Options;
import minecraftose.main.Tickable;
import minecraftose.main.network.packet.c2s.game.C2SPacketPlayerSneaking;
import jpize.util.time.Stopwatch;

public class PlayerInput implements Tickable{

    private final LocalPlayer player;

    private final Rotation3DController rotation;
    private final HorizontalMoveController horizontalMove;
    private final Stopwatch prevJumpTime;
    
    public PlayerInput(LocalPlayer player){
        this.player = player;

        this.rotation = new Rotation3DController();
        // this.rotation.setSmoothness(0);
        this.rotation.setSpeed(player.getMinecraft().getOptions().getMouseSensitivity());

        this.horizontalMove = new HorizontalMoveController(this);
        this.prevJumpTime = new Stopwatch();
    }
    
    public LocalPlayer getPlayer(){
        return player;
    }

    public void update(){
        if(player == null)
            return;
        final LevelC level = player.getLevel();
        if(level == null)
            return;
        if(level.getMinecraft().getChat().isOpened())
            return;

        final Minecraft minecraft = level.getMinecraft();
        final Options options = minecraft.getOptions();

        // Rotation
        rotation.update();
        player.getRotation().set(rotation.getRotation());

        // Horizontal motion
        horizontalMove.update();

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
        final PlayerCamera camera = minecraft.getCamera();

        if(options.getKey(KeyMapping.TOGGLE_PERSPECTIVE).isDown())
            camera.nextPerspective();

        // Boost
        if(Key.Y.isDown())
            player.getVelocity().mul(2, 1.4, 2);
        if(Key.H.isDown())
            player.getVelocity().add(camera.getRotation().getDirection().mul(2));
    }

    public void tick(){ }
    
    
    public Rotation3DController getRotation(){
        return rotation;
    }

    public HorizontalMoveController getHorizontalMove(){
        return horizontalMove;
    }

}
