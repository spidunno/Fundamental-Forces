package team.lodestar.fufo.common.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import team.lodestar.fufo.common.command.argument.StarfallResultArgumentType;
import team.lodestar.fufo.common.starfall.ScheduledStarfallEvent;
import team.lodestar.fufo.common.starfall.StarfallData.StarfallDataBuilder;
import team.lodestar.fufo.common.starfall.actors.AbstractStarfallActor;
import team.lodestar.fufo.registry.common.worldevent.FufoStarfallActors;
import team.lodestar.fufo.unsorted.LangHelpers;
import team.lodestar.lodestone.handlers.WorldEventHandler;

public class IssueStarfallCommand {
    public IssueStarfallCommand() {
    }

    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("starfall")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("result", new StarfallResultArgumentType())
                        .then(Commands.argument("position", BlockPosArgument.blockPos())
                                .executes(context -> {
                                    CommandSourceStack source = context.getSource();
                                    AbstractStarfallActor result = FufoStarfallActors.ACTORS.get(context.getArgument("result", String.class));
                                    ServerLevel level = source.getLevel();
                                    BlockPos pos = BlockPosArgument.getSpawnablePos(context, "position");
                                    WorldEventHandler.addWorldEvent(level, new ScheduledStarfallEvent(new StarfallDataBuilder(result, null, a -> a.getStarfallCountdown(level.random)).setDetermined().setPrecise().build(), pos));
                                    source.sendSuccess(Component.translatable(LangHelpers.getCommand("starfall_natural_position")), true);
                                    return 1;
                                }))
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(context -> {
                                    CommandSourceStack source = context.getSource();
                                    AbstractStarfallActor result = FufoStarfallActors.ACTORS.get(context.getArgument("result", String.class));
                                    ServerLevel level = source.getLevel();
                                    Player target = EntityArgument.getPlayer(context, "target");
                                    WorldEventHandler.addWorldEvent(level, new ScheduledStarfallEvent(new StarfallDataBuilder(result, target.getUUID(), a -> a.getStarfallCountdown(level.random)).setDetermined().build()));
                                    source.sendSuccess(Component.translatable(LangHelpers.getCommand("starfall_natural_target")), true);
                                    return 1;
                                })))

                .then(Commands.argument("result", new StarfallResultArgumentType())
                        .then(Commands.argument("position", BlockPosArgument.blockPos())
                                .then(Commands.argument("countdown", IntegerArgumentType.integer(0))
                                        .executes(context -> {
                                            CommandSourceStack source = context.getSource();
                                            int countdown = IntegerArgumentType.getInteger(context, "countdown");
                                            AbstractStarfallActor result = FufoStarfallActors.ACTORS.get(context.getArgument("result", String.class));
                                            ServerLevel level = source.getLevel();
                                            BlockPos pos = BlockPosArgument.getSpawnablePos(context, "position");
                                            WorldEventHandler.addWorldEvent(level, new ScheduledStarfallEvent(new StarfallDataBuilder(result, null, a -> countdown).setDetermined().setPrecise().build(), pos));
                                            source.sendSuccess(Component.translatable(LangHelpers.getCommand("starfall_artificial_position")), true);
                                            return 1;
                                        })))
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(Commands.argument("countdown", IntegerArgumentType.integer(0))
                                        .executes(context -> {
                                            CommandSourceStack source = context.getSource();
                                            int countdown = IntegerArgumentType.getInteger(context, "countdown");
                                            AbstractStarfallActor result = FufoStarfallActors.ACTORS.get(context.getArgument("result", String.class));
                                            ServerLevel level = source.getLevel();
                                            Player target = EntityArgument.getPlayer(context, "target");
                                            WorldEventHandler.addWorldEvent(level, new ScheduledStarfallEvent(new StarfallDataBuilder(result, target.getUUID(), a -> countdown).setDetermined().build()));
                                            source.sendSuccess(Component.translatable(LangHelpers.getCommand("starfall_artificial_target")), true);
                                            return 1;
                                        }))));
    }
}