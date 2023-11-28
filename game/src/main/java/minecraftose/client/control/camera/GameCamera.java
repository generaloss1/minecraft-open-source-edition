package minecraftose.client.control.camera;

import jpize.Jpize;
import jpize.graphics.camera.PerspectiveCamera;
import jpize.math.Mathc;
import jpize.math.Maths;
import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec2d;
import jpize.math.vecmath.vector.Vec3f;
import jpize.physic.utils.Velocity3f;
import minecraftose.client.Minecraft;
import minecraftose.client.block.BlockProps;
import minecraftose.client.block.ClientBlocks;
import minecraftose.client.chunk.ClientChunk;
import minecraftose.client.control.camera.perspective.CameraTarget;
import minecraftose.client.control.camera.perspective.FirstPersonPlayerCameraTarget;
import minecraftose.client.control.camera.perspective.ThirdPersonBackCameraTarget;
import minecraftose.client.control.camera.perspective.ThirdPersonFrontCameraTarget;
import minecraftose.client.entity.LocalPlayer;
import minecraftose.client.level.ClientLevel;
import minecraftose.client.options.Options;
import minecraftose.main.chunk.ChunkUtils;
import minecraftose.main.chunk.storage.ChunkPos;

public class GameCamera extends PerspectiveCamera{

    private final Minecraft minecraft;
    
    private final LocalPlayer player;
    private CameraTarget target;
    private final CameraTarget firstPerson, thirdPersonFront, thirdPersonBack;
    private PerspectiveType perspective;
    private float notInterpolatedFov;
    private float zoom = 1;
    private boolean inWater;
    private final Velocity3f hitDirection;
    private float eyeHeight;

    public GameCamera(Minecraft minecraft, double near, double far, double fieldOfView){
        super(near, far, fieldOfView);
        
        this.minecraft = minecraft;
        this.player = minecraft.getPlayer();
        
        this.firstPerson = new FirstPersonPlayerCameraTarget(player);
        this.thirdPersonFront = new ThirdPersonFrontCameraTarget(player);
        this.thirdPersonBack = new ThirdPersonBackCameraTarget(player);
        
        this.perspective = PerspectiveType.FIRST_PERSON;
        this.target = firstPerson;
        this.hitDirection = new Velocity3f().setMax(3);
        this.eyeHeight = player.getEyeHeight();
        
        setImaginaryOrigins(true, false, true);
    }
    
    public Minecraft getMinecraft(){
        return minecraft;
    }
    
    public LocalPlayer getPlayer(){
        return player;
    }


    public void update(){
        final ClientLevel level = minecraft.getLevel();
        if(level == null)
            return;

        final float deltaTime = Jpize.getDt();

        // Follow target
        if(target == null)
            return;
        final Vec3f position = target.getPosition().copy();
        final EulerAngles rotation = target.getRotation().copy();
        
        // Shaking
        // final GameTime gameTime = game.getTime();
        // final float time = gameTime.getSeconds() * 10;

        // final float playerSpeed = player.getVelocity().xz().len() * 20;
        // final float shakingHorizontal = Mathc.sin(time) * 0.02F * playerSpeed;
        // final float shakingVertical = (Mathc.pow(Mathc.sin(time) * 0.02F, 2) - 0.02F) * playerSpeed;
        // final Vec2f shakingShift = new Vec2f(0, shakingHorizontal);
        // shakingShift.rotDeg(rotation.yaw);
        // position.add(shakingShift.x, shakingVertical, shakingShift.y);

        // Eye Height
        eyeHeight += (player.getEyeHeight() - eyeHeight) * deltaTime * 12;
        position.y += eyeHeight;

        // Follow to target
        getPosition().set(position);
        getRotation().set(rotation);

        // Player
        final Options options = minecraft.getOptions();
        
        float fov = options.getFieldOfView() / zoom;
        if(player.isSprinting())
            fov *= 1.125F;
        
        setFov(fov);

        // Directed Hit
        hitDirection.mul(0.8);
        hitDirection.clampToMax();
        final Vec3f hitLocalDirection = hitDirection.copy().rotY(rotation.yaw);
        this.rotation.pitch -= hitLocalDirection.x * 1.5F;
        this.rotation.roll -= hitLocalDirection.z * 1.5F;

        // Jumps
        //this.rotation.pitch -= Maths.clamp(player.getVelocity().y / 2, -10, 10);

        // View Bobbing
        bobView(minecraft.getTime().getTickLerpFactor());

        // Interpolate FOV
        final float currentFOV = getFov();
        super.setFov(currentFOV + (notInterpolatedFov - currentFOV) * deltaTime * 9);
        this.rotation.clampPitch90();
        super.update();

        // Update is camera in water
        final BlockProps block = level.getBlockProps(position.xFloor(), position.yFloor(), position.zFloor());
        inWater = block.getID() == ClientBlocks.WATER.getID();
    }

    private void bobView(float t){
        final float walkDist = player.getWalkDist(t) * Maths.PI;
        final float bobbing = player.getBobbing();

        final float sin = Mathc.sin(walkDist) * bobbing;

        final Vec2d dx = new Vec2d(sin * 0.5).rotDeg(-rotation.yaw);
        final double dy = -Math.abs(Mathc.cos(walkDist)) * bobbing      ;
        position.add(0, dy, 0).add(dx);

        final float rz = sin * 3;
        final float rx = Math.abs(Mathc.cos(walkDist - 0.2)) * bobbing * 5;

        rotation.roll += rz;
        rotation.pitch += rx;
    }
    
    
    public void setDistance(int renderDistanceInChunks){
        // setFar(Math.max((renderDistanceInChunks + 0.5F) * SIZE, HEIGHT * 4));
    }
    
    
    public PerspectiveType getPerspective(){
        return perspective;
    }
    
    public void setPerspective(PerspectiveType perspective){
        this.perspective = perspective;
        switch(perspective){
            case FIRST_PERSON -> target = firstPerson;
            case THIRD_PERSON_BACK -> target = thirdPersonBack;
            case THIRD_PERSON_FRONT -> target = thirdPersonFront;
        }
    }


    public void push(Vec3f hitDirection){
        this.hitDirection.add(hitDirection.nor());
    }
    
    
    public float getZoom(){
        return zoom;
    }
    
    public void setZoom(float zoom){
        this.zoom = Maths.clamp(zoom, 1, 200);
    }
    
    
    public void setFov(float fieldOfView){
        notInterpolatedFov = fieldOfView;
    }

    
    public boolean isChunkSeen(ClientChunk chunk){
        ChunkPos pos = chunk.getPosition();
        
        return getFrustum().isBoxInFrustum(
            pos.x * ChunkUtils.SIZE,
            0,
            pos.z * ChunkUtils.SIZE,

            pos.x * ChunkUtils.SIZE + ChunkUtils.SIZE,
            (chunk.getMaxY() + 1),
            pos.z * ChunkUtils.SIZE + ChunkUtils.SIZE
        );
    }
    
    
    public int chunkX(){
        return Maths.floor(getX() / ChunkUtils.SIZE);
    }
    
    public int chunkZ(){
        return Maths.floor(getZ() / ChunkUtils.SIZE);
    }


    public boolean isInWater(){
        return inWater;
    }

}
