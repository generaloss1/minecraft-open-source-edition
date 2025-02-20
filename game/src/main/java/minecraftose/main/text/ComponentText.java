package minecraftose.main.text;


import jpize.util.color.Color;

public class ComponentText extends Component{

    private final String text;

    public ComponentText(TextStyle style, Color color, String text){
        super(style, color);
        this.text = text;
    }
    
    
    public String getText(){
        return text;
    }
    
    @Override
    public String toString(){
        return text + super.toString();
    }
    
}
