package minecraftose.client.renderer.level;

import jpize.gl.shader.Shader;
import jpize.util.Disposable;
import jpize.util.math.matrix.Matrix4f;
import jpize.util.math.vector.Vec3f;
import jpize.util.res.Resource;
import minecraftose.client.block.shape.BlockCursor;
import minecraftose.client.control.BlockRayCast;
import minecraftose.client.control.camera.PlayerCamera;

public class BlockSelectorRenderer implements Disposable {
    
    private final LevelRenderer levelRenderer;
    private final Shader shader;
    private final Matrix4f translationMatrix;
    
    public BlockSelectorRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
        
        this.shader = new Shader(Resource.internal("/shader/line.vert"), Resource.internal("/shader/line.frag"));
        
        this.translationMatrix = new Matrix4f();
    }
    
    
    public void render(PlayerCamera camera){
        final BlockRayCast rayCast = levelRenderer.getGameRenderer().getMinecraft().getBlockRayCast();
        if(!rayCast.isSelected())
            return;
        
        shader.bind();
        shader.uniform("u_projection", camera.getProjection());
        shader.uniform("u_view", camera.getView());
        
        translationMatrix.setTranslate(new Vec3f(rayCast.getSelectedBlockPosition()).sub(camera.getX(), 0, camera.getZ()));
        shader.uniform("u_model", translationMatrix);
        
        final BlockCursor shape = rayCast.getSelectedBlockProps().getCursor();
        if(shape != null)
            shape.render();
    }
    
    @Override
    public void dispose(){
        shader.dispose();
    }
    
}
