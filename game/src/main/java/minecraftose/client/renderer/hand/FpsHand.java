package minecraftose.client.renderer.hand;

import minecraftose.client.control.camera.PlayerCamera;
import minecraftose.client.entity.LocalPlayer;
import minecraftose.client.entity.model.ModelPart;
import minecraftose.client.entity.model.PlayerModel;
import minecraftose.client.renderer.GameRenderer;

public class FpsHand{

    private final GameRenderer gameRenderer;

    public FpsHand(GameRenderer gameRenderer){
        this.gameRenderer = gameRenderer;
    }

    public void render(){
        final PlayerCamera camera = gameRenderer.getMinecraft().getCamera();
        if(!camera.getPerspective().isFirstPerson())
            return;

        final LocalPlayer player = gameRenderer.getMinecraft().getPlayer();

        final PlayerModel model = player.getModel();
        final ModelPart arm = model.getRightArm();
        final ModelPart sleeve = model.getRightSleeve();

        //arm.render;
    }

}
