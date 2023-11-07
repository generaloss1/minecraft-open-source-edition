package minecraftose.client.net;

import jpize.Jpize;
import jpize.net.tcp.TcpConnection;
import jpize.net.tcp.TcpListener;
import jpize.net.tcp.packet.IPacket;
import jpize.net.tcp.packet.PacketDispatcher;
import minecraftose.client.ClientGame;
import minecraftose.main.network.packet.s2c.game.*;
import minecraftose.main.network.packet.s2c.login.S2CPacketEncryptStart;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientConnectionHandler implements TcpListener{

    private final ClientGame game;
    private TcpConnection connection;
    private final PacketDispatcher dispatcher;
    private final ConcurrentLinkedQueue<byte[]> receivedQueue;
    private final ClientPacketHandler handler;

    private int txCounter, rxCounter, tx, rx;

    public ClientConnectionHandler(ClientGame game){
        this.game = game;
        this.handler = new ClientPacketHandler(this);
        this.dispatcher = new PacketDispatcher();
        this.receivedQueue = new ConcurrentLinkedQueue<>();
        registerPackets();

        // Handle packets
        final Thread thread = new Thread(() -> {
            while(!Thread.interrupted()){
                Thread.yield();
                handlePackets();
            }
        });
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setDaemon(true);
        thread.start();
    }
    
    public ClientGame getGame(){
        return game;
    }


    public TcpConnection getConnection(){
        return connection;
    }

    public void sendPacket(IPacket<?> packet){
        connection.send(packet);
        txCounter++;
    }


    @Override
    public void received(byte[] bytes, TcpConnection sender){
        receivedQueue.add(bytes);
        rxCounter++;
    }
    
    @Override
    public void connected(TcpConnection connection){
        this.connection = connection;
    }
    
    @Override
    public void disconnected(TcpConnection connection){
        Jpize.exit();
    }


    public void handlePackets(){
        while(!receivedQueue.isEmpty()){
            final byte[] bytes = receivedQueue.poll();

            final boolean handled = dispatcher.handlePacket(bytes, handler);
            if(handled)
                rxCounter++;
        }
    }


    public void countTxRx(){
        tx = txCounter;
        txCounter = 0;
        tx = txCounter;
        rxCounter = 0;
    }

    public int getTX(){
        return tx;
    }

    public int getRX(){
        return rx;
    }


    private void registerPackets(){
        dispatcher.register(S2CPacketAbilities.PACKET_ID, S2CPacketAbilities.class);
        dispatcher.register(S2CPacketBlockUpdate.PACKET_ID, S2CPacketBlockUpdate.class);
        dispatcher.register(S2CPacketChatMessage.PACKET_ID, S2CPacketChatMessage.class);
        dispatcher.register(S2CPacketChunk.PACKET_ID, S2CPacketChunk.class);
        dispatcher.register(S2CPacketDisconnect.PACKET_ID, S2CPacketDisconnect.class);
        dispatcher.register(S2CPacketEncryptStart.PACKET_ID, S2CPacketEncryptStart.class);
        dispatcher.register(S2CPacketEntityMove.PACKET_ID, S2CPacketEntityMove.class);
        dispatcher.register(S2CPacketLightUpdate.PACKET_ID, S2CPacketLightUpdate.class);
        dispatcher.register(S2CPacketPlayerSneaking.PACKET_ID, S2CPacketPlayerSneaking.class);
        dispatcher.register(S2CPacketPlaySound.PACKET_ID, S2CPacketPlaySound.class);
        dispatcher.register(S2CPacketPong.PACKET_ID, S2CPacketPong.class);
        dispatcher.register(S2CPacketRemoveEntity.PACKET_ID, S2CPacketRemoveEntity.class);
        dispatcher.register(S2CPacketSpawnEntity.PACKET_ID, S2CPacketSpawnEntity.class);
        dispatcher.register(S2CPacketSpawnInfo.PACKET_ID, S2CPacketSpawnInfo.class);
        dispatcher.register(S2CPacketSpawnPlayer.PACKET_ID, S2CPacketSpawnPlayer.class);
        dispatcher.register(S2CPacketTeleportPlayer.PACKET_ID, S2CPacketTeleportPlayer.class);
        dispatcher.register(S2CPacketTime.PACKET_ID, S2CPacketTime.class);
    }
    
}
