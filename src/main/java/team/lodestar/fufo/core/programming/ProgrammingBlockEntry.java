package team.lodestar.fufo.core.programming;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

/**
 * Type of a programming block, containing common.
 */
public class ProgrammingBlockEntry<T extends ProgrammingBlock> {

    private final Function<ProgrammingBlockEntry<T>, T> supplier;
    private final ResourceLocation texture;

    public ProgrammingBlockEntry(Function<ProgrammingBlockEntry<T>, T> supplier, ResourceLocation texture) {
        this.supplier = supplier;
        this.texture = texture;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    /**
     * Constructs a new programming block of this type
     */
    public T newBlock() {
        return supplier.apply(this);
    }

}
