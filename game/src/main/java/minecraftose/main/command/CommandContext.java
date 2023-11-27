package minecraftose.main.command;

import minecraftose.main.command.argument.CommandArg;
import minecraftose.main.command.source.CommandSource;

import java.util.List;

public class CommandContext{
    
    private final CommandSource source;
    private final String command;
    private final List<CommandArg> arguments;
    
    public CommandContext(CommandSource source, String command, List<CommandArg> arguments){
        this.source = source;
        this.command = command;
        this.arguments = arguments;
    }
    
    public CommandSource getSource(){
        return source;
    }
    
    public String getCommand(){
        return command;
    }
    
    public CommandArg getArg(int index){
        return arguments.get(index);
    }
    
}
