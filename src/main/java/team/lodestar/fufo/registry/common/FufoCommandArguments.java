package team.lodestar.fufo.registry.common;

import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.Registry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.command.argument.SpellTypeArgumentType;
import team.lodestar.fufo.common.command.argument.StarfallResultArgumentType;
import top.theillusivec4.curios.api.CuriosApi;

import static team.lodestar.fufo.FufoMod.fufoPath;

public class FufoCommandArguments {

    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPES =
            DeferredRegister.create(Registry.COMMAND_ARGUMENT_TYPE_REGISTRY, FufoMod.FUFO);

    public static final RegistryObject<ArgumentTypeInfo<?, ?>> STARFALL_RESULT = ARGUMENT_TYPES.register("starfall_result", () -> ArgumentTypeInfos.registerByClass(StarfallResultArgumentType.class, SingletonArgumentInfo.contextFree(StarfallResultArgumentType::new)));

    public static final RegistryObject<ArgumentTypeInfo<?, ?>> SPELL_TYPE = ARGUMENT_TYPES.register("spell_type", () -> ArgumentTypeInfos.registerByClass(SpellTypeArgumentType.class, SingletonArgumentInfo.contextFree(SpellTypeArgumentType::new)));
}
