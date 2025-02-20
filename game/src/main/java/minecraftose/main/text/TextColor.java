package minecraftose.main.text;


import jpize.util.color.ImmutableColor;

public enum TextColor{
    
    BLACK        (new ImmutableColor(0   / 255F, 0   / 255F, 0   / 255F)),
    DARK_BLUE    (new ImmutableColor(0   / 255F, 0   / 255F, 170 / 255F)),
    DARK_GREEN   (new ImmutableColor(0   / 255F, 170 / 255F, 0   / 255F)),
    DARK_AQUA    (new ImmutableColor(0   / 255F, 170 / 255F, 170 / 255F)),
    DARK_RED     (new ImmutableColor(200 / 255F, 0   / 255F, 0   / 255F)),
    DARK_PURPLE  (new ImmutableColor(170 / 255F, 0   / 255F, 170 / 255F)),
    ORANGE       (new ImmutableColor(255 / 255F, 170 / 255F, 0   / 255F)),
    GRAY         (new ImmutableColor(170 / 255F, 170 / 255F, 170 / 255F)),
    DARK_GRAY    (new ImmutableColor(85  / 255F, 85  / 255F, 85  / 255F)),
    BLUE         (new ImmutableColor(30  / 255F, 144 / 255F, 255 / 255F)),
    GREEN        (new ImmutableColor(85  / 255F, 255 / 255F, 85  / 255F)),
    AQUA         (new ImmutableColor(85  / 255F, 255 / 255F, 255 / 255F)),
    RED          (new ImmutableColor(255 / 255F, 85  / 255F, 85  / 255F)),
    LIGHT_PURPLE (new ImmutableColor(255 / 255F, 85  / 255F, 255 / 255F)),
    YELLOW       (new ImmutableColor(255 / 255F, 255 / 255F, 85  / 255F)),
    WHITE        (new ImmutableColor(255 / 255F, 255 / 255F, 255 / 255F));
    
    
    public final ImmutableColor color;
    
    TextColor(ImmutableColor color){
        this.color = color;
    }
    
}
