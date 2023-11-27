package minecraftose.main.audio;

import minecraftose.main.block.BlockSetType;

public class SoundType{

    public static final SoundType GRASS  = new SoundType(SoundGroup.DIG_GRASS , SoundGroup.DIG_GRASS , SoundGroup.STEP_GRASS );
    public static final SoundType GRAVEL = new SoundType(SoundGroup.DIG_GRAVEL, SoundGroup.DIG_GRAVEL, SoundGroup.STEP_GRAVEL);
    public static final SoundType STONE  = new SoundType(SoundGroup.DIG_STONE , SoundGroup.DIG_STONE , SoundGroup.STEP_STONE );
    public static final SoundType WOOD   = new SoundType(SoundGroup.DIG_WOOD  , SoundGroup.DIG_WOOD  , SoundGroup.STEP_WOOD  );
    public static final SoundType SAND   = new SoundType(SoundGroup.DIG_SAND  , SoundGroup.DIG_SAND  , SoundGroup.STEP_SAND  );
    public static final SoundType GLASS  = new SoundType(SoundGroup.DIG_STONE , SoundGroup.GLASS     , SoundGroup.STEP_STONE );
    public static final SoundType LADDER = new SoundType(SoundGroup.DIG_WOOD  , SoundGroup.DIG_WOOD  , SoundGroup.STEP_LADDER);
    public static final SoundType CLOTH  = new SoundType(SoundGroup.DIG_CLOTH , SoundGroup.DIG_CLOTH , SoundGroup.STEP_CLOTH );
    public static final SoundType SNOWY_GRASS_BLOCK = new SoundType(SoundGroup.DIG_GRASS, SoundGroup.DIG_GRASS, SoundGroup.STEP_SNOW);

    
    private final SoundGroup place, destroy, step;

    public SoundType(SoundGroup place, SoundGroup destroy, SoundGroup step){
        this.place = place;
        this.destroy = destroy;
        this.step = step;
    }
    
    public SoundGroup getPlaceSounds(){
        return place;
    }
    
    public SoundGroup getDestroySounds(){
        return destroy;
    }
    
    public SoundGroup getStepSounds(){
        return step;
    }


    public Sound randomPlaceSound(){
        return place.random();
    }

    public Sound randomDestroySound(){
        return destroy.random();
    }

    public Sound randomStepSound(){
        return step.random();
    }


    public Sound randomSound(BlockSetType setType){
        return switch(setType){
            case PLACE, REPLACE -> randomPlaceSound();
            case DESTROY -> randomDestroySound();
        };
    }

}
