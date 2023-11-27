package minecraftose.client.command.vanilla;

import minecraftose.client.ClientGame;
import minecraftose.client.command.ClientCommandDispatcher;
import minecraftose.client.options.Options;
import minecraftose.main.command.CommandContext;
import minecraftose.main.command.argument.CommandArg;
import minecraftose.main.command.builder.Commands;
import minecraftose.main.network.packet.c2s.game.C2SPacketRenderDistance;

public class CCommandOption{
    
    public static void registerTo(ClientCommandDispatcher dispatcher){
        final ClientGame game = dispatcher.getGame();
        final Options options = game.getSession().getOptions();

        dispatcher.newCommand(Commands.literal("option")
            // Render Distance
            .then(Commands.literal("distance")
                .then(Commands.argument("distance", CommandArg.numInt())
                    .executes((context) -> CCommandOption.setRenderDistance(context, options, game))
                )
                .executes((context) -> context.getSource().sendMessage("Render distance = " + options.getRenderDistance() + " chunks"))
            )
            // FOV
            .then(Commands.literal("fov")
                .then(Commands.argument("fov", CommandArg.numFloat())
                    .executes((context) -> CCommandOption.setFOV(context, options, game))
                )
                .executes((context) -> context.getSource().sendMessage("Field of View = " + options.getFieldOfView()))
            )

        );
    }
    
    private static void setRenderDistance(CommandContext context, Options options, ClientGame game){
        final int distance = context.getArg(0).asNumInt().getInt();
        options.setRenderDistance(distance);
        game.getConnection().sendPacket(new C2SPacketRenderDistance(distance));
        context.getSource().sendMessage("Render distance set to " + distance + " chunks");
    }

    private static void setFOV(CommandContext context, Options options, ClientGame game){
        final float fov = context.getArg(0).asNumFloat().getFloat();
        options.setFieldOfView(fov);
        context.getSource().sendMessage("Field of View set to " + fov);
    }
    
}
