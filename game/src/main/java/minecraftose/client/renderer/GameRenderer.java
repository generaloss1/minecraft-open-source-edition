package minecraftose.client.renderer;

import jpize.io.context.JpizeApplication;
import minecraftose.client.Minecraft;
import minecraftose.client.renderer.chat.ChatRenderer;
import minecraftose.client.renderer.hand.FpsHand;
import minecraftose.client.renderer.infopanel.ChunkInfoRenderer;
import minecraftose.client.renderer.infopanel.InfoRenderer;
import minecraftose.client.renderer.level.LevelRenderer;
import minecraftose.client.renderer.text.TextComponentBatch;

public class GameRenderer extends JpizeApplication{
    
    private final Minecraft session;
    
    private final TextComponentBatch textComponentBatch;
    private final LevelRenderer levelRenderer;
    private final InfoRenderer infoRenderer;
    private final ChunkInfoRenderer chunkInfoRenderer;
    private final ChatRenderer chatRenderer;
    private final FpsHand hand;

    public GameRenderer(Minecraft session){
        this.session = session;
        
        this.textComponentBatch = new TextComponentBatch();
        this.levelRenderer = new LevelRenderer(this);
        this.infoRenderer = new InfoRenderer(this);
        this.chunkInfoRenderer = new ChunkInfoRenderer(this);
        this.chatRenderer = new ChatRenderer(this);
        this.hand = new FpsHand(this);
    }
    
    public Minecraft getSession(){
        return session;
    }
    
    
    @Override
    public void render(){
        textComponentBatch.updateScale();
        levelRenderer.render();
        infoRenderer.render();
        chatRenderer.render();
        chunkInfoRenderer.render();
        hand.render();
    }
    
    @Override
    public void resize(int width, int height){
        levelRenderer.resize(width, height);
        chunkInfoRenderer.resize(width, height);
    }
    
    @Override
    public void dispose(){
        textComponentBatch.dispose();
        levelRenderer.dispose();
        infoRenderer.dispose();
        chatRenderer.dispose();
        chunkInfoRenderer.dispose();
    }
    
    
    public final TextComponentBatch getTextComponentBatch(){
        return textComponentBatch;
    }
    
    public LevelRenderer getWorldRenderer(){
        return levelRenderer;
    }
    
    public InfoRenderer getInfoRenderer(){
        return infoRenderer;
    }

    public ChunkInfoRenderer getChunkInfoRenderer(){
        return chunkInfoRenderer;
    }

    public ChatRenderer getChatRenderer(){
        return chatRenderer;
    }

    public FpsHand getFirstPersonHand(){
        return hand;
    }
    
}
