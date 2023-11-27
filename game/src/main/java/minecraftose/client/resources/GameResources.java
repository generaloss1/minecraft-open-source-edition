package minecraftose.client.resources;

import jpize.graphics.texture.pixmap.PixmapIO;
import jpize.util.Disposable;
import jpize.audio.sound.AudioBuffer;
import jpize.audio.io.AudioLoader;
import jpize.util.file.Resource;
import jpize.graphics.texture.pixmap.PixmapRGBA;
import jpize.graphics.texture.Region;
import jpize.graphics.texture.Texture;
import jpize.graphics.texture.atlas.TextureAtlas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameResources implements Disposable{

    private final TextureAtlas<String> blockAtlas;
    private final List<Resource> blocksToLoadList;

    private final Map<Integer, AudioBuffer> soundList;

    public GameResources(){
        this.blockAtlas = new TextureAtlas<>();
        this.blocksToLoadList = new ArrayList<>();

        this.soundList = new HashMap<>();
    }


    public void load(){
        System.out.println("[Client]: Build blocks atlas");

        for(Resource resource: blocksToLoadList){
            final String name = resource.simpleName();
            final PixmapRGBA pixmap = PixmapIO.load(resource);
            blockAtlas.put(name, pixmap);
        }

        blockAtlas.generate(32 * 16, 32 * 16);
    }


    public void registerBlockRegion(Resource resource){
        blocksToLoadList.add(resource);
    }

    public void registerBlockRegion(String path, String name){
        registerBlockRegion(Resource.internal(path + name + ".png"));
    }

    public Region getBlockRegion(String name){
        return blockAtlas.getRegion(name);
    }

    public Texture getBlocks(){
        return blockAtlas.getTexture();
    }


    public void registerSound(int ID, Resource resource){
        final AudioBuffer buffer = new AudioBuffer();
        AudioLoader.load(buffer, resource);
        soundList.put(ID, buffer);
    }

    public void registerSound(int ID, String path){
        registerSound(ID, Resource.internal(path));
    }

    public AudioBuffer getSound(Integer soundID){
        return soundList.get(soundID);
    }


    public void registerMusic(String ID, String path){

    }

    public AudioBuffer getMusic(String musicID){
        return null;
    }


    @Override
    public void dispose(){
        blocksToLoadList.clear();
        blockAtlas.getTexture().dispose();

        for(AudioBuffer audio: soundList.values())
            audio.dispose();
    }

}
