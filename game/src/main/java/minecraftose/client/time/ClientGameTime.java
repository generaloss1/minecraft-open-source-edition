package minecraftose.client.time;

import jpize.util.time.DeltaTimeCounter;
import minecraftose.main.time.GameTime;

public class ClientGameTime extends GameTime{

    private final DeltaTimeCounter dtCounter;
    private long prevTickPointMillis;

    public ClientGameTime(){
        this.dtCounter = new DeltaTimeCounter();
    }

    @Override
    public void tick(){
        super.tick();
        dtCounter.update();
        this.prevTickPointMillis = System.currentTimeMillis();
    }


    public float getDeltaTime(){
        return dtCounter.get();
    }

    private float getMillisLerpFactor(){
        return (float) (System.currentTimeMillis() - prevTickPointMillis);
    }

    private float getSecondLerpFactor(){
        return getMillisLerpFactor() / MILLIS_PER_SECOND;
    }

    public float getTickLerpFactor(){
        return getMillisLerpFactor() / MILLIS_IN_SECOND / getDeltaTime();
    }

    public float getLerpSeconds(){
        return super.getSeconds() + getSecondLerpFactor();
    }

    public float getLerpMinutes(){
        return getLerpSeconds() / SECONDS_IN_MINUTE;
    }

    public float getLerpDays(){
        return getLerpSeconds() / SECONDS_IN_DAY;
    }

}
