package com.project_esoterica.esoterica.common.command;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.project_esoterica.esoterica.common.packets.ScreenshakePacket;
import com.project_esoterica.esoterica.core.data.SpaceModLang;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

import static com.project_esoterica.esoterica.core.registry.misc.PacketRegistry.INSTANCE;

public class ScreenshakeCommand {
    public ScreenshakeCommand() {
    }

    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("screenshake")
                .requires(cs -> cs.hasPermission(0))
                .then(Commands.argument("factor", FloatArgumentType.floatArg(0))
                        .then(Commands.argument("falloff", FloatArgumentType.floatArg(0, 1))
                                .executes((context) -> {
                                    CommandSourceStack source = context.getSource();
                                    if (source.getEntity() instanceof ServerPlayer player) {
                                        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new ScreenshakePacket(FloatArgumentType.getFloat(context, "factor"), FloatArgumentType.getFloat(context, "falloff")));
                                    }
                                    source.sendSuccess(SpaceModLang.getCommandKey("screenshake"), true);
                                    return 1;
                                })));
    }
}