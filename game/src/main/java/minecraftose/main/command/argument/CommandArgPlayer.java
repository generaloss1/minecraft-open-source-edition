package minecraftose.main.command.argument;

import minecraftose.server.player.ServerPlayer;
import minecraftose.main.network.PlayerProfile;
import minecraftose.server.Server;
import minecraftose.main.command.source.CommandSource;

public class CommandArgPlayer extends CommandArg{
    
    // Результат парсинга
    private ServerPlayer player;
    
    @Override
    public int parse(String remainingChars, CommandSource source, Server server){
        // Разделяем оставшуюся часть команды на части
        final String[] args = remainingChars.split(" ");
        
        // Если количество частей меньше 1 (имя игрока), завершить парсинг
        if(args.length < 1)
            return 0;
        
        // Находим игрока
        final String playerName = args[0];
        if(PlayerProfile.isNameInvalid(playerName))
            return 0;
        
        player = server.getPlayerList().getPlayer(playerName);
        if(player == null)
            return 0;
        
        return playerName.length();
    }
    
    public ServerPlayer getPlayer(){
        return player;
    }
    
}
