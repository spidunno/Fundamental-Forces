package com.project_esoterica.esoterica.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.project_esoterica.esoterica.core.data.SpaceModLang;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventManager;
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
                        boolean heightmap = WorldEventManager.heightmapCheck(level, player.blockPosition(), 1);
                        boolean blocks = WorldEventManager.blockCheck(level, WorldEventManager.nearbyBlockList(level, player.blockPosition()));

                        if (heightmap && blocks) {
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
                        if (blocks) {
                            source.sendSuccess(SpaceModLang.getCommandKey("checkarea.blocktag.success"), true);
                        } else {
                            source.sendSuccess(SpaceModLang.getCommandKey("checkarea.blocktag.failure"), true);
                        }

                        source.sendSuccess(new TextComponent("--------------------------------------------"), true);
                    }
                    return 1;
                });
    }
}