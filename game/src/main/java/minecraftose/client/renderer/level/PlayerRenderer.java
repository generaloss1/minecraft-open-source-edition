package minecraftose.client.renderer.level;

import jpize.util.Disposable;
import minecraftose.client.control.camera.GameCamera;
import minecraftose.client.control.camera.PerspectiveType;
import minecraftose.client.entity.model.PlayerModel;
import minecraftose.client.options.Options;

public class PlayerRenderer implements Disposable{
    
    final LevelRenderer levelRenderer;
    
    public PlayerRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
    }
    
    
    public void render(GameCamera camera){
        final Options options = levelRenderer.getGameRenderer().getMinecraft().getOptions();
        final PlayerModel playerModel = levelRenderer.getGameRenderer().getMinecraft().getPlayer().getModel();
        
        // Render player
        if(playerModel != null){
            playerModel.animate();
            if(camera.getPerspective() != PerspectiveType.FIRST_PERSON || options.isFirstPersonModel())
                playerModel.render(camera);
        }
    }
    
    @Override
    public void dispose(){ }
    
}