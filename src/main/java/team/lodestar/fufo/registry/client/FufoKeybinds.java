package team.lodestar.fufo.registry.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.unsorted.LangHelpers;

public class FufoKeybinds {

    private static final String CATEGORY = "key.category." + FufoMod.FUFO;

    public static KeyMapping swapHotbar;

    public static void registerKeyBinding(RegisterKeyMappingsEvent event) {
        swapHotbar = registerKeyBinding(event, new KeyMapping(LangHelpers.getKey("swapHotbar"), KeyConflictContext.IN_GAME, KeyModifier.NONE, getKey(GLFW.GLFW_KEY_Z), CATEGORY));
    }

    public static KeyMapping registerKeyBinding(RegisterKeyMappingsEvent event, KeyMapping key) {
        event.register(key);
        return key;
    }

    static InputConstants.Key getKey(int key) {
        return InputConstants.Type.KEYSYM.getOrCreate(key);
    }
}