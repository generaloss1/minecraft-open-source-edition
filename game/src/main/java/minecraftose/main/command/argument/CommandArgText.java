package minecraftose.main.command.argument;

import minecraftose.main.command.source.CommandSource;

public class CommandArgText extends CommandArg{
    
    // Результат парсинга
    private String text;
    
    @Override
    public int parse(String remainingChars, CommandSource source){
        text = remainingChars;
        return remainingChars.length();
    }
    
    public String getText(){
        return text;
    }
    
}