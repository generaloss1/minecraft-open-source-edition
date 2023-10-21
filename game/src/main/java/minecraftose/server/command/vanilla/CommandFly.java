package minecraftose.server.command.vanilla;

import minecraftose.main.text.Component;
import minecraftose.main.command.CommandContext;
import minecraftose.server.command.CommandDispatcher;
import minecraftose.main.command.builder.Commands;
import minecraftose.server.player.ServerPlayer;

public class CommandFly{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("fly")
            .requiresPlayer()
            .executes( CommandFly::toggleFly )
        );
    }
    
    private static void toggleFly(CommandContext context){
        // Player
        final ServerPlayer sender = context.getSource().asPlayer();
        // Fly
        if(sender.isFlyEnabled()){
            sender.setFlyEnabled(false);
            sender.sendMessage(new Component().text("Fly disabled"));
        }else{
            sender.setFlyEnabled(true);
            sender.sendMessage(new Component().text("Fly enabled"));
        }
    }
    
}
