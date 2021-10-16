package com.project_esoterica.empirical_esoterica.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.project_esoterica.empirical_esoterica.common.worldevent.WorldEventManager;
import com.project_esoterica.empirical_esoterica.common.worldevent.starfall.StarfallInstance;
import com.project_esoterica.empirical_esoterica.core.registry.worldevent.StarfallResults;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public class SummonStarfallCommand implements Command<CommandSourceStack> {

    private static final SummonStarfallCommand CMD = new SummonStarfallCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("summonstarfall")
                .then(Commands.argument("target", EntityArgument.player()).requires(cs -> cs.hasPermission(2)).executes(context -> {
                    Player player = (Player) context.getArgument("player", EntitySelector.class).findSingleEntity(context.getSource());
                    if (player.level instanceof ServerLevel serverLevel) {
                        WorldEventManager.addWorldEvent(serverLevel, new StarfallInstance(StarfallResults.SPACE_DEBRIS, serverLevel, player), false);
                    }
                    return SINGLE_SUCCESS;
                }));
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return SINGLE_SUCCESS;
    }
}