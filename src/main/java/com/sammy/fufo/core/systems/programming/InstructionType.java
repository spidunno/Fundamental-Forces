package com.sammy.fufo.core.systems.programming;

import net.minecraft.resources.ResourceLocation;

/**
 * An instruction type for programming.
 * Grab retract pivot pivot twist ungrab extend pivot pivot wait
 */
public class InstructionType {

    /**
     * The instruction resource location.
     */
    public final ResourceLocation id;

    // TODO: fix with lang
    /**
     * The instruction name.
     */
    public final String name;

    /**
     * The instruction description.
     */
    public final String description;

    /**
     * The atlas resource location.
     */
    public final ResourceLocation atlas;

    /**
     * The index in the atlas
     */
    public final int index;

    public InstructionType(ResourceLocation id, String name, String description, ResourceLocation atlas, int index) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.atlas = atlas;
        this.index = index;
    }

}
