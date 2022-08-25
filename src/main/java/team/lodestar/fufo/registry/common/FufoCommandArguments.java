package team.lodestar.fufo.registry.common;

import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.Registry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import team.lodestar.fufo.common.command.argument.StarfallResultArgumentType;

import static team.lodestar.fufo.FufoMod.fufoPath;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class FufoCommandArguments {
    @SubscribeEvent
    public static void registerArgumentTypes(RegisterEvent event) {
        event.register(Registry.COMMAND_ARGUMENT_TYPE_REGISTRY, fufoPath("starfall_result"), () -> ArgumentTypeInfos.registerByClass(StarfallResultArgumentType.class, SingletonArgumentInfo.contextFree(StarfallResultArgumentType::new)));
    }
}
