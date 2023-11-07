package minecraftose.server.net;

import jpize.net.tcp.TcpConnection;
import jpize.net.tcp.packet.IPacket;
import jpize.net.tcp.packet.PacketHandler;
import minecraftose.client.block.BlockProps;
import minecraftose.client.block.ClientBlocks;
import minecraftose.main.audio.SoundType;
import minecraftose.main.block.ChunkBlockData;
import minecraftose.main.block.BlockSetType;
import minecraftose.main.chunk.ChunkUtils;
import minecraftose.main.chunk.storage.ChunkPos;
import minecraftose.main.chunk.storage.HeightmapType;
import minecraftose.main.command.source.CommandSourcePlayer;
import minecraftose.main.network.packet.c2s.game.*;
import minecraftose.main.network.packet.s2c.game.S2CPacketBlockUpdate;
import minecraftose.main.network.packet.s2c.game.S2CPacketEntityMove;
import minecraftose.main.network.packet.s2c.game.S2CPacketPlayerSneaking;
import minecraftose.main.network.packet.s2c.game.S2CPacketPong;
import minecraftose.main.text.Component;
import minecraftose.main.text.TextColor;
import minecraftose.server.Server;
import minecraftose.server.chunk.ServerChunk;
import minecraftose.server.level.ServerLevel;
import minecraftose.server.player.ServerPlayer;

public class PlayerGameConnection implements PacketHandler{
    
    private final ServerPlayer player;
    private final Server server;
    private final TcpConnection connection;
    private final CommandSourcePlayer commandSource;
    
    public PlayerGameConnection(ServerPlayer player, TcpConnection connection){
        this.player = player;
        this.server = player.getServer();
        this.connection = connection;
        this.commandSource = new CommandSourcePlayer(player);
    }
    
    
    public ServerPlayer getPlayer(){
        return player;
    }

    public TcpConnection getConnection(){
        return connection;
    }
    
    public void sendPacket(IPacket<?> packet){
        connection.send(packet);
    }
    
    
    public void chunkRequest(C2SPacketChunkRequest packet){
        final ServerLevel level = player.getLevel();
        
        level.getChunkManager().requestedChunk(
            player,
            new ChunkPos(packet.chunkX, packet.chunkZ)
        );
    }
    
    public void playerBlockSet(C2SPacketPlayerBlockSet packet){
        final ServerLevel level = player.getLevel();
        final BlockProps oldBlock = level.getBlockProps(packet.x, packet.y, packet.z);
        final int oldHeightLight = level.getHeight(HeightmapType.LIGHT_SURFACE, packet.x, packet.z);

        // Set Block on the Server //
        final boolean result = level.setBlockState(packet.x, packet.y, packet.z, packet.blockData);
        if(!result)
            return;
        
        final BlockProps block = ChunkBlockData.getProps(packet.blockData);
        final int heightLight = level.getHeight(HeightmapType.LIGHT_SURFACE, packet.x, packet.z);
        final BlockSetType setType = BlockSetType.from(oldBlock.isEmpty(), block.isEmpty());

        // Send Set Block packet //
        server.getPlayerList().broadcastPacketExcept(new S2CPacketBlockUpdate(packet.x, packet.y, packet.z, packet.blockData), player);

        // Sound //
        final SoundType soundPack;
        if(setType.ordinal() > 0)
            soundPack = block.getSoundPack();
        else
            soundPack = oldBlock.getSoundPack();

        if(soundPack != null)
            level.playSound(soundPack.randomSound(setType), 1, 1, packet.x + 0.5F, packet.y + 0.5F, packet.z + 0.5F);

        // Process grass
        if(setType == BlockSetType.DESTROY && level.getBlockProps(packet.x, packet.y + 1, packet.z).getID() == ClientBlocks.GRASS.getID()){
            level.setBlock(packet.x, packet.y + 1, packet.z, ClientBlocks.AIR);
            player.sendPacket(new S2CPacketBlockUpdate(packet.x, packet.y + 1, packet.z, ClientBlocks.AIR.getDefaultData()));
            level.playSound(SoundType.GRASS.randomDestroySound(), 1, 1, packet.x + 0.5F, packet.y + 1.5F, packet.z + 0.5F);
        }

        // Process light
        final ServerChunk chunk = level.getBlockChunk(packet.x, packet.z);
        final int lx = ChunkUtils.getLocalCoord(packet.x);
        final int lz = ChunkUtils.getLocalCoord(packet.z);

        if(block.isLightTranslucent())
            level.getSkyLight().destroyBlockUpdate(chunk, heightLight, lx, packet.y, lz);
        else
            level.getSkyLight().placeBlockUpdate(chunk, oldHeightLight, lx, packet.y, lz);

        if(block.isGlow())
            level.getBlockLight().increase(chunk, lx, packet.y, lz, block.getLightLevel());
        else if(oldBlock.isGlow())
            level.getBlockLight().decrease(chunk, lx, packet.y, lz);

        level.getSkyLight().sendSections(chunk, packet.y);
    }
    
    public void move(C2SPacketMove packet){
        player.getPosition().set(packet.position);
        player.getRotation().set(packet.rotation);
        player.getVelocity().set(packet.velocity);
        
        server.getPlayerList().broadcastPacketLevelExcept(player.getLevel(), new S2CPacketEntityMove(player), player);
    }
    
    public void renderDistance(C2SPacketRenderDistance packet){
        player.setRenderDistance(packet.renderDistance);
    }
    
    public void sneaking(C2SPacketPlayerSneaking packet){
        player.setSneaking(packet.sneaking);
        
        server.getPlayerList().broadcastPacketExcept(new S2CPacketPlayerSneaking(player), player);
    }
    
    public void chatMessage(C2SPacketChatMessage packet){
        String message = packet.message;
        
        if(message.startsWith("/"))
            server.getCommandDispatcher().executeCommand(message.substring(1), commandSource);
        else
            player.sendToChat(new Component().color(TextColor.DARK_GREEN).text("<" + player.getName() + "> ").reset().text(packet.message));
    }

    public void ping(C2SPacketPing packet){
        sendPacket(new S2CPacketPong(packet.timeNanos));
    }
    
}
