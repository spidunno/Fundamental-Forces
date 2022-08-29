package team.lodestar.fufo.core.weaving.recipe;

import team.lodestar.fufo.core.weaving.Bindable;
import team.lodestar.fufo.core.weaving.Weave;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.ItemStack;

/**
 * A bindable element of a {@link Weave}, representing an Ingredient
 */
public class ItemStackBindable extends Bindable {
    private final ItemStack itemStack;

    public ItemStackBindable(Vec3i size, ItemStack itemStack) {
        super(size);
        this.itemStack = itemStack;
    }

    public ItemStackBindable(ItemStack itemStack) {
        this(new Vec3i(1, 1, 1), itemStack);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public String toString() {
        return "ItemStackBindable{itemStack=" + itemStack + "}";
    }

    /**
     * Checks equality of two {@link ItemStackBindable}s
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemStackBindable that = (ItemStackBindable) o;
        return itemStack.equals(that.itemStack);
    }
}
