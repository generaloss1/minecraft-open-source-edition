package minecraftose.server.command.vanilla;

import minecraftose.main.command.CommandContext;
import minecraftose.main.command.builder.Commands;
import minecraftose.main.command.node.CommandNodeLiteral;
import minecraftose.main.command.source.CommandSource;
import minecraftose.main.text.Component;
import minecraftose.server.command.ServerCommandDispatcher;

public class SCommandHelp{
    
    public static void registerTo(ServerCommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("help")
            .executes( SCommandHelp::sendHelp )
        );
    }
    
    private static void sendHelp(CommandContext context){
        final CommandSource source = context.getSource();
        
        for(CommandNodeLiteral command: context.getSource().tryToGetServer().getCommandDispatcher().getCommands())
            source.sendMessage("/" + command.getLiteral());

        source.sendMessage("");
        source.sendMessage(new Component().text("page 1 of 1"));
    }
    
}
