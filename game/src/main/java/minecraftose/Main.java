package minecraftose;

import jpize.Jpize;
import jpize.io.context.ContextBuilder;
import jpize.math.Maths;
import minecraftose.client.Minecraft;
import minecraftose.main.network.PlayerProfile;

import java.util.HashMap;
import java.util.Map;

public class Main{

    public static PlayerProfile profile;
    public static String sessionToken;
    public static String gameDir;

    public static void main(String[] args){
        // Parse arguments
        final Map<String, String> argsMap = mapArgs(args);

        profile = new PlayerProfile(argsMap.getOrDefault("username", "Player_" + Maths.random(10, 99)));
        sessionToken = argsMap.getOrDefault("sessionToken", "54_54-iWantPizza-54_54");
        gameDir = argsMap.get("gameDir");

        final int width = Integer.parseInt(argsMap.getOrDefault("width", "1280"));
        final int height = Integer.parseInt(argsMap.getOrDefault("height", "720"));

        // Run app
        ContextBuilder.newContext("Minecraft Open Source Edition")
            .ssaaSamples(4)
            .size(width, height)
            .register()
            .setAdapter(Minecraft.INSTANCE);
            //.setAdapter(new LerpTest());
            //.setAdapter(new BiomeGenerator());

        Jpize.runContexts();
    }

    private static Map<String, String> mapArgs(String[] args){
        final Map<String, String> map = new HashMap<>();

        for(int i = 0; i < args.length; i += 2){
            if(i + 1 == args.length)
                break;

            final String key = args[i].substring(2);
            final String value = args[i + 1];

            map.put(key, value);
        }

        return map;
    }

}
