package com.project_esoterica.esoterica.core.registry.misc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.project_esoterica.esoterica.common.command.DevWorldSetupCommand;
import com.project_esoterica.esoterica.common.command.FallStarCommand;
import com.project_esoterica.esoterica.common.command.ScreenshakeCommand;
import com.project_esoterica.esoterica.common.command.StarfallAreaCheckCommand;
import com.project_esoterica.esoterica.core.systems.command.StarfallResultArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.project_esoterica.esoterica.EsotericaHelper.prefix;
import static com.project_esoterica.esoterica.EsotericaMod.MOD_ID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandsRegistry {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd = dispatcher.register(Commands.literal("es")
                .then(DevWorldSetupCommand.register())
                .then(FallStarCommand.register())
                .then(ScreenshakeCommand.register())
                .then(StarfallAreaCheckCommand.register())
        );
        dispatcher.register(Commands.literal(MOD_ID)
                .redirect(cmd));
    }

    public static void registerCommandArgumentTypesSerializers() {
        register(prefix("starfall_result"), StarfallResultArgumentType.class, new EmptyArgumentSerializer<>(StarfallResultArgumentType::new));
    }

    private static <T extends ArgumentType<?>> void register(ResourceLocation key, Class<T> argumentClass, ArgumentSerializer<T> serializer) {
        ArgumentTypes.register(key.toString(), argumentClass, serializer);
    }
}