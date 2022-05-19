package com.sammy.fufo.core.setup.content.magic;

import com.sammy.fufo.core.systems.magic.element.MagicBinding;
import com.sammy.fufo.core.systems.magic.element.MagicElement;

import java.util.HashMap;

public class ElementTypeRegistry {

    public static final HashMap<String, MagicElement> ELEMENTS = new HashMap<>();
    public static final HashMap<String, MagicBinding> BINDINGS = new HashMap<>();

    public static final MagicElement FORCE = registerElement(new MagicElement("force"));
    public static final MagicElement FIRE = registerElement(new MagicElement("fire"));
    public static final MagicElement WATER = registerElement(new MagicElement("water"));
    public static final MagicElement EARTH = registerElement(new MagicElement("earth"));
    public static final MagicElement AIR = registerElement(new MagicElement("air"));

    protected static MagicElement registerElement(MagicElement element) {
        ELEMENTS.put(element.id, element);
        registerBinding(new MagicBinding(element));
        return element;
    }

    protected static MagicBinding registerBinding(MagicBinding binding) {
        BINDINGS.put(binding.id, binding);
        return binding;
    }
}