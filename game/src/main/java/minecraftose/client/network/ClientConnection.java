package minecraftose.client.network;

import jpize.app.Jpize;
import jpize.util.net.packet.NetPacket;
import jpize.util.net.packet.NetPacketDispatcher;
import jpize.util.net.tcp.TCPClient;
import jpize.util.net.tcp.TCPConnection;
import jpize.util.security.AESKey;
import minecraftose.client.Minecraft;
import minecraftose.main.network.packet.s2c.game.*;
import minecraftose.main.network.packet.s2c.login.S2CPacketEncryptStart;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientConnection {

    private final Minecraft minecraft;

    private final TCPClient tcpClient;
    private TCPConnection tcpConnection;

    private final NetPacketDispatcher dispatcher;
    private final ClientPacketHandler handler;
    private final ConcurrentLinkedQueue<byte[]> receivedPacketsQueue;

    private int txCounter, rxCounter, tx, rx;

    public ClientConnection(Minecraft minecraft){
        this.minecraft = minecraft;
        this.handler = new ClientPacketHandler(this);
        this.dispatcher = new NetPacketDispatcher();
        this.receivedPacketsQueue = new ConcurrentLinkedQueue<>();
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

        this.tcpClient = new TCPClient()
                .setOnConnect(this::connected)
                .setOnDisconnect(this::disconnected)
                .setOnReceive(this::received);
    }
    
    public Minecraft getMinecraft(){
        return minecraft;
    }


    public void connect(String address, int port){
        tcpClient.connect(address, port);
        tcpConnection.options().setTcpNoDelay(true);
    }

    public void disconnect(){
        tcpClient.disconnect();
    }

    public void sendPacket(NetPacket<?> packet){
        tcpConnection.send(packet);
        txCounter++;
    }

    public void encode(AESKey encodeKey){
        tcpConnection.encode(encodeKey);
    }


    public void received(TCPConnection sender, byte[] bytes){
        receivedPacketsQueue.add(bytes);
    }
    
    public void connected(TCPConnection connection){
        this.tcpConnection = connection;
    }
    
    public void disconnected(TCPConnection connection){
        Jpize.exit();
    }


    public void handlePackets(){
        while(!receivedPacketsQueue.isEmpty()){
            final byte[] bytes = receivedPacketsQueue.poll();
            dispatcher.readPacket(bytes, handler);
            rxCounter += dispatcher.handlePackets();
        }
    }


    public void countTxRx(){
        tx = txCounter;
        txCounter = 0;
        rx = rxCounter;
        rxCounter = 0;
    }

    public int getTX(){
        return tx;
    }

    public int getRX(){
        return rx;
    }


    private void registerPackets(){
        dispatcher.register(
            S2CPacketAbilities.class,
            S2CPacketBlockUpdate.class,
            S2CPacketChatMessage.class,
            S2CPacketChunk.class,
            S2CPacketDisconnect.class,
            S2CPacketEncryptStart.class,
            S2CPacketEntityMove.class,
            S2CPacketLightUpdate.class,
            S2CPacketPlayerSneaking.class,
            S2CPacketPlaySound.class,
            S2CPacketPong.class,
            S2CPacketRemoveEntity.class,
            S2CPacketSpawnEntity.class,
            S2CPacketSpawnInfo.class,
            S2CPacketSpawnPlayer.class,
            S2CPacketTeleportPlayer.class,
            S2CPacketTime.class,
            S2CPacketPlayerVelocity.class
        );
    }
    
}
