package minecraftose.client.time.var;

public class TickBool{

    private boolean lastValue, value;

    public TickBool(){ }


    public boolean value(){
        return value;
    }


    public void set(boolean newValue){
        updateLast();
        value = newValue;
    }


    private void updateLast(){
        lastValue = value;
    }


    public boolean getLerp(float t){
        return (t > 0.5) ? value : lastValue;
    }

}
