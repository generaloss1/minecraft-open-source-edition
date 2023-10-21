package minecraftose.server.command.vanilla;

import jpize.math.vecmath.vector.Vec3f;
import minecraftose.main.text.Component;
import minecraftose.main.text.TextColor;
import minecraftose.main.command.CommandContext;
import minecraftose.server.command.CommandDispatcher;
import minecraftose.main.command.builder.Commands;
import minecraftose.server.level.ServerLevel;
import minecraftose.server.player.ServerPlayer;

public class CommandSetWorldSpawn{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("setworldspawn")
            .requiresPlayer()
            .executes( CommandSetWorldSpawn::setWorldSpawn)
        );
    }
    
    private static void setWorldSpawn(CommandContext context){
        // Player
        final ServerPlayer sender = context.getSource().asPlayer();
        // Spawn position
        final Vec3f position = sender.getPosition();
        final ServerLevel level = sender.getLevel();
        // Set world spawn
        level.getConfiguration().setWorldSpawn(position.x, position.z);
        context.getServer().getPlayerList().broadcastServerMessage(new Component().color(TextColor.GREEN).text("World spawn set in: " + position));
    }
    
}
