package com.project_esoterica.empirical_esoterica.common.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.project_esoterica.empirical_esoterica.common.worldevent.starfall.StarfallResult;
import com.project_esoterica.empirical_esoterica.core.data.SpaceModLang;
import com.project_esoterica.empirical_esoterica.core.registry.worldevent.StarfallResults;
import com.project_esoterica.empirical_esoterica.core.systems.command.StarfallResultArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.Vec3;

public class FallStarCommand {
    public FallStarCommand() {
    }

    public static LiteralArgumentBuilder<CommandSourceStack> register() {
        return Commands.literal("fallstar")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("countdown", IntegerArgumentType.integer(0))
                        .then(Commands.argument("result", new StarfallResultArgumentType())
                                .then(Commands.argument("position", Vec3Argument.vec3())
                                        .executes(context -> {
                                            return 1;
                                        }))
                                .then(Commands.argument("target", EntityArgument.player())
                                        .executes(context -> {
                                            return 1;
                                        }))));
    }
}