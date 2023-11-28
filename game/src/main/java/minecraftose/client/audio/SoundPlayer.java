package minecraftose.client.audio;

import jpize.al.listener.AlListener;
import jpize.util.Disposable;
import jpize.audio.sound.AudioBuffer;
import jpize.audio.sound.AudioSource;
import minecraftose.client.Minecraft;
import minecraftose.main.audio.Sound;

public class SoundPlayer implements Disposable{

    public static final int MAX_SOUND_SOURCES = 64;


    private final Minecraft minecraft;
    private final AudioSource[] sources;

    public SoundPlayer(Minecraft minecraft){
        this.minecraft = minecraft;

        System.out.println("[Client]: Initialize audio player");

        this.sources = new AudioSource[MAX_SOUND_SOURCES];
        for(int i = 0; i < sources.length; i++){
            sources[i] = new AudioSource();
            sources[i].setRelative(false); //: ?
        }

        AlListener.setOrientation(0, 0, 1, 0, 1, 0); //: check it
    }


    public void play(Sound sound, float volume, float pitch, float x, float y, float z){
        final AudioBuffer buffer = minecraft.getResources().getSound(sound.getID());
        if(buffer == null){
            System.err.println("Sound " + sound.name() + " is not found");
            return;
        }
        play(buffer, volume, pitch * sound.pitch(), x, y, z);
    }

    public void play(AudioBuffer buffer, float volume, float pitch, float x, float y, float z){
        if(buffer == null)
            return;

        final int sourceIndex = getFirstFreeIndex();
        if(sourceIndex == -1)
            return;

        final AudioSource source = sources[sourceIndex];
        source.setBuffer(buffer);
        source.setGain(volume);
        source.setPitch(pitch);
        source.setPosition(x, y, z);
        source.play();
    }

    private int getFirstFreeIndex(){
        for(int i = 0; i < sources.length; i++)
            if(!sources[i].isPlaying())
                return i;

        return -1;
    }


    public int getPlayingSoundsNum(){
        int playing = 0;
        for(AudioSource source: sources)
            if(source.isPlaying())
                playing++;

        return playing;
    }


    @Override
    public void dispose(){
        for(AudioSource source: sources)
            source.dispose();
    }

}
