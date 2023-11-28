package minecraftose.client.renderer.level;

import jpize.util.file.Resource;
import jpize.gl.Gl;
import jpize.gl.glenum.GlDepthFunc;
import jpize.gl.glenum.GlTarget;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.Shader;
import jpize.graphics.util.color.Color;
import minecraftose.client.chunk.ClientChunk;
import minecraftose.client.control.camera.GameCamera;
import minecraftose.client.level.ClientLevel;
import minecraftose.client.options.Options;
import minecraftose.main.time.GameTime;
import jpize.util.Disposable;

import java.util.Collection;

import static minecraftose.main.chunk.ChunkUtils.SIZE;

public class ChunkRenderer implements Disposable{
    
    private final LevelRenderer levelRenderer;
    private final Shader shader, packedShader;
    private int renderedChunks;
    
    public ChunkRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
        
        Gl.depthFunc(GlDepthFunc.LEQUAL);

        this.shader = new Shader(
                Resource.internal("shader/level/chunk/custom-blocks.vert"),
                Resource.internal("shader/level/chunk/custom-blocks.frag")
        );
        this.packedShader = new Shader(
                Resource.internal("shader/level/chunk/packed-voxel.vert"),
                Resource.internal("shader/level/chunk/packed-voxel.frag")
        );

        Gl.polygonOffset(1, 1); // For BlockSelector line rendering
    }
    
    
    public void render(GameCamera camera){
        setupShaders(camera);
        Gl.enable(GlTarget.POLYGON_OFFSET_FILL);
        renderMeshes(camera);
        Gl.disable(GlTarget.POLYGON_OFFSET_FILL);
    }
    
    
    private void renderMeshes(GameCamera camera){
        // Level
        final ClientLevel level = levelRenderer.getGameRenderer().getMinecraft().getLevel();
        if(level == null)
            return;

        // Chunks
        final Collection<ClientChunk> chunks =
            level.getChunkManager().getAllChunks() // Get all chunks
            .stream().filter(camera::isChunkSeen).toList(); // Frustum culling

        // Rendered chunks for the info panel
        renderedChunks = chunks.size();

        // Atlas
        final Texture blockAtlas = levelRenderer.getGameRenderer().getMinecraft().getResources().getBlocks();


        // Update translation matrix
        for(ClientChunk chunk: chunks)
            chunk.updateTranslationMatrix(camera);

        // Render custom blocks
        Gl.disable(GlTarget.CULL_FACE);
        shader.bind();
        for(ClientChunk chunk: chunks){
            shader.setUniform("u_model", chunk.getTranslationMatrix());
            chunk.getMeshStack().getCustom().render();

            if(chunk.getMeshStack().getCustom().isHasPacked()){
                packedShader.bind();
                packedShader.setUniform("u_model", chunk.getTranslationMatrix());
                chunk.getMeshStack().getCustom().renderPacked();
                shader.bind();
            }
        }
        Gl.enable(GlTarget.CULL_FACE);

        // Render solid blocks
        for(ClientChunk chunk: chunks){
            shader.setUniform("u_model", chunk.getTranslationMatrix());
            chunk.getMeshStack().getSolid().render();

            if(chunk.getMeshStack().getSolid().isHasPacked()){
                packedShader.bind();
                packedShader.setUniform("u_model", chunk.getTranslationMatrix());
                chunk.getMeshStack().getSolid().renderPacked();
                shader.bind();
            }
        }

        // Render translucent blocks
        //Gl.disable(GlTarget.CULL_FACE);
        for(ClientChunk chunk: chunks){
            shader.setUniform("u_model", chunk.getTranslationMatrix());
            chunk.getMeshStack().getTranslucent().render();

            if(chunk.getMeshStack().getTranslucent().isHasPacked()){
                packedShader.bind();
                packedShader.setUniform("u_model", chunk.getTranslationMatrix());
                chunk.getMeshStack().getTranslucent().renderPacked();
                shader.bind();
            }
        }
        //Gl.enable(GlTarget.CULL_FACE);
    }
    
    private void setupShaders(GameCamera camera){
        final Options options = levelRenderer.getGameRenderer().getMinecraft().getOptions();
        final Color fogColor = levelRenderer.getSkyRenderer().getFogColor(camera);
        final float fogStart = levelRenderer.getSkyRenderer().getFogStart();
        final float skyBrightness = levelRenderer.getSkyRenderer().getSkyBrightness();
        final Texture blockAtlas = levelRenderer.getGameRenderer().getMinecraft().getResources().getBlocks();
        final GameTime gameTime = levelRenderer.getGameRenderer().getMinecraft().getTime();

        // Shader
        shader.bind();
        shader.setUniform("u_projection", camera.getProjection());
        shader.setUniform("u_view", camera.getView());
        shader.setUniform("u_atlas", blockAtlas);
        
        shader.setUniform("u_renderDistanceBlocks", (options.getRenderDistance() - 1) * SIZE);
        shader.setUniform("u_fogEnabled", options.isFogEnabled());
        shader.setUniform("u_fogColor", fogColor);
        shader.setUniform("u_fogStart", fogStart);
        shader.setUniform("u_skyBrightness", skyBrightness);

        // Packed Shader
        packedShader.bind();
        packedShader.setUniform("u_projection", camera.getProjection());
        packedShader.setUniform("u_view", camera.getView());
        packedShader.setUniform("u_atlas", blockAtlas);

        packedShader.setUniform("u_renderDistanceBlocks", (options.getRenderDistance() - 1) * SIZE);
        packedShader.setUniform("u_fogEnabled", options.isFogEnabled());
        packedShader.setUniform("u_fogColor", fogColor);
        packedShader.setUniform("u_fogStart", fogStart);
        packedShader.setUniform("u_skyBrightness", skyBrightness);
    }
    
    public int getRenderedChunks(){
        return renderedChunks;
    }
    
    @Override
    public void dispose(){
        shader.dispose();
        packedShader.dispose();
    }
    
}
