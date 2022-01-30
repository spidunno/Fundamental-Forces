package com.project_esoterica.esoterica.core.setup.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.project_esoterica.esoterica.EsotericaMod;
import com.project_esoterica.esoterica.core.data.SpaceModLang;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public class KeyBindingRegistry {

    private static final String CATEGORY = "key.category."+EsotericaMod.MODID;

    public static KeyMapping swapHotbar;

    public static void registerKeyBinding(FMLClientSetupEvent event)
    {
        swapHotbar = registerKeyBinding(new KeyMapping(SpaceModLang.getKey("swapHotbar"), KeyConflictContext.IN_GAME, KeyModifier.NONE, getKey(GLFW.GLFW_KEY_Z), CATEGORY));
    }
    public static KeyMapping registerKeyBinding(KeyMapping key)
    {
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
    static InputConstants.Key getKey(int key) {
        return InputConstants.Type.KEYSYM.getOrCreate(key);
    }
}
