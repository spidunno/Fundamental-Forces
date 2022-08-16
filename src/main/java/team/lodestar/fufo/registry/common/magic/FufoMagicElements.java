package team.lodestar.fufo.registry.common.magic;

import net.minecraft.resources.ResourceLocation;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.core.element.MagicBinding;
import team.lodestar.fufo.core.element.MagicElement;
import team.lodestar.fufo.registry.common.weave.FufoBindingTypes;

import java.util.HashMap;

public class FufoMagicElements {
    public static final HashMap<ResourceLocation, MagicElement> ELEMENTS = new HashMap<>(); //TODO: create inner classes for all of these, containing registry entries for each registry
    public static final MagicElement FORCE = registerElement(new MagicElement(FufoMod.fufoPath("force")));
    public static final MagicElement FIRE = registerElement(new MagicElement(FufoMod.fufoPath("fire")));
    public static final MagicElement WATER = registerElement(new MagicElement(FufoMod.fufoPath("water")));
    public static final MagicElement EARTH = registerElement(new MagicElement(FufoMod.fufoPath("earth")));
    public static final MagicElement AIR = registerElement(new MagicElement(FufoMod.fufoPath("air")));

    protected static MagicElement registerElement(MagicElement element) {
        ELEMENTS.put(element.id, element);
        FufoBindingTypes.registerBinding(new MagicBinding(element));
        return element;
    }
}
