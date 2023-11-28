package minecraftose.server.network;

import jpize.net.security.KeyRSA;
import jpize.net.tcp.TcpConnection;
import jpize.net.tcp.TcpListener;
import jpize.net.tcp.packet.PacketDispatcher;
import jpize.net.tcp.packet.PacketHandler;
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

public class ServerConnectionManager implements TcpListener{
    
    private final Server server;
    private final Map<TcpConnection, PacketHandler> handlerMap;
    private final PacketDispatcher dispatcher;
    private final KeyRSA rsaKey;
    
    public ServerConnectionManager(Server server){
        this.server = server;
        this.handlerMap = new HashMap<>();
        this.dispatcher = new PacketDispatcher();
        this.rsaKey = new KeyRSA(1024);
        registerPackets();
    }
    
    public Server getServer(){
        return server;
    }
    
    public KeyRSA getRsaKey(){
        return rsaKey;
    }


    private void registerPackets(){
        dispatcher.register(C2SPacketAuth.PACKET_ID, C2SPacketAuth.class);
        dispatcher.register(C2SPacketChatMessage.PACKET_ID, C2SPacketChatMessage.class);
        dispatcher.register(C2SPacketChunkRequest.PACKET_ID, C2SPacketChunkRequest.class);
        dispatcher.register(C2SPacketEncryptEnd.PACKET_ID, C2SPacketEncryptEnd.class);
        dispatcher.register(C2SPacketLogin.PACKET_ID, C2SPacketLogin.class);
        dispatcher.register(C2SPacketMoveAndRot.PACKET_ID, C2SPacketMoveAndRot.class);
        dispatcher.register(C2SPacketMove.PACKET_ID, C2SPacketMove.class);
        dispatcher.register(C2SPacketRot.PACKET_ID, C2SPacketRot.class);
        dispatcher.register(C2SPacketPing.PACKET_ID, C2SPacketPing.class);
        dispatcher.register(C2SPacketPlayerBlockSet.PACKET_ID, C2SPacketPlayerBlockSet.class);
        dispatcher.register(C2SPacketPlayerSneaking.PACKET_ID, C2SPacketPlayerSneaking.class);
        dispatcher.register(C2SPacketRenderDistance.PACKET_ID, C2SPacketRenderDistance.class);
        dispatcher.register(C2SPacketHitEntity.PACKET_ID, C2SPacketHitEntity.class);
    }
    
    
    @Override
    public synchronized void received(byte[] bytes, TcpConnection sender){
        final PacketHandler packetHandler = handlerMap.get(sender);
        if(packetHandler == null)
            return;

        dispatcher.handlePacket(bytes, packetHandler);
    }
    
    @Override
    public void connected(TcpConnection connection){
        setHandlerForConnection(connection, new PlayerLoginConnection(server, connection));
    }

    public void setHandlerForConnection(TcpConnection connection, PacketHandler handler){
        handlerMap.put(connection, handler);
    }
    
    @Override
    public void disconnected(TcpConnection connection){
        final PacketHandler packetHandler = handlerMap.get(connection);

        if(packetHandler instanceof ServerPlayerGameConnection connectionAdapter){
            final ServerPlayer player = connectionAdapter.getPlayer();
            server.getPlayerList().disconnectPlayer(player);
            
            player.sendToChat(new Component().color(TextColor.YELLOW).text("Player " + player.getName() + " leave the game"));
        }
        
        handlerMap.remove(connection);
    }

}
