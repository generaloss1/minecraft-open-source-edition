package minecraftose.client.control;

import jpize.math.Mathc;
import jpize.math.Maths;
import jpize.math.vecmath.matrix.Matrix4f;
import jpize.math.vecmath.vector.Vec3f;
import jpize.math.vecmath.vector.Vec3i;
import jpize.physic.Ray3f;
import jpize.physic.utils.Intersector;
import minecraftose.client.Minecraft;
import minecraftose.client.block.BlockProps;
import minecraftose.client.block.ClientBlocks;
import minecraftose.client.block.shape.BlockCursor;
import minecraftose.client.entity.LocalPlayer;
import minecraftose.client.level.ClientLevel;
import minecraftose.main.Dir;
import minecraftose.main.block.ChunkBlockData;
import minecraftose.main.chunk.ChunkUtils;

public class BlockRayCast{
    
    private final Minecraft session;
    
    private final Ray3f ray;
    private float length;
    private final Vec3i selectedBlock, imaginarySelectedBlock;
    private BlockProps selectedBlockProps;
    private Dir selectedFace;
    private boolean selected;
    private ClientLevel level;
    
    public BlockRayCast(Minecraft session, float length){
        this.session = session;
        
        ray = new Ray3f();
        setLength(length);
        
        selectedBlock = new Vec3i();
        imaginarySelectedBlock = new Vec3i();
    }
    
    public Minecraft getSession(){
        return session;
    }
    
    
    public void setLevel(ClientLevel level){
        this.level = level;
    }
    
    
    public void update(){
        if(level == null)
            return;
        
        // Update ray
        final LocalPlayer player = session.getGame().getPlayer();
        ray.dir().set(player.getRotation().getDirection());
        ray.origin().set(player.getLerpPosition().copy().add(0, player.getEyeHeight(), 0));
        
        // Get pos, dir, len
        final Vec3f pos = ray.origin();
        final Vec3f dir = ray.dir();
        
        // ...
        final Vec3i step = new Vec3i(
            Mathc.signum(dir.x),
            Mathc.signum(dir.y),
            Mathc.signum(dir.z)
        );
        final Vec3f delta = new Vec3f(
            step.x / dir.x,
            step.y / dir.y,
            step.z / dir.z
        );
        final Vec3f tMax = new Vec3f(
            Math.min(length / 2, Maths.abs((Math.max(step.x, 0) - Maths.frac(pos.x)) / dir.x)),
            Math.min(length / 2, Maths.abs((Math.max(step.y, 0) - Maths.frac(pos.y)) / dir.y)),
            Math.min(length / 2, Maths.abs((Math.max(step.z, 0) - Maths.frac(pos.z)) / dir.z))
        );
        
        selectedBlock.set(pos.xFloor(), pos.yFloor(), pos.zFloor());
        final Vec3i faceNormal = new Vec3i();
        
        selected = false;
        while(tMax.len() < length){
            
            if(tMax.x < tMax.y){
                if(tMax.x < tMax.z){
                    tMax.x += delta.x;
                    selectedBlock.x += step.x;
                    faceNormal.set(-step.x, 0, 0);
                }else{
                    tMax.z += delta.z;
                    selectedBlock.z += step.z;
                    faceNormal.set(0, 0, -step.z);
                }
            }else{
                if(tMax.y < tMax.z){
                    tMax.y += delta.y;
                    selectedBlock.y += step.y;
                    faceNormal.set(0, -step.y, 0);
                }else{
                    tMax.z += delta.z;
                    selectedBlock.z += step.z;
                    faceNormal.set(0, 0, -step.z);
                }
            }
            
            if(selectedBlock.y < 0 || selectedBlock.y > ChunkUtils.HEIGHT_IDX)
                break;
            
            final short blockData = level.getBlockState(selectedBlock.x, selectedBlock.y, selectedBlock.z);
            final BlockProps block = ChunkBlockData.getProps(blockData);

            if(!block.isEmpty() && block.getID() != ClientBlocks.VOID_AIR.getID() && block.getCursor() != null){
                if(block.isSolid()){
                    selectedFace = Dir.fromNormal(faceNormal.x, faceNormal.y, faceNormal.z);
                    selectedBlockProps = block;
                    imaginarySelectedBlock.set(selectedBlock).add(selectedFace.getNormal());
                    selected = true;
                    
                    break;
                }else{
                    final BlockCursor shape = block.getCursor();
                    final Matrix4f mat = new Matrix4f().translate(selectedBlock);
                    final float intersect = Intersector.getRayIntersectionQuadMesh(ray, mat, shape.getVertices(), shape.getQuadIndices());
                    System.out.println(intersect);
                    if(intersect != -1){
                        selectedFace = Dir.fromNormal(faceNormal.x, faceNormal.y, faceNormal.z);
                        selectedBlockProps = block;
                        imaginarySelectedBlock.set(selectedBlock).add(selectedFace.getNormal());
                        selected = true;

                        break;
                    }
                }
            }
        }
        
    }
    
    
    public Dir getSelectedFace(){
        return selectedFace;
    }
    
    public Vec3i getSelectedBlockPosition(){
        return selectedBlock;
    }

    public BlockProps getSelectedBlockProps(){
        return selectedBlockProps;
    }
    
    public Vec3i getImaginaryBlockPosition(){
        return imaginarySelectedBlock;
    }
    
    public void setLength(float length){
        this.length = length;
    }
    
    public boolean isSelected(){
        return selected;
    }
    
}
