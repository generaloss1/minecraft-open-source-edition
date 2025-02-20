package minecraftose.client.resources;


import jpize.audio.al.buffer.AlBuffer;
import jpize.audio.util.AlMusic;
import jpize.gl.texture.Texture2D;
import jpize.util.Disposable;
import jpize.util.atlas.TextureAtlas;
import jpize.util.pixmap.PixmapIO;
import jpize.util.pixmap.PixmapRGBA;
import jpize.util.region.Region;
import jpize.util.res.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameResources implements Disposable {

    private final TextureAtlas<String> blockAtlas;
    private final List<Resource> blocksToLoadList;

    private final Map<Integer, AlBuffer> soundList;

    public GameResources(){
        this.blockAtlas = new TextureAtlas<>();
        this.blockAtlas.enablePaddingFilling(true);
        this.blockAtlas.setPadding(0);
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

        blockAtlas.build(32 * 16, 32 * 16);
    }


    public void registerBlockRegion(Resource resource){
        blocksToLoadList.add(resource);
    }

    public void registerBlockRegion(String path, String name){
        registerBlockRegion(Resource.internal("/" + path + name + ".png"));
    }

    public Region getBlockRegion(String name){
        return blockAtlas.getRegion(name);
    }

    public Texture2D getBlocks(){
        return blockAtlas.getTexture();
    }


    public void registerSound(int ID, Resource resource){
        final AlBuffer buffer = new AlBuffer();
        buffer.load(resource);
        soundList.put(ID, buffer);
    }

    public void registerSound(int ID, String path){
        registerSound(ID, Resource.internal(path));
    }

    public AlBuffer getSound(Integer soundID){
        return soundList.get(soundID);
    }


    public void registerMusic(String ID, String path){

    }

    public AlMusic getMusic(String musicID){
        return null;
    }


    @Override
    public void dispose(){
        blocksToLoadList.clear();
        blockAtlas.getTexture().dispose();

        for(AlBuffer audio: soundList.values())
            audio.dispose();
    }

}
