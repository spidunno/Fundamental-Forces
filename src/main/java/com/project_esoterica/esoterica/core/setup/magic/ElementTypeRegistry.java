package com.project_esoterica.esoterica.core.setup.magic;

import com.project_esoterica.esoterica.core.systems.magic.element.MagicElement;

import java.util.HashMap;

public class ElementTypeRegistry {

    public static final HashMap<String, MagicElement> ELEMENTS = new HashMap<>();

    public static final MagicElement FORCE = registerElement(new MagicElement("force"));
    public static final MagicElement FIRE = registerElement(new MagicElement("fire"));
    public static final MagicElement WATER = registerElement(new MagicElement("water"));
    public static final MagicElement EARTH = registerElement(new MagicElement("earth"));
    public static final MagicElement AIR = registerElement(new MagicElement("air"));

    private static MagicElement registerElement(MagicElement element) {
        ELEMENTS.put(element.id, element);
        return element;
    }
}
