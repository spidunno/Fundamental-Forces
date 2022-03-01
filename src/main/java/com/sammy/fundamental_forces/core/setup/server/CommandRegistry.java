package com.sammy.fundamental_forces.core.setup.server;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.sammy.fundamental_forces.common.command.*;
import com.sammy.fundamental_forces.common.command.argument.StarfallResultArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.sammy.fundamental_forces.FundamentalForcesMod.MODID;
import static com.sammy.fundamental_forces.core.helper.DataHelper.prefix;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandRegistry {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd = dispatcher.register(Commands.literal("es")
                .then(DevWorldSetupCommand.register())
                .then(IssueStarfallCommand.register())
                .then(ScreenshakeCommand.register())
                .then(SetSpellCommand.register())
                .then(StarfallAreaCheckCommand.register())
        );
        dispatcher.register(Commands.literal(MODID)
                .redirect(cmd));
    }

    public static void registerArgumentTypes() {
        registerArgumentType(prefix("starfall_result"), StarfallResultArgumentType.class, new EmptyArgumentSerializer<>(StarfallResultArgumentType::new));
    }

    private static <T extends ArgumentType<?>> void registerArgumentType(ResourceLocation key, Class<T> argumentClass, ArgumentSerializer<T> serializer) {
        ArgumentTypes.register(key.toString(), argumentClass, serializer);
    }
}