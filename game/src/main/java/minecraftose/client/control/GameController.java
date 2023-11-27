package minecraftose.client.control;

import jpize.Jpize;
import jpize.gl.tesselation.GlFace;
import jpize.gl.Gl;
import jpize.gl.tesselation.GlPolygonMode;
import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec3f;
import jpize.math.vecmath.vector.Vec3i;
import jpize.physic.axisaligned.box.AABox;
import jpize.physic.axisaligned.box.AABoxBody;
import jpize.sdl.input.Btn;
import jpize.sdl.input.Key;
import minecraftose.client.Minecraft;
import minecraftose.client.block.ClientBlock;
import minecraftose.client.block.BlockProps;
import minecraftose.client.block.ClientBlocks;
import minecraftose.client.block.shape.BlockCollide;
import minecraftose.client.chat.Chat;
import minecraftose.client.control.camera.GameCamera;
import minecraftose.client.entity.LocalPlayer;
import minecraftose.client.level.ClientLevel;
import minecraftose.client.options.KeyMapping;
import minecraftose.client.options.Options;
import minecraftose.client.renderer.infopanel.ChunkInfoRenderer;
import minecraftose.client.renderer.infopanel.InfoRenderer;
import minecraftose.main.block.ChunkBlockData;
import minecraftose.main.entity.Entity;
import minecraftose.main.item.BlockItem;
import minecraftose.main.item.Item;
import minecraftose.main.item.ItemStack;
import minecraftose.main.item.Items;
import minecraftose.main.network.packet.c2s.game.C2SPacketChunkRequest;
import minecraftose.main.network.packet.c2s.game.C2SPacketPing;
import minecraftose.main.network.packet.c2s.game.C2SPacketPlayerBlockSet;

import java.util.Collection;

public class GameController{
    
    private final Minecraft session;
    private boolean f3Plus;
    
    public GameController(Minecraft session){
        this.session = session;
    }
    
    public Minecraft getSession(){
        return session;
    }
    
    
    public void update(){
        final Options options = session.getOptions();
        final GameCamera camera = session.getGame().getCamera();
        final BlockRayCast blockRayCast = session.getGame().getBlockRayCast();
        final ClientLevel level = session.getGame().getLevel();
        
        /* Window **/
        
        // Fullscreen
        if(Key.F11.isDown())
            Jpize.window().toggleFullscreenDesktop();
        
        /* Chat **/
        
        final Chat chat = session.getGame().getChat();
        
        if(chat.isOpened()){
            if(Key.ENTER.isDown()){
                chat.enter();
                chat.close();
            }
            
            if(Key.UP.isDown())
                chat.historyUp();
            if(Key.DOWN.isDown())
                chat.historyDown();
            
            if(Key.LCTRL.isPressed()){
                if(Key.C.isDown())
                    Jpize.setClipboardText(chat.getEnteringText());
                if(Key.V.isDown())
                    chat.getTextProcessor().insertLine(Jpize.getClipboardText());
            }
            
            if(Key.ESCAPE.isDown())
                chat.close();
            
            return; // Abort subsequent control
            
        }else if(options.getKey(KeyMapping.CHAT).isDown())
            chat.open();
        else if(options.getKey(KeyMapping.COMMAND).isDown()){
            chat.openAsCommandLine();
        }
        
        /* Game **/

        if(camera == null)
            return;

        // F3 + ...
        if(Key.F3.isPressed()){
            // G - Chunk border
            if(Key.G.isDown()){
                session.getRenderer().getWorldRenderer().getChunkBorderRenderer().toggleShow();
                f3Plus = true;
                return;
            }

            // R - Reload chunks
            if(Key.H.isDown()){
                session.getGame().getLevel().getChunkManager().reload();
                f3Plus = true;
                return;
            }

            // C - Reload chunks
            if(Key.C.isDown()){
                getSession().getGame().getConnection().sendPacket(new C2SPacketChunkRequest(camera.chunkX(), camera.chunkZ()));
                f3Plus = true;
                return;
            }

            // F - Toggle fog
            if(Key.F.isDown()){
                options.setFog(!options.isFogEnabled());
                f3Plus = true;
                return;
            }
        }

        // Info Panel
        if(Key.F3.isReleased()){
            if(!f3Plus){
                final InfoRenderer info = session.getRenderer().getInfoRenderer();
                final ChunkInfoRenderer chunkInfo = session.getRenderer().getChunkInfoRenderer();

                info.toggleOpen();

                if(info.isOpen()){
                    if(Key.LSHIFT.isPressed())
                        chunkInfo.setOpen(true);
                }else
                    chunkInfo.setOpen(false);
            }

            f3Plus = false;
        }

        // Place/Destroy/Copy block
        if(blockRayCast.isSelected()){
            final LocalPlayer player = session.getGame().getPlayer();
            
            if(Btn.LEFT.isDown() || Key.U.isPressed()){
                final Vec3i blockPos = blockRayCast.getSelectedBlockPosition();
                level.setBlock(blockPos.x, blockPos.y, blockPos.z, ClientBlocks.AIR);
                session.getGame().getConnection().sendPacket(new C2SPacketPlayerBlockSet(blockPos.x, blockPos.y, blockPos.z, ClientBlocks.AIR.getDefaultData()));
                
                for(int i = 0; i < 100; i++){
                    session.getGame().spawnParticle(session.BREAK_PARTICLE, new Vec3f(
                        blockPos.x + Maths.random(1F),
                        blockPos.y + Maths.random(1F),
                        blockPos.z + Maths.random(1F)
                    ));
                }
            }else if(Btn.RIGHT.isDown() || Key.J.isPressed()){
                final Item item = player.getInventory().getSelectedItemStack().getItem();
                if(item instanceof BlockItem blockItem)
                    placeBlock(ClientBlocks.COBBLESTONE); // blockItem.getBlock()
            }else if(Btn.MIDDLE.isDown()){
                final Vec3i blockPos = blockRayCast.getSelectedBlockPosition();

                final BlockProps blockProps = level.getBlockProps(blockPos.x, blockPos.y, blockPos.z);
                final Item item = Items.COBBLESTONE; // Registry.block_item.get(blockProps.getBlock())

                if(item != null){
                    final ItemStack stack = new ItemStack(item, item.maxStack());
                    player.getInventory().setSelectedItemStack(stack);
                }
            }
        }
        
        // Show mouse
        //if(Key.M.isDown())
        //    playerController.getRotationController().toggleShowMouse();
        
        // Camera zoom
        if(options.getKey(KeyMapping.ZOOM).isDown())
            camera.setZoom(10);
        else if(options.getKey(KeyMapping.ZOOM).isPressed()){
            camera.setZoom(camera.getZoom() + Jpize.input().getScroll() * (camera.getZoom() / 8));
        }else if(options.getKey(KeyMapping.ZOOM).isReleased())
            camera.setZoom(1);
        
        // Ping server
        if(Key.P.isDown())
            session.getGame().getConnection().sendPacket(new C2SPacketPing(System.nanoTime()));
        
        // Polygon Mode
        if(Key.F9.isDown())
            Gl.polygonMode(GlFace.FRONT_AND_BACK, GlPolygonMode.LINE);
        if(Key.F8.isDown())
            Gl.polygonMode(GlFace.FRONT_AND_BACK, GlPolygonMode.FILL);
        
        // Exit
        if(Key.ESCAPE.isDown())
            Jpize.exit();
    }
    
