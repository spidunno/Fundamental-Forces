package com.sammy.fufo.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.sammy.fufo.common.worldevents.starfall.StarfallActor;
import com.sammy.fufo.core.data.SpaceModLang;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
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
                        boolean heightmap = StarfallActor.heightmapCheck(level, player.blockPosition(), 1);
                        boolean blocks = StarfallActor.blockCheck(level, StarfallActor.nearbyBlockList(level, player.blockPosition()));

                        if (heightmap && blocks) {
                            source.sendSuccess(new TranslatableComponent(SpaceModLang.getCommand("checkarea.report.success")), true);
                        } else {
                            source.sendSuccess(new TranslatableComponent(SpaceModLang.getCommand("checkarea.report.failure")), true);

                        }
                        source.sendSuccess(new TextComponent("--------------------------------------------"), true);
                        if (heightmap) {
                            source.sendSuccess(new TranslatableComponent(SpaceModLang.getCommand("checkarea.heightmap.success")), true);
                        } else {
                            source.sendSuccess(new TranslatableComponent(SpaceModLang.getCommand("checkarea.heightmap.failure")), true);
                        }

                        source.sendSuccess(new TextComponent("--------------------------------------------"), true);
                        if (blocks) {
                            source.sendSuccess(new TranslatableComponent(SpaceModLang.getCommand("checkarea.blocktag.success")), true);
                        } else {
                            source.sendSuccess(new TranslatableComponent(SpaceModLang.getCommand("checkarea.blocktag.failure")), true);
                        }

                        source.sendSuccess(new TextComponent("--------------------------------------------"), true);
                    }
                    return 1;
                });
    }
}