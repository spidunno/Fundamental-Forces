package com.sammy.fundamental_forces.common.command;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.sammy.fundamental_forces.common.packets.screenshake.PositionedScreenshakePacket;
import com.sammy.fundamental_forces.common.packets.screenshake.ScreenshakePacket;
import com.sammy.fundamental_forces.core.data.SpaceModLang;
import com.sammy.fundamental_forces.core.helper.DataHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

import static com.sammy.fundamental_forces.core.setup.server.PacketRegistry.INSTANCE;

public class ScreenshakeCommand {
    public ScreenshakeCommand() {
    }

    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("screenshake")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("intensity", FloatArgumentType.floatArg(0))
                        .then(Commands.argument("falloffTransformSpeed", FloatArgumentType.floatArg(0))
                                .then(Commands.argument("timeBeforeFastFalloff", IntegerArgumentType.integer(0))
                                        .then(Commands.argument("slowFalloff", FloatArgumentType.floatArg(0))
                                                .then(Commands.argument("fastFalloff", FloatArgumentType.floatArg(0))
                                                        .executes((context) -> {
                                                            CommandSourceStack source = context.getSource();
                                                            if (source.getEntity() instanceof ServerPlayer player) {
                                                                INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new ScreenshakePacket(
                                                                        FloatArgumentType.getFloat(context, "intensity"),
                                                                        FloatArgumentType.getFloat(context, "falloffTransformSpeed"),
                                                                        IntegerArgumentType.getInteger(context, "timeBeforeFastFalloff"),
                                                                        FloatArgumentType.getFloat(context, "slowFalloff"),
                                                                        FloatArgumentType.getFloat(context, "fastFalloff")));
                                                            }
                                                            source.sendSuccess(new TranslatableComponent(SpaceModLang.getCommand("screenshake")), true);
                                                            return 1;
                                                        }))))))
                .then(Commands.argument("position", BlockPosArgument.blockPos())
                        .then(Commands.argument("falloffDistance", FloatArgumentType.floatArg(0))
                                .then(Commands.argument("maxDistance", FloatArgumentType.floatArg(0))
                                        .then(Commands.argument("intensity", FloatArgumentType.floatArg(0))
                                                .then(Commands.argument("falloffTransformSpeed", FloatArgumentType.floatArg(0))
                                                        .then(Commands.argument("timeBeforeFastFalloff", IntegerArgumentType.integer(0))
                                                                .then(Commands.argument("slowFalloff", FloatArgumentType.floatArg(0))
                                                                        .then(Commands.argument("fastFalloff", FloatArgumentType.floatArg(0))
                                                                                .executes((context) -> {
                                                                                    CommandSourceStack source = context.getSource();
                                                                                    if (source.getEntity() instanceof ServerPlayer player) {
                                                                                        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PositionedScreenshakePacket(
                                                                                                DataHelper.fromBlockPos(BlockPosArgument.getLoadedBlockPos(context, "position")),
                                                                                                FloatArgumentType.getFloat(context, "falloffDistance"),
                                                                                                FloatArgumentType.getFloat(context, "maxDistance"),
                                                                                                FloatArgumentType.getFloat(context, "intensity"),
                                                                                                FloatArgumentType.getFloat(context, "falloffTransformSpeed"),
                                                                                                IntegerArgumentType.getInteger(context, "timeBeforeFastFalloff"),
                                                                                                FloatArgumentType.getFloat(context, "slowFalloff"),
                                                                                                FloatArgumentType.getFloat(context, "fastFalloff")));
                                                                                    }
                                                                                    source.sendSuccess(new TranslatableComponent(SpaceModLang.getCommand("screenshake")), true);
                                                                                    return 1;
                                                                                })))))))));
    }
}