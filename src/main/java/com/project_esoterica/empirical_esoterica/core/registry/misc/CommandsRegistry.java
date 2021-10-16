package com.project_esoterica.empirical_esoterica.core.registry.misc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.project_esoterica.empirical_esoterica.core.data.SpaceModLang;
import com.project_esoterica.empirical_esoterica.core.systems.command.StarfallResultArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandsRegistry {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("esoterica")
                .then(Commands.literal("devsetup")
                        .requires(cs -> cs.hasPermission(2))
                        .executes((context) -> {
                            CommandSourceStack source = context.getSource();
                            MinecraftServer server = source.getServer();
                            GameRules rules = server.getGameRules();
                            rules.getRule(GameRules.RULE_KEEPINVENTORY).set(true, server);
                            rules.getRule(GameRules.RULE_DOMOBSPAWNING).set(false, server);
                            rules.getRule(GameRules.RULE_DAYLIGHT).set(false, server);
                            rules.getRule(GameRules.RULE_WEATHER_CYCLE).set(false, server);
                            rules.getRule(GameRules.RULE_MOBGRIEFING).set(false, server);
                            source.getLevel().setDayTime(2_000);
                            source.sendSuccess(SpaceModLang.getCommandKey("devsetup"), true);
                            return 1;
                        }))
                .then(Commands.literal("fallstar")
                        .requires(cs -> cs.hasPermission(2))
                        .then(Commands.argument("result", new StarfallResultArgumentType()))
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            return 1;
                        }))
        );
    }

}