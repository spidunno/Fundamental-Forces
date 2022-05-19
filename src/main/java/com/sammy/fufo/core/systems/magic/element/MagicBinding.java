package com.sammy.fufo.core.systems.magic.element;

public class MagicBinding {
    public final String id;

    public MagicBinding(String id) {
        this.id = id;
    }

    public MagicBinding(MagicElement element) {
        this.id = element.id;
    }
}