package team.lodestar.fufo.core.weaving.recipe;

import team.lodestar.fufo.core.weaving.Bindable;
import team.lodestar.fufo.core.weaving.Weave;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * A bindable element of a {@link Weave}, representing an Ingredient
 */
public class IngredientBindable extends Bindable {
    private final Ingredient ingredient;

    public IngredientBindable(Vec3i size, Ingredient ingredient) {
        super(size);
        this.ingredient = ingredient;
    }

    public IngredientBindable(Ingredient ingredient) {
        this(new Vec3i(1, 1, 1), ingredient);
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public String toString() {
        return "IngredientBindable{ingredient=" + ingredient + "}";
    }

    /**
     * Checks equality of two {@link IngredientBindable}s
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientBindable that = (IngredientBindable) o;
        return ingredient.equals(that.ingredient);
    }
}
