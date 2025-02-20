package minecraftose.client.renderer.chat;

import jpize.app.Jpize;
import jpize.gl.tesselation.GlScissor;
import jpize.gl.texture.Texture2D;
import jpize.util.Disposable;
import jpize.util.font.Font;
import jpize.util.math.vector.Vec2f;
import jpize.util.mesh.TextureBatch;
import jpize.util.region.TextureRegion;
import minecraftose.client.chat.Chat;
import minecraftose.client.chat.ChatMessage;
import minecraftose.client.renderer.GameRenderer;
import minecraftose.client.renderer.text.TextComponentBatch;
import minecraftose.main.text.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRenderer implements Disposable {
    
    private static final float MSG_LIFE_TIME_SEC = 6;
    
    private final GameRenderer gameRenderer;
    private final TextureBatch batch;
    private final TextComponentBatch textBatch;
    private final int chatX, chatY;
    private float scroll;
    private float scrollMotion;
    
    private final Map<Integer, Texture2D> skins;
    private final TextureRegion headRegion, hatRegion;
    private final GlScissor<String> scissor;
    
    public ChatRenderer(GameRenderer gameRenderer){
        this.gameRenderer = gameRenderer;
        this.textBatch = gameRenderer.getTextComponentBatch();
        this.batch = textBatch.getBatch();
        
        chatX = 10;
        chatY = 10;
        
        skins = new HashMap<>();
        for(int i = 1; i <= 21; i++)
            skins.put(i, new Texture2D("/texture/skin/skin" + i + ".png"));
        
        headRegion = new TextureRegion(skins.get(1), 8, 8, 8, 8);
        hatRegion = new TextureRegion(skins.get(1), 40, 8, 8, 8);

        scissor = new GlScissor<>();
    }
    
    
    public void render(){
        batch.setup();
        textBatch.setBackgroundColor(0, 0, 0, 0);
        
        final float chatHeight = Jpize.getHeight() / 2F;
        final float chatWidth = Jpize.getWidth() / 2F;
        final Font font = textBatch.getFont();
        final Vec2f scale = font.getRenderOptions().scale();
        final float lineAdvance = font.getLineAdvance() * scale.y;
        
        final Chat chat = gameRenderer.getMinecraft().getChat();
        final List<ChatMessage> messages = chat.getMessages();
        
        final float openedChatY = chatY + (chat.isOpened() ? lineAdvance + 10 : 0);
        float chatMessagesHeight = 0;
        font.getRenderOptions().setWrapWidth(chatWidth);
        for(ChatMessage message: messages)
            chatMessagesHeight += font.getTextHeight(message.getComponents().toString());
        
        // Enter
        if(chat.isOpened()){
            final String enteringText = chat.getEnteringText();
            final float lineWidth = font.getTextWidth(enteringText);
            
            batch.drawRectBlack(chatX, chatY + font.getDescentScaled(), Math.max(lineWidth, chatWidth), lineAdvance, 0.4F);
            
            final float cursorLineWidth = font.getTextWidth(enteringText.substring(0, chat.getCursorX()));
            
            textBatch.drawComponent(new Component().text(enteringText), chatX, chatY);

            if(chat.isCursorRender()){
                batch.scale().set(scale);
                batch.drawRect(chatX + cursorLineWidth, chatY + font.getDescentScaled(), lineAdvance, 1, 1, 1, 1);
                batch.scale().set(1F);
            }
        }
        
        // Scroll
        if(chat.isOpened() && Jpize.input().isCursorInRect(chatX, openedChatY, chatWidth, chatHeight))
            scrollMotion -= Jpize.getScroll() * Jpize.getDeltaTime() * lineAdvance * 10;
        
        scroll += scrollMotion;
        scrollMotion *= 0.95F;
        
        if(!chat.isOpened())
            scroll = 0;
        
        scroll = Math.min(0, scroll);
        scroll = Math.max(Math.min(0, chatHeight - chatMessagesHeight), scroll);
        
        // Chat
        final float headSize = lineAdvance + 0;
        final float headAdvance = headSize * 1.5F;
        final float headDrawX = (headAdvance - headSize) / 2;
        final float headDrawY = (lineAdvance - headSize) / 2;
        
        scissor.put("chat",  chatX, openedChatY + font.getDescentScaled(), chatWidth + headAdvance, chatHeight); // Scissors begin
        scissor.apply();
        font.getRenderOptions().setWrapWidth(chatWidth + headAdvance);

        int textAdvanceY = 0;
        for(int i = messages.size() - 1; i >= 0; i--){
            final ChatMessage message = messages.get(i);
            
            float alpha = 1F;
            if(!chat.isOpened()){
                if(message.getSeconds() < MSG_LIFE_TIME_SEC)
                    alpha = (float) Math.min(1, MSG_LIFE_TIME_SEC - message.getSeconds());
                else
                    continue;
            }
            
            final float textHeight = textBatch.getFont().getTextHeight(message.getComponents().toString());
            final float renderChatY = openedChatY + textAdvanceY + scroll;
            final float lineWrapAdvanceY = textHeight - lineAdvance;
            
            final boolean isPlayer = message.getSource().isPlayer();

            // Render background
            batch.drawRectBlack(chatX, renderChatY + font.getDescentScaled(), chatWidth + headAdvance, textHeight, 0.4F * alpha);
            
            // Render head
            if(isPlayer){
                final int skinID = Math.abs(message.getSource().asPlayer().getPlayerName().hashCode()) % 20 + 1;
                final Texture2D skin = skins.get(skinID);
                
                batch.setAlpha(alpha);
                batch.draw(skin, headRegion, chatX + headDrawX, renderChatY + lineWrapAdvanceY + headDrawY, headSize, headSize);
                
                batch.setTransformOrigin(0.5, 0.5);
                batch.scale().set(1.1);
                batch.draw(skin, hatRegion, chatX + headDrawX, renderChatY + lineWrapAdvanceY + headDrawY, headSize, headSize);
                batch.scale().set(1);
            }
            // Render text
            textBatch.drawComponents(message.getComponents(), chatX + headAdvance, renderChatY + lineWrapAdvanceY, chatWidth, alpha);
            
            textAdvanceY += (int) textHeight;
        }
        
        batch.render();
        scissor.remove("chat"); // Scissors end
        scissor.apply();
    }
    
    @Override
    public void dispose(){
        batch.dispose();
    }
    
}
