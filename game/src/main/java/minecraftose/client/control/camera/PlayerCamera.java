package minecraftose.client.control.camera;

import jpize.Jpize;
import jpize.graphics.camera.PerspectiveCamera;
import jpize.math.Mathc;
import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec2d;
import jpize.math.vecmath.vector.Vec3f;
import jpize.physic.utils.Velocity3f;
import minecraftose.client.Minecraft;
import minecraftose.client.block.BlockProps;
import minecraftose.client.block.ClientBlocks;
import minecraftose.client.chunk.ClientChunk;
import minecraftose.client.entity.LocalPlayer;
import minecraftose.client.level.ClientLevel;
import minecraftose.client.options.Options;
import minecraftose.main.chunk.ChunkUtils;
import minecraftose.main.chunk.storage.ChunkPos;

public class PlayerCamera extends PerspectiveCamera{

    private final Minecraft minecraft;
    
    private final LocalPlayer player;
    private CameraPerspective perspective;
    private float notInterpolatedFov;
    private float zoom;
    private boolean inWater;
    private final Velocity3f hitDirection;
    private float eyeHeight;

    public PlayerCamera(Minecraft minecraft, double near, double far, double fieldOfView){
        super(near, far, fieldOfView);
        
        this.minecraft = minecraft;
        this.player = minecraft.getPlayer();

        this.zoom = 1;
        
        this.perspective = CameraPerspective.FIRST_PERSON;
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

        // Set pos & rot
        super.position.set(player.getLerpPosition());
        super.rotation.set(player.getInput().getRotation().getRotation());
        super.rotation.clampPitch90();

        // Perspective
        final float thirdCameraDist = 4;
        if(!perspective.isFirstPerson())
            super.position.sub(super.rotation.getDirection().mul(perspective.isMirrored() ? -thirdCameraDist : thirdCameraDist));
        if(perspective.isMirrored())
            super.rotation.pitch += 180;

        // Eye Height
        eyeHeight += (player.getEyeHeight() - eyeHeight) * deltaTime * 12;
        super.position.y += eyeHeight;

        // Player
        final Options options = minecraft.getOptions();
        
        float fov = options.getFieldOfView() / zoom;
        if(player.isSprinting())
            fov *= 1.125F;
        
        setFov(fov);

        // Directed Hit
        hitDirection.mul(0.8);
        hitDirection.clampToMax();
        final Vec3f hitLocalDirection = hitDirection.copy().rotY(super.rotation.yaw);
        super.rotation.pitch -= hitLocalDirection.x * 1.5F;
        super.rotation.roll -= hitLocalDirection.z * 1.5F;

        // Jumps
        //this.rotation.pitch -= Maths.clamp(player.getVelocity().y / 2, -10, 10);

        // View Bobbing
        bobView(minecraft.getTime().getTickLerpFactor());

        // Interpolate FOV
        final float currentFOV = getFov();
        super.setFov(currentFOV + (notInterpolatedFov - currentFOV) * deltaTime * 9);
        super.update();

        // Update is camera in water
        final BlockProps block = level.getBlockProps(position.xFloor(), position.yFloor(), position.zFloor());
        inWater = block.getID() == ClientBlocks.WATER.getID();
    }

    private void bobView(float t){
        final float walkDist = player.getWalkDist(t) * Maths.PI;
        final float bobbing = player.getBobbing();

        final float sin = Mathc.sin(walkDist) * bobbing;

        final Vec2d dx = new Vec2d(sin * 0.5).rotDeg(-super.rotation.yaw);
        final double dy = -Math.abs(Mathc.cos(walkDist)) * bobbing;
        super.position.add(0, dy, 0).add(dx);

        final float rz = sin * 3;
        final float rx = Math.abs(Mathc.cos(walkDist - 0.2)) * bobbing * 5;

        super.rotation.roll += rz;
        super.rotation.pitch += rx;
    }
    
    
    public void setDistance(int renderDistanceInChunks){
        // setFar(Math.max((renderDistanceInChunks + 0.5F) * SIZE, HEIGHT * 4));
    }
    
    
    public CameraPerspective getPerspective(){
        return perspective;
    }
    
    public void nextPerspective(){
        perspective = perspective.next();
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
        final ChunkPos position = chunk.getPosition();
        
        return getFrustum().isBoxInFrustum(
            position.x * ChunkUtils.SIZE,
            0,
            position.z * ChunkUtils.SIZE,
            position.x * ChunkUtils.SIZE + ChunkUtils.SIZE,
            (chunk.getMaxY() + 1),
            position.z * ChunkUtils.SIZE + ChunkUtils.SIZE
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
