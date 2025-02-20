package minecraftose.main.text;

import jpize.util.color.Color;
import minecraftose.main.lang.Language;

public class ComponentTranslate extends Component{
    
    private final String translationKey;
    private final Component[] arguments;
    private Language translatedWith;
    
    public ComponentTranslate(TextStyle style, Color color, String translationKey, Component... arguments){
        super(style, color);
        this.translationKey = translationKey;
        this.arguments = arguments;
    }
    
    
    public void updateTranslation(Language language){
        if(language == translatedWith)
            return;
        
        final String translation = language.getTranslation(translationKey);
        final String[] translationParts = translation.split("%s");
        
        int i = 0;
        super.formattedText(translationParts[0]);
        for(Component argument: arguments){
            super.addComponent(argument);
            super.formattedText(translationParts[++i]);
        }
        
        translatedWith = language;
    }
    
    
    @Override
    public String toString(){
        return super.toString();
    }
    
}
