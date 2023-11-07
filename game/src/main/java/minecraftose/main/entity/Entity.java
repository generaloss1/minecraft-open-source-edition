package minecraftose.main.entity;

import jpize.math.Mathc;
import jpize.math.Maths;
import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import jpize.physic.axisaligned.box.AABox;
import jpize.physic.axisaligned.box.AABoxBody;
import jpize.physic.axisaligned.box.AABoxCollider;
import jpize.physic.utils.Velocity3f;
import minecraftose.client.block.BlockProps;
import minecraftose.client.block.ClientBlocks;
import minecraftose.client.block.shape.BlockCollide;
import minecraftose.client.time.TickFloat;
import minecraftose.main.Dir;
import minecraftose.main.Tickable;
import minecraftose.main.block.ChunkBlockData;
import minecraftose.main.chunk.ChunkUtils;
import minecraftose.main.level.Level;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Entity extends AABoxBody implements Tickable{
    
    private Level level;
    private final EntityType<?> entityType;
    private final EulerAngles rotation;
    private final Velocity3f velocity;
    private UUID uuid;
    
    private AABoxBody[] blockBoxes;
    private boolean onGround;
    private final TickFloat walkDist;
    private final TickFloat moveDist;

    public Entity(EntityType<?> entityType, Level level){
        super(entityType.getBoundingBox());
        
        this.level = level;
        this.entityType = entityType;
        this.rotation = new EulerAngles();
        this.velocity = new Velocity3f();
        this.uuid = UUID.randomUUID();

        this.walkDist = new TickFloat();
        this.moveDist = new TickFloat();
    }
    
    
    public Level getLevel(){
        return level;
    }
    
    public void setLevel(Level level){
        this.level = level;
    }
    
    public EntityType<?> getEntityType(){
        return entityType;
    }
    
    public EulerAngles getRotation(){
        return rotation;
    }
    
    public Velocity3f getVelocity(){
        return velocity;
    }
    
    
    public UUID getUUID(){
        return uuid;
    }
    
    public void setUUID(UUID uuid){
        this.uuid = uuid;
    }
    
    public float getEyeHeight(){
        return getBoundingBox().getSizeY() * 0.85F;
    }
    

    @Override
    public void tick(){
        // Check is chunk loaded
        final Vec3f pos = getPosition();
        if(level.getBlockChunk(pos.xFloor(), pos.zFloor()) == null)
            return;
        
        // Update blocks around player
        blockBoxes = getBlockBoxes();
        // Update is player on ground

        // Movement
        final Vec3f collideMovement = collideMovement(getVelocity());
        if(collideMovement != null){
            // Zero axes
            getVelocity().collidedAxesToZero(collideMovement);

            // Walk dist
            walkDist.add(Math.min(0.25F, Mathc.sqrt(collideMovement.x * collideMovement.x + collideMovement.z * collideMovement.z) * 0.6F)); //: lenXZ
            moveDist.add(collideMovement.len() * 0.6F);
        }

        // Check is entity on a ground
        onGround = isCollidedTo(Dir.NEGATIVE_Y);
    }


    public float getWalkDist(float t){
        return walkDist.getLerp(t);
    }

    public float getMoveDist(float t){
        return moveDist.getLerp(t);
    }

    
    public boolean isOnGround(){
        return onGround;
    }
    
    public boolean isCollidedTo(Dir face){
        final Vec3f dir = new Vec3f(face.getNormal()).mul(Maths.Epsilon);
        return AABoxCollider.getCollidedMovement(dir, this, blockBoxes).len2() < dir.len2();
    }
    
    public boolean isCollidedTo(Vec3f direction){
        final Vec3f dir = direction.copy().nor().mul(Maths.Epsilon);
        return AABoxCollider.getCollidedMovement(dir, this, blockBoxes).len2() < dir.len2();
    }


    private Vec3f collideMovement(Vec3f motion){
        if(blockBoxes == null)
            return null;

        final Vec3f collideMovement = AABoxCollider.getCollidedMovement(motion, this, blockBoxes);
        getPosition().add(collideMovement);

        return collideMovement;
    }

    /** Get Bounding Boxes of blocks around Entity */
    private AABoxBody[] getBlockBoxes(){
        final ArrayList<AABoxBody> blockBoxes = new ArrayList<>();

        final Velocity3f velocity = getVelocity();
        final Vec3f min = this.getMin();
        final Vec3f max = this.getMax();

        final int beginX = Maths.floor(min.x - 1 + Math.min(0, velocity.x));
        final int beginY = Math.max(0, Math.min(ChunkUtils.HEIGHT_IDX,
            Maths.floor(min.y - 1 + Math.min(0, velocity.y))
        ));
        final int beginZ = Maths.floor(min.z - 1 + Math.min(0, velocity.z));

        final int endX = Maths.ceil(max.x + 1 + Math.max(0, velocity.x));
        final int endY = Math.max(0, Math.min(ChunkUtils.HEIGHT,
            Maths.ceil(max.y + 1 + Math.max(0, velocity.y))
        ));
        final int endZ = Maths.ceil(max.z + 1 + Math.max(0, velocity.z));

        for(int x = beginX; x < endX; x++)
            for(int y = beginY; y < endY; y++)
                for(int z = beginZ; z < endZ; z++){

                    final short blockData = level.getBlockState(x, y, z);
                    final BlockProps block = ChunkBlockData.getProps(blockData);

                    if(block.getID() == ClientBlocks.AIR.getID())
                        continue;

                    if(block.getID() == ClientBlocks.VOID_AIR.getID()){
                        final AABoxBody box = new AABoxBody(BlockCollide.SOLID.getBoxes()[0]);
                        box.getPosition().set(x, y, z);
                        blockBoxes.add(box);
                        continue;
                    }

                    final BlockCollide shape = block.getCollide();
                    if(shape == null)
                        continue;

                    for(AABox boundingBox: shape.getBoxes()){
                        final AABoxBody box = new AABoxBody(boundingBox);
                        box.getPosition().set(x, y, z);

                        blockBoxes.add(box);
                    }
                }

        return blockBoxes.toArray(new AABoxBody[0]);
    }

}
