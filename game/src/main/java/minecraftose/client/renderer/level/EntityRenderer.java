package minecraftose.client.renderer.level;

import jpize.util.Disposable;
import minecraftose.main.entity.Entity;
import minecraftose.client.control.camera.GameCamera;
import minecraftose.client.entity.RemotePlayer;
import minecraftose.client.entity.model.PlayerModel;
import minecraftose.client.level.ClientLevel;

public class EntityRenderer implements Disposable{
    
    final LevelRenderer levelRenderer;
    
    public EntityRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
    }
    
    
    public void render(GameCamera camera){
        final ClientLevel level = levelRenderer.getGameRenderer().getSession().getGame().getLevel();
        if(level == null)
            return;

        for(Entity entity : level.getEntities()){
            
            // Remote players
            if(entity instanceof RemotePlayer remotePlayer){
                remotePlayer.updateInterpolation();
                
                final PlayerModel model = remotePlayer.getModel();
                if(model != null){
                    model.animate();
                    model.render(camera);
                }
            }
            
        }
    }
    
    @Override
    public void dispose(){ }
    
}
