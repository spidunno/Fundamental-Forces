package com.project_esoterica.empirical_esoterica.core.registry.misc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.project_esoterica.empirical_esoterica.common.command.DevWorldSetupCommand;
import com.project_esoterica.empirical_esoterica.common.command.FallStarCommand;
import com.project_esoterica.empirical_esoterica.common.command.ScreenshakeCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.project_esoterica.empirical_esoterica.EmpiricalEsoterica.MOD_ID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandsRegistry {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd = dispatcher.register(Commands.literal("es")
                .then(DevWorldSetupCommand.register())
                .then(FallStarCommand.register())
                .then(ScreenshakeCommand.register())
        );
        dispatcher.register(Commands.literal(MOD_ID)
                .redirect(cmd));
    }

}