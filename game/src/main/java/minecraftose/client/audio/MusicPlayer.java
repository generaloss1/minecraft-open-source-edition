package minecraftose.client.audio;

import jpize.audio.util.AlMusic;
import jpize.util.math.Maths;
import minecraftose.client.Minecraft;

public class MusicPlayer {

    private final Minecraft minecraft;

    private AlMusic music;
    private MusicGroup group;
    private int index;

    public MusicPlayer(Minecraft minecraft){
        this.minecraft = minecraft;
    }

    public Minecraft getMinecraft(){
        return minecraft;
    }


    public void setGroup(MusicGroup group){
        if(music != null)
            music.stop();
        if(group.getList().length == 0)
            return;

        this.group = group;
        this.index = Maths.random(0, group.getList().length - 1);
        play();
    }

    private void play(){
        final String musicID = group.getList()[index];
        music = minecraft.getResources().getMusic(musicID);
        if(music == null){
            System.err.println("Music " + musicID + " is not found");
            return;
        }

        music.setGain(0.15);
        music.setOnComplete(this::playNext);
        music.play();

        System.out.println("Playing: " + musicID + "(" + index + ")");
    }

    public void update() {
        if(music != null)
            music.update();
    }

    private void playNext(){
        index++;
        if(index == group.getList().length)
            index = 0;

        play();
    }

}
