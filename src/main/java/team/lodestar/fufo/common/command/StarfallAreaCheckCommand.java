package team.lodestar.fufo.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import team.lodestar.fufo.common.worldevents.starfall.StarfallActor;
import team.lodestar.fufo.unsorted.LangHelpers;

public class StarfallAreaCheckCommand {
    public StarfallAreaCheckCommand() {
    }

    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("checkarea")
                .requires(cs -> cs.hasPermission(2))
                .executes((context) -> {
                    CommandSourceStack source = context.getSource();
                    if (source.getEntity() instanceof ServerPlayer player) {
                        ServerLevel level = context.getSource().getLevel();
                        boolean heightmap = StarfallActor.chunkChangesCheck(level, player.blockPosition(), 1);
                        boolean blocks = StarfallActor.blockCheck(level, StarfallActor.nearbyBlockList(level, player.blockPosition()));

                        if (heightmap && blocks) {
                            source.sendSuccess(Component.translatable(LangHelpers.getCommand("checkarea.report.success")), true);
                        } else {
                            source.sendSuccess(Component.translatable(LangHelpers.getCommand("checkarea.report.failure")), true);

                        }
                        source.sendSuccess(Component.literal("--------------------------------------------"), true);
                        if (heightmap) {
                            source.sendSuccess(Component.translatable(LangHelpers.getCommand("checkarea.heightmap.success")), true);
                        } else {
                            source.sendSuccess(Component.translatable(LangHelpers.getCommand("checkarea.heightmap.failure")), true);
                        }

                        source.sendSuccess(Component.literal("--------------------------------------------"), true);
                        if (blocks) {
                            source.sendSuccess(Component.translatable(LangHelpers.getCommand("checkarea.blocktag.success")), true);
                        } else {
                            source.sendSuccess(Component.translatable(LangHelpers.getCommand("checkarea.blocktag.failure")), true);
                        }

                        source.sendSuccess(Component.literal("--------------------------------------------"), true);
                    }
                    return 1;
                });
    }
}