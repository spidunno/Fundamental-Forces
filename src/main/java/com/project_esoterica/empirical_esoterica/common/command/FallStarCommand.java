package com.project_esoterica.empirical_esoterica.common.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.project_esoterica.empirical_esoterica.common.worldevent.WorldEventManager;
import com.project_esoterica.empirical_esoterica.common.worldevent.starfall.StarfallInstance;
import com.project_esoterica.empirical_esoterica.common.worldevent.starfall.StarfallResult;
import com.project_esoterica.empirical_esoterica.core.registry.worldevent.StarfallResults;
import com.project_esoterica.empirical_esoterica.core.systems.command.StarfallResultArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class FallStarCommand {
    public FallStarCommand() {
    }

    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("fallstar")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("countdown", IntegerArgumentType.integer(0))
                        .then(Commands.argument("result", new StarfallResultArgumentType())
                                .then(Commands.argument("position", BlockPosArgument.blockPos())
                                        .executes(context -> {
                                            CommandSourceStack source = context.getSource();
                                            StarfallResult result = StarfallResults.STARFALL_RESULTS.get(context.getArgument("result", String.class));
                                            ServerLevel level = source.getLevel();
                                            BlockPos pos = BlockPosArgument.getSpawnablePos(context,"position");
                                            WorldEventManager.addWorldEvent(level, new StarfallInstance(result, level, pos), false);
                                            return 1;
                                        }))
                                .then(Commands.argument("target", EntityArgument.player())
                                        .executes(context -> {
                                            CommandSourceStack source = context.getSource();
                                            StarfallResult result = StarfallResults.STARFALL_RESULTS.get(context.getArgument("result", String.class));
                                            ServerLevel level = source.getLevel();
                                            Player target = EntityArgument.getPlayer(context, "target");
                                            WorldEventManager.addWorldEvent(level, new StarfallInstance(result, level, target), false);
                                            return 1;
                                        }))));
    }
}