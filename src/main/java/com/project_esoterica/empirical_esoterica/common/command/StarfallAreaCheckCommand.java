package com.project_esoterica.empirical_esoterica.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.project_esoterica.empirical_esoterica.common.worldevent.starfall.StarfallResult;
import com.project_esoterica.empirical_esoterica.core.data.SpaceModLang;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

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
                        boolean heightmap = StarfallResult.heightmapCheck(level, player.blockPosition());
                        boolean blockTag = StarfallResult.blockTagCheck(level, player.blockPosition());
                        boolean blockEntity = StarfallResult.blockEntityCheck(level, player.blockPosition());

                        if (heightmap && blockTag && blockEntity) {
                            source.sendSuccess(SpaceModLang.getCommandKey("checkarea.report.success"), true);
                        } else {
                            source.sendSuccess(SpaceModLang.getCommandKey("checkarea.report.failure"), true);

                        }
                        source.sendSuccess(new TextComponent("--------------------------------------------"), true);
                        if (heightmap) {
                            source.sendSuccess(SpaceModLang.getCommandKey("checkarea.heightmap.success"), true);
                        } else {
                            source.sendSuccess(SpaceModLang.getCommandKey("checkarea.heightmap.failure"), true);
                        }

                        source.sendSuccess(new TextComponent("--------------------------------------------"), true);
                        if (blockTag) {
                            source.sendSuccess(SpaceModLang.getCommandKey("checkarea.blocktag.success"), true);
                        } else {
                            source.sendSuccess(SpaceModLang.getCommandKey("checkarea.blocktag.failure"), true);
                        }

                        source.sendSuccess(new TextComponent("--------------------------------------------"), true);
                        if (blockEntity) {
                            source.sendSuccess(SpaceModLang.getCommandKey("checkarea.blockentity.success"), true);
                        } else {
                            source.sendSuccess(SpaceModLang.getCommandKey("checkarea.blockentity.failure"), true);
                        }

                        source.sendSuccess(new TextComponent("--------------------------------------------"), true);
                    }
                    return 1;
                });
    }
}