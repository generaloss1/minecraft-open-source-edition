package minecraftose.client.resources;

import minecraftose.main.audio.Sound;
import minecraftose.main.registry.Registry;

public class VanillaSound{

    public static void register(GameResources resources){
        System.out.println("[Resources]: Load Sounds");

        Sound.CLICK.getID(); // init Sound class

        for(Sound sound: Registry.sound.collection())
            resources.registerSound(sound.getID(), "/sound/" + sound.name() + ".ogg");
    }

}
