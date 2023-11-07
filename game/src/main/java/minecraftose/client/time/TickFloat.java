package minecraftose.client.time;

import jpize.math.Maths;

public class TickFloat{

    private float lastValue, value;

    public TickFloat(){ }


    public float get(){
        return value;
    }

    public void set(float newValue){
        lastValue = value;
        value = newValue;
    }


    public void add(float addValue){
        lastValue = value;
        value += addValue;
    }

    public void sub(float addValue){
        lastValue = value;
        value -= addValue;
    }


    public float getLerp(float t){
        return Maths.lerp(lastValue, value, t);
    }

}