    private void placeBlock(ClientBlock block){
        final BlockRayCast blockRayCast = session.getGame().getBlockRayCast();
        final ClientLevel level = session.getGame().getLevel();
        final Vec3i blockPos = blockRayCast.getImaginaryBlockPosition();
        final LocalPlayer player = session.getGame().getPlayer();

        final int blockStates = block.getStates().size();

        byte blockState = 0;

        if(block == ClientBlocks.OAK_LOG || block == ClientBlocks.BIRCH_LOG || block == ClientBlocks.SPRUCE_LOG){
            // F:0  -Y:2  +Y:1
            int pitch = (int) ((player.getRotation().pitch % 360 / 90 % 4 + 4.5F) % 4);
            if(pitch == 3)
                pitch--;

            // -X:2  +X:0  -Z:3  +Z:1
            if(pitch == 0){
                int yaw = (int) ((player.getRotation().yaw % 360 / 90 % 4 + 4.5F) % 4);
                if(yaw == 2 || yaw == 0)
                    blockState = 2;

                if(yaw == 3 || yaw == 1)
                    blockState = 1;
            }
        }

        else if(blockStates > 1)
            blockState = (byte) Maths.random(0, blockStates - 1);

        final short blockData = ChunkBlockData.getData(block, blockState);
        
        final BlockCollide collideShape = block.getState(blockState).getCollide();
        if(collideShape != null){
            
            final AABox[] blockBoxes = collideShape.getBoxes();
            final AABoxBody blockBox = new AABoxBody(new Vec3f(blockPos));
            final Collection<Entity> entities = session.getGame().getLevel().getEntities();
            
            // Check intersect with player & entity
            for(AABox box: blockBoxes){
                blockBox.getBoundingBox().resize(box);
                
                if(player.getAABB().intersects(blockBox))
                    return;
                
                for(Entity entity: entities)
                    if(entity.getAABB().intersects(blockBox))
                        return;
            }
        }
        
        level.setBlockState(blockPos.x, blockPos.y, blockPos.z, blockData);
        session.getGame().getConnection().sendPacket(new C2SPacketPlayerBlockSet(blockPos.x, blockPos.y, blockPos.z, blockData));
    }
    
}
