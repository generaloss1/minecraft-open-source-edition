package minecraftose.client.audio;

import jpize.util.Disposable;
import jpize.audio.sound.AudioBuffer;
import jpize.audio.sound.AudioSource;
import jpize.math.Maths;
import minecraftose.client.Minecraft;

public class MusicPlayer implements Disposable{

    private final Minecraft minecraft;

    private final AudioSource source;
    private MusicGroup group;
    private int index;

    public MusicPlayer(Minecraft minecraft){
        this.minecraft = minecraft;
        this.source = new AudioSource();
        this.source.setGain(0.15);
    }

    public Minecraft getMinecraft(){
        return minecraft;
    }


    public void setGroup(MusicGroup group){
        source.stop();
        if(group.getList().length == 0)
            return;

        this.group = group;
        this.index = Maths.random(0, group.getList().length - 1);
        play();
    }

    private void play(){
        final String musicID = group.getList()[index];
        final AudioBuffer buffer = minecraft.getResources().getMusic(musicID);
        if(buffer == null){
            System.err.println("Music " + musicID + " is not found");
            return;
        }

        source.setBuffer(buffer);
        source.play(this::playNext);

        System.out.println("Playing: " + musicID + "(" + index + ")");
    }

    private void playNext(){
        index++;
        if(index == group.getList().length)
            index = 0;

        play();
    }

    @Override
    public void dispose(){
        source.dispose();
    }

}
