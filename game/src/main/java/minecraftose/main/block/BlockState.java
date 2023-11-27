package minecraftose.main.block;

import minecraftose.main.audio.SoundType;

public class BlockState{

    // private MaterialColor color;
    // private PushReaction pushReaction;
    // private boolean blocksMotion;
    // private boolean flammable;
    // private boolean liquid;
    // private boolean solidBlocking;
    // private boolean replaceable;
    // private boolean solid;
    // protected boolean hasCollision;
    // protected float explosionResistance;
    // protected boolean isRandomlyTicking;
    // protected SoundType soundType;
    // protected float friction;
    // protected float speedFactor;
    // protected float jumpFactor;
    // protected boolean dynamicShape;
    // ToIntFunction<BlockState> lightEmission = (state) -> {
    //     return 0;
    // };
    // protected float destroyTime;
    // protected boolean requiresCorrectToolForDrops;
    // protected boolean canOcclude = true;
    // protected boolean isAir;
    // private final StatePredicate isRedstoneConductor;
    // private final StatePredicate isSuffocating;
    // private final StatePredicate isViewBlocking;
    // private final StatePredicate hasPostProcess;
    // private final StatePredicate emissiveRendering;
    // private final OffsetType offsetType;

    public final boolean isAir(){
        return false;
    }

    public final boolean isReplaceable(){
        return false;
    }

    public final boolean isFullCube(){
        return false;
    }

    public final boolean hasCollision(){
        return false;
    }

    public final boolean isRandomlyTicking(){
        return false;
    }

    public final SoundType getSoundType(){
        return null;
    }

    public final float getFriction(){
        return 0;
    }

    public final float getSpeedFactor(){
        return 0;
    }

    public final float getJumpFactor(){
        return 0;
    }

    public final int getLightEmission(){
        return 0;
    }

}
