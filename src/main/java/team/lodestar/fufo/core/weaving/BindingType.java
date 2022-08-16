package team.lodestar.fufo.core.weaving;

import team.lodestar.fufo.common.recipe.WeaveRecipe;
import net.minecraft.resources.ResourceLocation;

/**
 * A type of binding between {@link Weave}s in a {@link WeaveRecipe}
 */
public class BindingType {

    public ResourceLocation id;

    /**
     * Constructs a BindingType given an ID
     */
    public BindingType(ResourceLocation id) {
        this.id = id;
    }

    /**
     * Checks the equality of this BindingType with another
     * @param other The other BindingType to compare to
     */
    public boolean equals(BindingType other) {
        return id.equals(other.id);
    }

}
