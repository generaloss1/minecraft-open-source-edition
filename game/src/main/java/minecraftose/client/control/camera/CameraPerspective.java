package minecraftose.client.control.camera;

public enum CameraPerspective{

    FIRST_PERSON      (true,  false),
    THIRD_PERSON_BACK (false, true ),
    THIRD_PERSON_FRONT(false, false);

    private final boolean firstPerson;
    private final boolean mirrored;

    CameraPerspective(boolean firstPerson, boolean mirrored){
        this.firstPerson = firstPerson;
        this.mirrored = mirrored;
    }

    public boolean isFirstPerson(){
        return firstPerson;
    }

    public boolean isMirrored(){
        return mirrored;
    }

    public CameraPerspective next(){
        return values()[(ordinal() + 1) % values().length];
    }

}
