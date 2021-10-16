package com.project_esoterica.empirical_esoterica.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;

public class DevWorldSetupCommand implements Command<CommandSourceStack> {

    private static final DevWorldSetupCommand CMD = new DevWorldSetupCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("devsetup").requires(cs -> cs.hasPermission(2)).executes(CMD);
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        MinecraftServer server = source.getServer();
        GameRules rules = server.getGameRules();
        rules.getRule(GameRules.RULE_KEEPINVENTORY).set(true, server);
        rules.getRule(GameRules.RULE_DOMOBSPAWNING).set(false, server);
        rules.getRule(GameRules.RULE_DAYLIGHT).set(false, server);
        rules.getRule(GameRules.RULE_WEATHER_CYCLE).set(false, server);
        rules.getRule(GameRules.RULE_MOBGRIEFING).set(false, server);
        source.getLevel().setDayTime(2_000);
        source.sendSuccess(new TextComponent("Command successful you fuckhead."), true);
        return SINGLE_SUCCESS;
    }
}