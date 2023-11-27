package minecraftose.client.renderer.hand;

import minecraftose.client.control.camera.GameCamera;
import minecraftose.client.control.camera.PerspectiveType;
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
        final GameCamera camera = gameRenderer.getSession().getGame().getCamera();
        if(camera.getPerspective() != PerspectiveType.FIRST_PERSON)
            return;

        final LocalPlayer player = gameRenderer.getSession().getGame().getPlayer();

        final PlayerModel model = player.getModel();
        final ModelPart arm = model.getRightArm();
        final ModelPart sleeve = model.getRightSleeve();

        //arm.render;
    }

}
