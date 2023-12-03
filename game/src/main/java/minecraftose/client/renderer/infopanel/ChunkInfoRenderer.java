package minecraftose.client.renderer.infopanel;

import jpize.util.Disposable;
import jpize.util.Resizable;
import jpize.graphics.camera.CenteredOrthographicCamera;
import jpize.graphics.util.batch.TextureBatch;
import minecraftose.client.control.camera.PlayerCamera;
import minecraftose.client.renderer.GameRenderer;
import minecraftose.server.IntegratedServer;
import minecraftose.server.level.LevelS;

import java.util.Collection;

public class ChunkInfoRenderer implements Resizable, Disposable{

    private final GameRenderer gameRenderer;
    private final TextureBatch batch;
    private final CenteredOrthographicCamera camera;
    private boolean open;

    public ChunkInfoRenderer(GameRenderer gameRenderer){
        this.gameRenderer = gameRenderer;
        this.batch = new TextureBatch(5000);
        this.camera = new CenteredOrthographicCamera();
    }

    public GameRenderer getGameRenderer(){
        return gameRenderer;
    }


    public void render(){
        if(!open)
            return;

        final IntegratedServer server = gameRenderer.getMinecraft().getIntegratedServer();
        if(server == null)
            return;

        final PlayerCamera playerCamera = gameRenderer.getMinecraft().getCamera();
        if(playerCamera == null)
            return;

        final float size = 20;

        camera.update();
        camera.getPosition().set(playerCamera.getPosition().xz().mul(1 / 16F * size));
        camera.setRotation(-playerCamera.getRotation().yaw + 90);

        batch.setAlpha(0.3);
        batch.begin(camera);

        final Collection<LevelS> levels = server.getLevelManager().getLoadedLevels(); //: WHERE RENDER?
        // for(LevelS level: levels)
        //     level.getChunkProvider().render(batch, size);

        batch.end();
    }


    public boolean isOpen(){
        return open;
    }

    public void setOpen(boolean open){
        this.open = open;
    }


    @Override
    public void resize(int width, int height){
        camera.resize(width, height);
    }

    @Override
    public void dispose(){
        batch.dispose();
    }

}
