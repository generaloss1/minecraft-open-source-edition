package minecraftose.main.item;

import minecraftose.main.text.TextColor;

public enum ItemRarity{

    COMMON   (TextColor.WHITE       ),
    UNCOMMON (TextColor.YELLOW      ),
    RARE     (TextColor.AQUA        ),
    EPIC     (TextColor.LIGHT_PURPLE);

    public final TextColor color;

    ItemRarity(TextColor color){
        this.color = color;
    }

}
