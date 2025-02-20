package minecraftose.server.network;

import jpize.util.net.packet.NetPacketDispatcher;
import jpize.util.net.tcp.TCPConnection;
import jpize.util.security.RSAKey;
import minecraftose.main.network.packet.PacketHandler;
import minecraftose.main.network.packet.c2s.game.*;
import minecraftose.main.network.packet.c2s.game.move.C2SPacketMove;
import minecraftose.main.network.packet.c2s.game.move.C2SPacketMoveAndRot;
import minecraftose.main.network.packet.c2s.game.move.C2SPacketRot;
import minecraftose.main.network.packet.c2s.login.C2SPacketAuth;
import minecraftose.main.network.packet.c2s.login.C2SPacketEncryptEnd;
import minecraftose.main.network.packet.c2s.login.C2SPacketLogin;
import minecraftose.main.text.Component;
import minecraftose.main.text.TextColor;
import minecraftose.server.Server;
import minecraftose.server.player.ServerPlayer;

import java.util.HashMap;
import java.util.Map;

public class ServerConnectionManager {
    
    private final Server server;
    private final Map<TCPConnection, PacketHandler> handlerMap;
    private final NetPacketDispatcher dispatcher;
    private final RSAKey rsaKey;
    
    public ServerConnectionManager(Server server){
        this.server = server;
        this.handlerMap = new HashMap<>();
        this.dispatcher = new NetPacketDispatcher();
        this.rsaKey = new RSAKey(1024);
        registerPackets();
    }
    
    public Server getServer(){
        return server;
    }
    
    public RSAKey getRsaKey(){
        return rsaKey;
    }


    private void registerPackets(){
        dispatcher.register(
            C2SPacketAuth.class,
            C2SPacketChatMessage.class,
            C2SPacketChunkRequest.class,
            C2SPacketEncryptEnd.class,
            C2SPacketLogin.class,
            C2SPacketMoveAndRot.class,
            C2SPacketMove.class,
            C2SPacketRot.class,
            C2SPacketPing.class,
            C2SPacketPlayerBlockSet.class,
            C2SPacketPlayerSneaking.class,
            C2SPacketRenderDistance.class,
            C2SPacketHitEntity.class
        );
    }
    
    
    public synchronized void received(TCPConnection sender, byte[] bytes){
        final PacketHandler packetHandler = handlerMap.get(sender);
        if(packetHandler == null)
            return;

        dispatcher.readPacket(bytes, packetHandler);
        dispatcher.handlePackets();
    }
    
    public void connected(TCPConnection connection){
        setHandlerForConnection(connection, new PlayerLoginConnection(server, connection));
    }

    public void setHandlerForConnection(TCPConnection connection, PacketHandler handler){
        handlerMap.put(connection, handler);
    }
    
    public void disconnected(TCPConnection connection){
        final PacketHandler packetHandler = handlerMap.get(connection);

        if(packetHandler instanceof ServerPlayerGameConnection connectionAdapter){
            final ServerPlayer player = connectionAdapter.getPlayer();
            server.getPlayerList().disconnectPlayer(player);
            
            player.sendToChat(new Component().color(TextColor.YELLOW).text("Player " + player.getName() + " leave the game"));
        }
        
        handlerMap.remove(connection);
    }

}
