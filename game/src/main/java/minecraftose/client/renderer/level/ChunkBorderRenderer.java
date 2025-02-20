package minecraftose.client.renderer.level;

import jpize.gl.shader.Shader;
import jpize.gl.tesselation.GlPrimitive;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertAttr;
import jpize.util.Disposable;
import jpize.util.math.matrix.Matrix4f;
import jpize.util.math.vector.Vec3f;
import jpize.util.mesh.IndexedMesh;
import jpize.util.res.Resource;
import minecraftose.client.control.camera.PlayerCamera;

import static minecraftose.main.chunk.ChunkBase.HEIGHT;
import static minecraftose.main.chunk.ChunkBase.SIZE;

public class ChunkBorderRenderer implements Disposable {
    
    private final LevelRenderer levelRenderer;
    private final Shader shader;
    private final IndexedMesh mesh;
    private final Matrix4f translationMatrix;
    private boolean show;
    
    public ChunkBorderRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
        
        this.shader = new Shader(Resource.internal("/shader/line.vert"), Resource.internal("/shader/line.frag"));
        
        this.mesh = new IndexedMesh(new GlVertAttr(3, GlType.FLOAT));
        this.mesh.setMode(GlPrimitive.LINES);
        
        this.mesh.vertices().setData(new float[]{
            SIZE, HEIGHT, SIZE, //0
            0   , HEIGHT, SIZE, //1
            SIZE, 0     , SIZE, //2
            0   , 0     , SIZE, //3
            SIZE, HEIGHT, 0   , //4
            0   , HEIGHT, 0   , //5
            SIZE, 0     , 0   , //6
            0   , 0     , 0   , //7
        });
        this.mesh.indices().setData(new int[]{
            7, 6, 6, 2, 2, 3, 3, 7, //Top
            0, 4, 4, 5, 5, 1, 1, 0, //Bottom
            0, 2, 2, 6, 6, 4, 4, 0, //Left
            7, 3, 3, 1, 1, 5, 5, 7, //Right
            3, 2, 2, 0, 0, 1, 1, 3, //Front
            4, 6, 6, 7, 7, 5, 5, 4, //Back
        });
        
        this.translationMatrix = new Matrix4f();
    }
    
    
    public boolean isShow(){
        return show;
    }
    
    public void show(boolean show){
        this.show = show;
    }
    
    public void toggleShow(){
        show(!show);
    }
    
    
    public void render(PlayerCamera camera){
        if(!show)
            return;
        
        shader.bind();
        shader.uniform("u_projection", camera.getProjection());
        shader.uniform("u_view", camera.getView());
        
        final Vec3f position = new Vec3f(camera.chunkX() * SIZE, 0, camera.chunkZ() * SIZE).sub(camera.getX(), 0, camera.getZ());
        translationMatrix.setTranslate(position);
        shader.uniform("u_model", translationMatrix);
        
        mesh.render();
    }
    
    @Override
    public void dispose(){
        shader.dispose();
        mesh.dispose();
    }
    
}
