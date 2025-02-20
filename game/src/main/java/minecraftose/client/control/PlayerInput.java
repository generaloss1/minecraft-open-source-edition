package minecraftose.client.control;

import jpize.glfw.input.Key;
import jpize.util.input.RotationInput;
import jpize.util.math.EulerAngles;
import jpize.util.time.Stopwatch;
import minecraftose.client.Minecraft;
import minecraftose.client.control.camera.HorizontalMoveController;
import minecraftose.client.control.camera.PlayerCamera;
import minecraftose.client.entity.LocalPlayer;
import minecraftose.client.level.LevelC;
import minecraftose.client.options.KeyMapping;
import minecraftose.client.options.Options;
import minecraftose.main.Tickable;
import minecraftose.main.network.packet.c2s.game.C2SPacketPlayerSneaking;

public class PlayerInput implements Tickable{

    private final LocalPlayer player;

    private final RotationInput rotation;
    private final HorizontalMoveController horizontalMove;
    private final Stopwatch prevJumpTime;
    
    public PlayerInput(LocalPlayer player){
        this.player = player;

        this.rotation = new RotationInput(new EulerAngles());
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
        player.getRotation().set(rotation.getTarget());

        // Horizontal motion
        horizontalMove.update();

        if(Key.X.down()){
            player.setWalkSpeedFactor(10);
            player.setJumpHeightFactor(10);
        }else if(Key.X.up()){
            player.setWalkSpeedFactor(1);
            player.setJumpHeightFactor(1);
        }

        if(options.getKey(KeyMapping.SPRINT).pressed() && options.getKey(KeyMapping.FORWARD).pressed() ||
            options.getKey(KeyMapping.SPRINT).pressed() && options.getKey(KeyMapping.FORWARD).down())
            player.setSprinting(true);
        else if(options.getKey(KeyMapping.FORWARD).up())
            player.setSprinting(false);

        if(options.getKey(KeyMapping.SNEAK).down()){
            player.setSneaking(true);
            minecraft.getConnection().sendPacket(new C2SPacketPlayerSneaking(player));
        }else if(options.getKey(KeyMapping.SNEAK).up()){
            player.setSneaking(false);
            minecraft.getConnection().sendPacket(new C2SPacketPlayerSneaking(player));
        }

        // Jump, Sprint, Sneak
        if(options.getKey(KeyMapping.JUMP).down()){
            player.setJumping(true);

            // Activate Flying
            if(player.isFlyEnabled()){
                if(prevJumpTime.getMillis() < 350)
                    player.setFlying(!player.isFlying());
                prevJumpTime.stop().reset().start();
            }
        }else if(options.getKey(KeyMapping.JUMP).up())
            player.setJumping(false);

        // Toggle perspective
        final PlayerCamera camera = minecraft.getCamera();

        if(options.getKey(KeyMapping.TOGGLE_PERSPECTIVE).down())
            camera.nextPerspective();

        // Boost
        if(Key.Y.down())
            player.getVelocity().mul(2, 1.4, 2);
        if(Key.H.down())
            player.getVelocity().add(camera.getDirection().mul(2));
    }

    public void tick(){ }
    
    
    public RotationInput getRotation(){
        return rotation;
    }

    public HorizontalMoveController getHorizontalMove(){
        return horizontalMove;
    }

}
