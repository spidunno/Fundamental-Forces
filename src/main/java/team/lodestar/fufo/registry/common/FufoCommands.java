package team.lodestar.fufo.registry.common;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.ArgumentUtils;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import team.lodestar.fufo.common.command.IssueStarfallCommand;
import team.lodestar.fufo.common.command.SetSpellCommand;
import team.lodestar.fufo.common.command.StarfallAreaCheckCommand;
import team.lodestar.fufo.common.command.ToggleManualTickingCommand;
import team.lodestar.fufo.common.command.argument.StarfallResultArgumentType;

import static team.lodestar.fufo.FufoMod.FUFO;
import static team.lodestar.fufo.FufoMod.fufoPath;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FufoCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd = dispatcher.register(Commands.literal("es")
                .then(IssueStarfallCommand.register())
                .then(SetSpellCommand.register())
                .then(StarfallAreaCheckCommand.register())
                .then(ToggleManualTickingCommand.register())
        );
        dispatcher.register(Commands.literal(FUFO)
                .redirect(cmd));
    }

    public static void registerArgumentTypes() {
        registerArgumentType(fufoPath("starfall_result"), StarfallResultArgumentType.class, new EmptyArgumentSerializer<>(StarfallResultArgumentType::new));
    }

    private static <T extends ArgumentType<?>> void registerArgumentType(ResourceLocation key, Class<T> argumentClass, ArgumentSerializer<T> serializer) {
        ArgumentTypes.register(key.toString(), argumentClass, serializer);
    }
}