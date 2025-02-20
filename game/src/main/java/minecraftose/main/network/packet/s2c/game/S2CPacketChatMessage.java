package minecraftose.main.network.packet.s2c.game;

import minecraftose.client.network.ClientPacketHandler;
import minecraftose.main.chat.source.MessageSource;
import minecraftose.main.chat.source.MessageSourcePlayer;
import minecraftose.main.chat.source.MessageSourceServer;
import minecraftose.main.chat.source.MessageSources;
import minecraftose.main.text.ComponentText;
import minecraftose.main.text.TextStyle;
import jpize.util.io.ExtDataInputStream;
import jpize.util.io.ExtDataOutputStream;
import jpize.util.net.packet.NetPacket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class S2CPacketChatMessage extends NetPacket<ClientPacketHandler>{
    
        
    public S2CPacketChatMessage(){}
    
    
    public MessageSource source;
    public String playerName;
    public List<ComponentText> components;
    
    private S2CPacketChatMessage(MessageSource source, List<ComponentText> components){
        this();
        this.source = source;
        this.components = components;
    }
    
    public S2CPacketChatMessage(List<ComponentText> components){
        this(new MessageSourceServer(), components);
    }
    
    public S2CPacketChatMessage(String playerName, List<ComponentText> components){
        this(new MessageSourcePlayer(playerName), components);
        this.playerName = playerName;
    }
    
    
    @Override
    public void write(ExtDataOutputStream stream) throws IOException{
        // Write source
        stream.writeByte(source.getSource().ordinal());
        if(source.getSource() == MessageSources.PLAYER)
            stream.writeUTF(playerName);
        
        // Write components
        stream.writeShort(components.size());
        for(ComponentText component: components)
            writeComponent(stream, component);
    }
    
    private void writeComponent(ExtDataOutputStream stream, ComponentText component) throws IOException{
        stream.writeByte(component.getStyle().getData());
        stream.writeColor(component.getColor());
        stream.writeUTF(component.getText());
    }
    
    @Override
    public void read(ExtDataInputStream stream) throws IOException{
        // Read source
        final MessageSources messageSource = MessageSources.values()[stream.readByte()];
        if(messageSource == MessageSources.PLAYER){
            playerName = stream.readUTF();
            source = new MessageSourcePlayer(playerName);
        }else
            source = new MessageSourceServer();
        
        // Read components
        components = new ArrayList<>();
        final short componentsNum = stream.readShort();
        
        for(int i = 0; i < componentsNum; i++)
            readComponent(stream);
    }
    
    private void readComponent(ExtDataInputStream stream) throws IOException{
        components.add(
            new ComponentText(
                new TextStyle(stream.readByte()),
                stream.readColor(),
                stream.readUTF()
            )
        );
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.chatMessage(this);
    }
    
}