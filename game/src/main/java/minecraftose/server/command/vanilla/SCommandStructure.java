package minecraftose.server.command.vanilla;

import jpize.util.file.Resource;
import jpize.math.vecmath.vector.Vec3f;
import jpize.math.vecmath.vector.Vec3i;
import jpize.util.file.ResourceExt;
import minecraftose.main.command.CommandContext;
import minecraftose.main.command.argument.CommandArg;
import minecraftose.main.command.builder.Commands;
import minecraftose.main.network.packet.s2c.game.S2CPacketBlockUpdate;
import minecraftose.main.registry.Registry;
import minecraftose.main.text.Component;
import minecraftose.main.text.TextColor;
import minecraftose.server.command.ServerCommandDispatcher;
import minecraftose.server.level.LevelS;
import minecraftose.server.player.PlayerList;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class SCommandStructure{

    public static void registerTo(ServerCommandDispatcher dispatcher){
        dispatcher.newCommand(Commands.literal("struct")
            .then(Commands.literal("save")
                .then(Commands.argument("begin", CommandArg.position())
                    .then(Commands.argument("end", CommandArg.position())
                        .then(Commands.argument("name", CommandArg.word())
                            .executes(SCommandStructure::save)
                        )
                    )
                )
            )
            .then(Commands.literal("load")
                .then(Commands.argument("name", CommandArg.word())
                    .executes(SCommandStructure::load)
                )
            )
            .then(Commands.literal("palette")
                .executes(SCommandStructure::palette)
            )
        );
    }

    private static void palette(CommandContext context){
        final LevelS level = context.getSource().getLevel();
        final PlayerList playerList = context.getSource().tryToGetServer().getPlayerList();

        final Vec3f position = context.getSource().getPosition();
        final Vec3i start = new Vec3i(position.xFloor(), position.yRound(), position.zFloor());

        short blockID = 1;
        mainCycle: for(int i = 0; i < 6; i++)
            for(int j = 0; j < 6; j++){
                level.setBlockState(i + start.x, start.y, j + start.z, blockID);
                playerList.broadcastPacket(new S2CPacketBlockUpdate(i + start.x, start.y, j + start.z, blockID));
                blockID++;
                if(blockID == Registry.block.collection().size() - 1)
                    break mainCycle;
            }
    }

    private static void save(CommandContext context){
        final LevelS level = context.getSource().getLevel();

        // Dimensions
        final Vec3i begin = context.getArg(0).asPosition().getPosition().floor().castInt();
        final Vec3i end = context.getArg(1).asPosition().getPosition().floor().castInt();
        final String name = context.getArg(2).asWord().getWord();

        final Vec3i start = begin.min(end);
        final Vec3i size = begin.copy().sub(end).abs().add(1);

        // File
        final ResourceExt file = Resource.external("structures/" + name + ".struct");
        try(final JpizeOutputStream outStream = file.jpizeOut()){

            // Write
            outStream.writeVec3i(size);

            for(int x = 0; x < size.x; x++)
                for(int y = 0; y < size.y; y++)
                    for(int z = 0; z < size.z; z++)
                        outStream.writeShort(level.getBlockState(x + start.x, y + start.y, z + start.z));

            outStream.flush();

        }catch(IOException e){
            throw new RuntimeException(e);
        }

        context.getSource().sendMessage(new Component().text("Structure saved as ").color(TextColor.YELLOW).text(name + ".struct"));
    }

    private static void load(CommandContext context){
        final LevelS level = context.getSource().getLevel();
        final PlayerList playerList = context.getSource().tryToGetServer().getPlayerList();

        final String name = context.getArg(0).asWord().getWord();

        final Vec3f position = context.getSource().getPosition();
        final Vec3i start = new Vec3i(position.xFloor(), position.yRound(), position.zFloor());

        // File
        final Resource file = Resource.external("structures/" + name + ".struct");
        try(final JpizeInputStream inStream = file.jpizeIn()){

            // Read
            final Vec3i size = inStream.readVec3i();

            for(int x = 0; x < size.x; x++)
                for(int y = 0; y < size.y; y++)
                    for(int z = 0; z < size.z; z++){
                        final short block = inStream.readShort();
                        if(level.setBlockState(x + start.x, y + start.y, z + start.z, block))
                            playerList.broadcastPacket(new S2CPacketBlockUpdate(x + start.x, y + start.y, z + start.z, block));
                    }

        }catch(IOException e){
            throw new RuntimeException(e);
        }

        context.getSource().sendMessage(new Component().text("Structure ").color(TextColor.YELLOW).text(name + ".struct").reset().text(" loaded"));
    }

}
