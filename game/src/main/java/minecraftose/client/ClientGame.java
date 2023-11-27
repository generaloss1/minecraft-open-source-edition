package minecraftose.client;

import jpize.Jpize;
import jpize.al.listener.AlListener;
import jpize.math.vecmath.vector.Vec3f;
import minecraftose.client.chat.Chat;
import minecraftose.client.command.ClientCommandDispatcher;
import minecraftose.client.control.BlockRayCast;
import minecraftose.client.control.camera.GameCamera;
import minecraftose.client.entity.LocalPlayer;
import minecraftose.client.level.ClientLevel;
import minecraftose.client.network.ClientConnection;
import minecraftose.client.renderer.particle.Particle;
import minecraftose.client.renderer.particle.ParticleBatch;
import minecraftose.client.time.ClientGameTime;
import minecraftose.main.Tickable;
import minecraftose.main.network.packet.c2s.game.move.C2SPacketMoveAndRot;
import minecraftose.main.network.packet.c2s.game.move.C2SPacketRot;
import minecraftose.main.network.packet.c2s.login.C2SPacketLogin;
import minecraftose.main.network.packet.c2s.game.move.C2SPacketMove;
import minecraftose.main.time.GameTime;

public class ClientGame implements Tickable{
    
    private final Minecraft session;
    private final ClientConnection connection;
    private final ClientCommandDispatcher commandDispatcher;
    private final Chat chat;

    private final BlockRayCast blockRayCast;
    private final ClientGameTime time;
    private final LocalPlayer player;
    private final GameCamera camera;


    private ClientLevel level;
    
    public ClientGame(Minecraft session){
        this.session = session;

        this.connection = new ClientConnection(this);
        
        this.blockRayCast = new BlockRayCast(session, 16);
        this.chat = new Chat(this);
        this.time = new ClientGameTime();

        this.player = new LocalPlayer(this, null, session.getProfile().getUsername());
        this.commandDispatcher = new ClientCommandDispatcher(this);

        this.camera = new GameCamera(this, 0.1, 5000, session.getOptions().getFieldOfView());
        this.camera.setDistance(session.getOptions().getRenderDistance());
    }
    
    public Minecraft getSession(){
        return session;
    }

    
    public void connect(String address, int port){
        System.out.println("[Client]: Connect to " + address + ":" + port);
        connection.connect(address, port);
        connection.sendPacket( new C2SPacketLogin(session.getVersion().getID(), session.getProfile().getUsername()) );
    }

    @Override
    public void tick(){
        if(level == null)
            return;

        // Send player position & rotation
        if(player.isPositionChanged() && player.isRotationChanged())
            connection.sendPacket(new C2SPacketMoveAndRot(player));
        else if(player.isPositionChanged())
            connection.sendPacket(new C2SPacketMove(player));
        else if(player.isRotationChanged())
            connection.sendPacket(new C2SPacketRot(player));

        time.tick();
        player.tick();
        level.tick();

        // Update audio listener
        if(player.isRotationChanged())
            AlListener.setOrientation(player.getRotation().getDirection().rotY(180));
        if(player.isPositionChanged()){
            AlListener.setPosition(player.getLerpPosition().copy().add(0, player.getEyeHeight(), 0));
            AlListener.setVelocity(player.getVelocity());
        }

        if(time.getTicks() % GameTime.TICKS_IN_SECOND == 0)
            connection.countTxRx();
    }
    
    public void update(){
        if(camera == null)
            return;

        session.getGame().getPlayer().getController().update();
        player.updateInterpolation();
        blockRayCast.update();
        camera.update();
    }
    
    public void createClientLevel(String worldName){
        if(level != null){
            Jpize.execSync(() ->{
                level.getConfiguration().setName(worldName);
                level.getChunkManager().reset();
            });
        }else{
            level = new ClientLevel(this, worldName);
            blockRayCast.setLevel(level);
            player.setLevel(level);
        }
    }
    
    public void disconnect(){
        connection.disconnect();
        
        if(level != null){
            Jpize.execSync(() -> {
                System.out.println(10000);
                level.getChunkManager().dispose();
            });
        }
    }
    
    public void spawnParticle(Particle particle, Vec3f position){
        final ParticleBatch particleBatch = session.getRenderer().getWorldRenderer().getParticleBatch();
        particleBatch.spawnParticle(particle, position);
    }
    
    public final ClientLevel getLevel(){
        return level;
    }
    
    public final LocalPlayer getPlayer(){
        return player;
    }
    
    public final GameCamera getCamera(){
        return camera;
    }
    
    public final BlockRayCast getBlockRayCast(){
        return blockRayCast;
    }
    
    public final Chat getChat(){
        return chat;
    }

    public final ClientCommandDispatcher getCommandDispatcher(){
        return commandDispatcher;
    }

    public final ClientGameTime getTime(){
        return time;
    }

    public final ClientConnection getConnection(){
        return connection;
    }

}
