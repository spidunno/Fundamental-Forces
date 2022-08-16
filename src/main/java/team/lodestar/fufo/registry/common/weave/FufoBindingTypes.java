package team.lodestar.fufo.registry.common.weave;

import net.minecraft.resources.ResourceLocation;
import team.lodestar.fufo.core.element.MagicBinding;

import java.util.HashMap;

public class FufoBindingTypes {
    public static final HashMap<ResourceLocation, MagicBinding> BINDING_TYPES = new HashMap<>();

    public static MagicBinding registerBinding(MagicBinding binding) {
        BINDING_TYPES.put(binding.id, binding);
        return binding;
    }
}
