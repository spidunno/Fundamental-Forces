package com.project_esoterica.esoterica.core.registry;

import com.project_esoterica.esoterica.common.options.FireOffsetOption;
import com.project_esoterica.esoterica.common.options.ScreenshakeOption;
import com.project_esoterica.esoterica.core.systems.option.IEsotericaOption;
import net.minecraft.client.Option;
import net.minecraft.client.gui.screens.AccessibilityOptionsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.ArrayList;

@SuppressWarnings("ALL")
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OptionsRegistry {
    public static final ArrayList<IEsotericaOption> OPTIONS = new ArrayList<>();

    @SubscribeEvent
    public static void registerOptions(FMLCommonSetupEvent event)
    {
        registerOption(new ScreenshakeOption());
        registerOption(new FireOffsetOption());
    }
    public static void registerOption(IEsotericaOption option)
    {
        OPTIONS.add(option);
    }
}
