package com.project_esoterica.esoterica.common.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.project_esoterica.esoterica.common.worldevents.starfall.StarfallActor;
import com.project_esoterica.esoterica.common.worldevents.starfall.ScheduledStarfallEvent;
import com.project_esoterica.esoterica.core.data.SpaceModLang;
import com.project_esoterica.esoterica.core.registry.worldevent.StarfallActors;
import com.project_esoterica.esoterica.core.systems.command.StarfallResultArgumentType;
import com.project_esoterica.esoterica.core.systems.worldevent.WorldEventManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public class FallStarCommand {
    public FallStarCommand() {
    }

    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("fallstar")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("result", new StarfallResultArgumentType())
                        .then(Commands.argument("position", BlockPosArgument.blockPos())
                                .executes(context -> {
                                    CommandSourceStack source = context.getSource();
                                    StarfallActor result = StarfallActors.STARFALL_RESULTS.get(context.getArgument("result", String.class));
                                    ServerLevel level = source.getLevel();
                                    BlockPos pos = BlockPosArgument.getSpawnablePos(context, "position");
                                    WorldEventManager.addWorldEvent(level, new ScheduledStarfallEvent(result).randomizedStartingCountdown(level).targetPosition(pos).determined(), false);
                                    source.sendSuccess(SpaceModLang.getCommandKey("fallstar_natural_position"), true);
                                    return 1;
                                }))
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(context -> {
                                    CommandSourceStack source = context.getSource();
                                    StarfallActor result = StarfallActors.STARFALL_RESULTS.get(context.getArgument("result", String.class));
                                    ServerLevel level = source.getLevel();
                                    Player target = EntityArgument.getPlayer(context, "target");
                                    WorldEventManager.addWorldEvent(level, new ScheduledStarfallEvent(result).randomizedStartingCountdown(level).targetEntity(target).determined(), false);
                                    source.sendSuccess(SpaceModLang.getCommandKey("fallstar_natural_target"), true);
                                    return 1;
                                })))

                .then(Commands.argument("result", new StarfallResultArgumentType())
                        .then(Commands.argument("position", BlockPosArgument.blockPos())
                                .then(Commands.argument("countdown", IntegerArgumentType.integer(0))
                                        .executes(context -> {
                                            CommandSourceStack source = context.getSource();
                                            int countdown = IntegerArgumentType.getInteger(context, "countdown");
                                            StarfallActor result = StarfallActors.STARFALL_RESULTS.get(context.getArgument("result", String.class));
                                            ServerLevel level = source.getLevel();
                                            BlockPos pos = BlockPosArgument.getSpawnablePos(context, "position");
                                            WorldEventManager.addWorldEvent(level, new ScheduledStarfallEvent(result).exactStartingCountdown(countdown).targetExactPosition(pos), false);
                                            source.sendSuccess(SpaceModLang.getCommandKey("fallstar_artificial_position"), true);
                                            return 1;
                                        })))
                        .then(Commands.argument("target", EntityArgument.player())

                                .then(Commands.argument("countdown", IntegerArgumentType.integer(0))
                                        .executes(context -> {
                                            CommandSourceStack source = context.getSource();
                                            int countdown = IntegerArgumentType.getInteger(context, "countdown");
                                            StarfallActor result = StarfallActors.STARFALL_RESULTS.get(context.getArgument("result", String.class));
                                            ServerLevel level = source.getLevel();
                                            Player target = EntityArgument.getPlayer(context, "target");
                                            WorldEventManager.addWorldEvent(level, new ScheduledStarfallEvent(result).exactStartingCountdown(countdown).targetEntity(target), false);
                                            source.sendSuccess(SpaceModLang.getCommandKey("fallstar_artificial_target"), true);
                                            return 1;
                                        }))));
    }
}