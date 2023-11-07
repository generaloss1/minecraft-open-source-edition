package minecraftose.client.entity;

import jpize.Jpize;
import jpize.math.Mathc;
import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import minecraftose.client.ClientGame;
import minecraftose.client.entity.model.PlayerModel;
import minecraftose.client.time.TickFloat;
import minecraftose.main.entity.Player;
import minecraftose.client.level.ClientLevel;

public class AbstractClientPlayer extends Player{

    private final ClientGame game;

    private volatile PlayerModel model;
    private final Vec3f lastPosition, lerpPosition;
    private final EulerAngles lastRotation, lerpRotation;
    private final TickFloat bobbing, modelBobbing;

    public AbstractClientPlayer(ClientGame game, ClientLevel level, String name){
        super(level, name);
        this.game = game;

        this.bobbing = new TickFloat();
        this.modelBobbing = new TickFloat();

        // Interpolation
        this.lastPosition = new Vec3f();
        this.lerpPosition = new Vec3f();
        this.lastRotation = new EulerAngles();
        this.lerpRotation = new EulerAngles();

        // Player model
        Jpize.execSync( ()-> this.model = new PlayerModel(this) );
    }


    public ClientGame getGame(){
        return game;
    }

    @Override
    public ClientLevel getLevel(){
        return (ClientLevel) super.getLevel();
    }

    @Override
    public void tick(){
        // View Bobbing
        if(isOnGround()){
            bobbing.add((Math.min(0.1F, getVelLenXZ()) - bobbing.get()) * 0.4F); //: lenXZ
        }else
            bobbing.sub(bobbing.get() * 0.4F);

        modelBobbing.add((Math.min(0.1F, getVelLenXZ()) - bobbing.get()) * 0.4F); //: lenXZ
        System.out.println(modelBobbing.get());

        // Interpolation
        lastPosition.set(getPosition());
        lastRotation.set(getRotation());
        
        // Player tick
        super.tick();
    }
    
    public PlayerModel getModel(){
        return model;
    }
    

    public float getVelLenXZ(){
        return Mathc.sqrt(getVelocity().x * getVelocity().x + getVelocity().z * getVelocity().z);
    }

    public float getModelBobbing(){
        final float lerpFactor = game.getTime().getTickLerpFactor();
        return modelBobbing.getLerp(lerpFactor);
    }

    public void updateInterpolation(){
        final float lerpFactor = game.getTime().getTickLerpFactor();

        lerpPosition.lerp(lastPosition, getPosition(), lerpFactor);
        lerpRotation.lerp(lastRotation, getRotation(), lerpFactor);
    }


    public float getBobbing(){
        final float lerpFactor = game.getTime().getTickLerpFactor();
        return bobbing.getLerp(lerpFactor);
    }

    public Vec3f getLerpPosition(){
        return lerpPosition;
    }
    
    public EulerAngles getLerpRotation(){
        return lerpRotation;
    }
    
    public boolean isPositionChanged(){
        return !lastPosition.equals(getPosition());
    }
    
    public boolean isRotationChanged(){
        return !lastRotation.equals(getRotation());
    }
    
}
