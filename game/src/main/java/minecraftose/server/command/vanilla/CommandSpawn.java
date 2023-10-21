package minecraftose.server.command.vanilla;

import jpize.math.vecmath.vector.Vec3f;
import minecraftose.main.text.Component;
import minecraftose.main.command.CommandContext;
import minecraftose.server.command.CommandDispatcher;
import minecraftose.main.command.builder.Commands;
import minecraftose.server.level.ServerLevel;
import minecraftose.server.player.ServerPlayer;

public class CommandSpawn{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("spawn")
            .requiresPlayer()
            .executes( CommandSpawn::teleportToSpawn )
        );
    }
    
    private static void teleportToSpawn(CommandContext context){
        // Player
        final ServerPlayer sender = context.getSource().asPlayer();
        // Spawn position
        final ServerLevel level = (ServerLevel) sender.getLevel();
        final Vec3f spawnPosition = level.getSpawnPosition();
        // Teleport
        sender.teleport(spawnPosition);
        sender.sendMessage(new Component().text("You teleported to spawn"));
    }
    
}
