package minecraftose.main.command.builder;

import minecraftose.main.command.argument.CommandArg;
import minecraftose.main.command.node.CommandNodeArg;
import minecraftose.main.command.node.CommandNodeLiteral;

public class Commands{
    
    public static CommandNodeLiteral literal(String literal){
        return new CommandBuilderLiteral(literal).buildNode();
    }
    
    public static CommandNodeArg argument(String name, CommandArg type){
        return new CommandBuilderArg(name, type).buildArg();
    }
    
    
    
}
