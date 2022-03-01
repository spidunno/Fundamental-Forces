package com.sammy.fundamental_forces.core.setup.client;

import com.sammy.fundamental_forces.common.options.FireOffsetOption;
import com.sammy.fundamental_forces.common.options.ScreenshakeOption;
import com.sammy.fundamental_forces.core.systems.option.FundamentalOption;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.ArrayList;

@SuppressWarnings("ALL")
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OptionsRegistry {
    public static final ArrayList<FundamentalOption> OPTIONS = new ArrayList<>();

    @SubscribeEvent
    public static void registerOptions(FMLCommonSetupEvent event)
    {
        registerOption(new ScreenshakeOption());
        registerOption(new FireOffsetOption());
    }
    public static void registerOption(FundamentalOption option)
    {
        OPTIONS.add(option);
    }
}
