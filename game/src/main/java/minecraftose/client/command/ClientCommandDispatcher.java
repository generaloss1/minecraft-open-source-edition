package minecraftose.client.command;

import minecraftose.client.Minecraft;
import minecraftose.client.command.vanilla.CCommandOption;
import minecraftose.main.command.Command;
import minecraftose.main.command.CommandContext;
import minecraftose.main.command.argument.CommandArg;
import minecraftose.main.command.node.CommandNodeArg;
import minecraftose.main.command.node.CommandNodeLiteral;
import minecraftose.main.command.source.CommandLocalPlayerSource;
import minecraftose.main.text.Component;
import minecraftose.main.text.TextColor;

import java.util.*;

public class ClientCommandDispatcher{

    private final Minecraft minecraft;
    private final Map<String, CommandNodeLiteral> commands;
    private final CommandLocalPlayerSource source;
    
    public ClientCommandDispatcher(Minecraft minecraft){
        this.minecraft = minecraft;
        this.commands = new HashMap<>();
        this.source = new CommandLocalPlayerSource(minecraft.getPlayer());
        
        // Register commands
        CCommandOption.registerTo(this);
    }
    
    public Minecraft getMinecraft(){
        return minecraft;
    }
    
    
    public Collection<CommandNodeLiteral> getCommands(){
        return commands.values();
    }
    
    public void newCommand(CommandNodeLiteral node){
        commands.put(node.getLiteral(), node);
    }

    public boolean hasCommand(String command){
        return commands.containsKey(command);
    }
    
    public void executeCommand(String commandMessage){
        // Разделяем комманду на аргументы
        final String[] splitCommand = commandMessage.split(" ");
        
        // Проверяем существует ли такая команда в корневом списке
        CommandNodeLiteral targetCommandNode = commands.get(splitCommand[0]);
        if(targetCommandNode == null){
            source.sendMessage(new Component().color(TextColor.RED).text("Command /" + splitCommand[0] + " is not exists"));
            return;
        }else if(!targetCommandNode.requirementsTest(source)){
            source.sendMessage(new Component().color(TextColor.RED).text("You don't have permission"));
            return;
        }
        
        
        // Аргументы
        final String joinedArguments = commandMessage.substring(splitCommand[0].length());
        final List<CommandArg> argumentList = new ArrayList<>();
        
        // Ищем ноду по дереву
        // Проходим по всей строке аргументов
        int index = joinedArguments.startsWith(" ") ? 1 : 0;
        mainCycle: while(index < joinedArguments.length()){
            // Оставшиеся символы
            final String remainingChars = joinedArguments.substring(index);
            final int nextSpace = remainingChars.indexOf(" ");
            
            final Collection<CommandNodeLiteral> children = targetCommandNode.getChildren();
            final List<CommandNodeLiteral> childrenLiteral = children.stream().filter(child -> child.getClass() == CommandNodeLiteral.class).toList();
            final List<CommandNodeArg> childrenArg = children.stream().filter(child -> child.getClass() == CommandNodeArg.class).map(child -> (CommandNodeArg) child).toList();

            // Проходимся по каждому child-node LITERAL и проверяем на равенство

            for(int j = 0; j < childrenLiteral.size(); j++){
                final CommandNodeLiteral child = childrenLiteral.get(j);

                // Находим текущий аргумент
                final String currentArgument = nextSpace == -1 ? remainingChars : remainingChars.substring(0, nextSpace);
                    
                // [DEBUG]: System.out.println("Check literal: " + currentArgument + " by " + child.getClass().getSimpleName() + "(" + child.getLiteral() + ")");
                    
                // Проверяем на равенство
                if(currentArgument.equalsIgnoreCase(child.getLiteral())){
                    targetCommandNode = child;
                    index += currentArgument.length() + 1; // 1 - пробел
                    continue mainCycle;
                // Если не удалось проверить
                }else{
                    // И если этот аргумент оставался последним вариантом
                    if(j + 1 == childrenLiteral.size() && childrenArg.isEmpty()){
                        final int wrongArgEndPointer = nextSpace > 0 ? (nextSpace + index) : joinedArguments.length();
                        source.sendMessage(new Component().color(TextColor.RED).text("Wrong argument: ").reset().text("/" + splitCommand[0] + joinedArguments.substring(0, wrongArgEndPointer)).color(TextColor.RED).text("<-HERE"));
                        return;
                    }
                }
            }

            // Проходимся по каждому child-node ARGUMENT и пытаемся парсить их

            final int oldIndex = index;
            for(int j = 0; j < childrenArg.size(); j++){
                final CommandNodeArg child = childrenArg.get(j);

                // Пытаемся парсить
                final CommandArg arg = child.getArgument();
                final int parsedChars = arg.parse(remainingChars, source);
                // [DEBUG]: System.out.println("Parsed " + remainingChars + " by "+ arg.getClass().getSimpleName() + ": " + parsedChars);

                // Если не удалось разобрать аргумент
                if(parsedChars == 0){
                    // И если этот аргумент оставался последним вариантом
                    if(j + 1 == childrenArg.size()){
                        // Указать на ошибку

                        final int wrongArgEndPointer = nextSpace > 0 ? (nextSpace + index) : joinedArguments.length();
                        source.sendMessage(new Component().color(TextColor.RED).text("Wrong argument: ").reset().text("/" + splitCommand[0] + joinedArguments.substring(0, wrongArgEndPointer)).color(TextColor.RED).text("<-HERE"));
                        return;
                    }else
                        continue;
                }

                index += parsedChars + 1; // 1 - пробел
                argumentList.add(arg);
                targetCommandNode = child;

                break;
            }

            // Если не один нод в списке children не смог парсить joinedArguments.substring(index) (индекс остался тем же)
            if(oldIndex == index){
                // Выбрасываем ошибку
                final int wrongArgEndPointer = nextSpace > 0 ? (nextSpace + index) : joinedArguments.length();
                source.sendMessage(new Component().color(TextColor.RED).text("Wrong argument: ").reset().text("/" + splitCommand[0] + joinedArguments.substring(0, wrongArgEndPointer)).color(TextColor.RED).text("<-HERE"));
                return;
            }
        }
        
        // Check permissions
        if(!targetCommandNode.requirementsTest(source)){
            source.sendMessage(new Component().color(TextColor.RED).text("You don't have permission"));
            return;
        }
        
        // Последняя нода должна содержать интерфейс 'Command' для исполнения команды
        final Command command = targetCommandNode.getCommand();
        if(command == null){
            source.sendMessage(new Component().color(TextColor.RED).text("Wrong arguments"));
            return;
        }
        
        // Исполняем команду
        final CommandContext commandContext = new CommandContext(source, commandMessage, argumentList);
        command.run(commandContext);
    }
    
}
