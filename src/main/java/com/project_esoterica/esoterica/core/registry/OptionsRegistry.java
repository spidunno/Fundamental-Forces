package com.project_esoterica.esoterica.core.registry;

import com.project_esoterica.esoterica.common.options.ScreenshakeOption;
import net.minecraft.client.Option;
import net.minecraft.client.gui.screens.AccessibilityOptionsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class OptionsRegistry {
    public static final ArrayList<Option> OPTIONS = new ArrayList<>();

    @SubscribeEvent
    public static void setupScreen(ScreenEvent.InitScreenEvent.Post event) {
        if (event.getScreen() instanceof AccessibilityOptionsScreen accessibilityOptionsScreen) {
            accessibilityOptionsScreen.list.addSmall(OPTIONS.toArray(Option[]::new));
        }
    }
}
