package minecraftose.client.renderer.text;

import jpize.app.Jpize;
import jpize.gl.texture.Texture2D;
import jpize.util.Disposable;
import jpize.util.color.AbstractColor;
import jpize.util.color.Color;
import jpize.util.font.Font;
import jpize.util.font.Glyph;
import jpize.util.math.Maths;
import jpize.util.math.vector.Vec2f;
import jpize.util.mesh.TextureBatch;
import jpize.util.res.Resource;
import minecraftose.main.text.Component;
import minecraftose.main.text.ComponentText;
import minecraftose.main.text.TextStyle;

import java.util.List;

public class TextComponentBatch implements Disposable {
    
    private final TextureBatch batch;
    private final Font font;
    private final Color background;
    
    public TextComponentBatch(){
        this.batch = new TextureBatch();
        this.background = new Color();

        this.font = new Font().loadFNT(Resource.internal("/font/minecraft/default.fnt"), false);
        // this.font = FontLoader.loadTrueType("font/faithful/faithful.ttf", 32);
        this.font.getRenderOptions().setLineGap(1F);
    }
    
    
    public void updateScale(){
        font.getRenderOptions().scale().set(Maths.round(Jpize.getHeight() / 330F));
    }
    
    
    public void drawComponents(List<ComponentText> components, float x, float y, float width, float alpha){
        // Подготовим batch и font
        batch.setup();

        final Vec2f scale = font.getRenderOptions().scale();
        
        // Позиция следующего символа
        final float lineAdvance = font.getLineAdvance();
        
        float advanceX = 0;
        float advanceY = 0;
        
        // Фон
        if(background.alpha != 0){
            final StringBuilder text = new StringBuilder();
            for(ComponentText component: components)
                text.append(component.toString());
            
            final Vec2f bounds = font.getTextBounds(text.toString());
            batch.drawRect(x, y, bounds.x, bounds.y,  background);
        }
        
        // Пройдемся по каждому компоненту дерева, разложенному в плоский список
        for(ComponentText component: components){
            
            // Стиль компонента
            final TextStyle style = component.getStyle();
            batch.shear(style.isItalic() ? 15 : 0, 0);
            
            // Цвет компонента
            final Color color = component.getColor().copy();
            color.setAlpha(color.alpha * alpha);
            
            // Текст
            final String text = component.getText();
            
            // Рендерим каждый символ
            for(int i = 0; i < text.length(); i++){
                final int code = text.charAt(i);
                
                // Перенос на новую строку соответствующим символом
                if(code == 10){
                    advanceX = 0;
                    advanceY -= lineAdvance;
                    continue;
                }
                
                // Глиф
                final Glyph glyph = font.glyphs().get(code);
                if(glyph == null)
                    continue;
                
                // Перенос на новую строку если текст не вмещается в заданную ширину
                if(width > 0 && (advanceX + glyph.advanceX) * scale.x >= width){
                    advanceX = 0;
                    advanceY -= lineAdvance;
                }
                
                // Координаты глифа
                final float glyphX = x + (advanceX + glyph.offset.x) * scale.x;
                final float glyphY = y + (advanceY + glyph.offset.y) * scale.y;
                
                // Рендерим тень
                batch.setColor(color.copy().mulRGB(0.25));
                final Texture2D page = font.pages().get(glyph.pageID);
                batch.draw(page, glyph.region, glyphX + scale.x, glyphY - scale.y, glyph.size.x * scale.x, glyph.size.y * scale.y);

                // Рендерим основной символ
                batch.setColor(color);
                batch.draw(page, glyph.region, glyphX, glyphY, glyph.size.x * scale.x, glyph.size.y * scale.y);

                advanceX += glyph.advanceX;
            }
        }
        
        // Закончим отрисовку
        batch.shear(0, 0);
        batch.resetColor();
        batch.render();
    }
    
    
    public void drawComponents(List<ComponentText> components, float x, float y, float alpha){
        drawComponents(components, x, y, -1, alpha);
    }
    
    public void drawComponents(List<ComponentText> components, float x, float y){
        drawComponents(components, x, y, 1F);
    }
    
    
    public void drawComponent(Component component, float x, float y, float width, float alpha){
        drawComponents(component.toFlatList(), x, y, width, alpha);
    }
    
    public void drawComponent(Component component, float x, float y, float alpha){
        drawComponents(component.toFlatList(), x, y, -1, alpha);
    }
    
    public void drawComponent(Component component, float x, float y){
        drawComponents(component.toFlatList(), x, y, 1F);
    }
    
    
    public TextureBatch getBatch(){
        return batch;
    }
    
    public Font getFont(){
        return font;
    }
    
    
    public void setBackgroundColor(double r, double g, double b, double a){
        background.set(r, g, b, a);
    }
    
    public void setBackgroundColor(double r, double g, double b){
        background.set(r, g, b);
    }
    
    public void setBackgroundColor(AbstractColor color){
        background.set(color);
    }
    
    
    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
    }

}
