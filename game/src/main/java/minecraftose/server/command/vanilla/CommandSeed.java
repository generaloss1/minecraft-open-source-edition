package minecraftose.server.command.vanilla;

import minecraftose.main.command.CommandContext;
import minecraftose.main.command.builder.Commands;
import minecraftose.main.text.Component;
import minecraftose.main.text.TextColor;
import minecraftose.server.command.CommandDispatcher;
import minecraftose.server.player.ServerPlayer;

public class CommandSeed{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("seed")
            .requiresPlayer()
            .executes( CommandSeed::sendSeed)
        );
    }
    
    private static void sendSeed(CommandContext context){
        // Player
        final ServerPlayer sender = context.getSource().asPlayer();
        sender.sendMessage(new Component().color(TextColor.GREEN).text("World seed: " + sender.getLevel().getConfiguration().getSeed()));
    }
    
}
