package minecraftose.server.command.vanilla;

import minecraftose.main.text.Component;
import minecraftose.main.command.CommandContext;
import minecraftose.server.command.CommandDispatcher;
import minecraftose.main.command.builder.Commands;
import minecraftose.main.command.node.CommandNodeLiteral;
import minecraftose.main.command.source.CommandSource;

public class CommandHelp{
    
    public static void registerTo(CommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("help")
            .executes( CommandHelp::sendHelp )
        );
    }
    
    private static void sendHelp(CommandContext context){
        final CommandSource source = context.getSource();
        
        for(CommandNodeLiteral command: context.getServer().getCommandDispatcher().getCommands()){
            source.sendMessage(new Component().text("/" + command.getLiteral()));
        }
    }
    
}
