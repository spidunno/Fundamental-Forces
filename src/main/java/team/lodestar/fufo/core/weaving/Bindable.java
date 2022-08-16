package team.lodestar.fufo.core.weaving;

import net.minecraft.core.Vec3i;

/**
 * Represents a bindable component inside of a Weave
 */
public abstract class Bindable {

    /**
     * The location of this Bindable in a {@link Weave}
     */
    private Vec3i location;

    /**
     * The size of this Bindable in three dimensions.
     */
    private Vec3i size = new Vec3i(1, 1, 1);

    public Bindable(Vec3i size) {
        this.size = size;
    }

    /**
     * Sets the location of this Bindable in a {@link Weave}
     */
    public void setLocation(Vec3i location) {
        this.location = location;
    }

    /**
     * Gets the location of this Bindable in a {@link Weave}
     */
    public Vec3i getLocation() {
        return location;
    }

    /**
     * The size of this Bindable in three dimensions.
     * Assumed to be greater or equal to 1x1
     *
     * @return Size of this Bindable in three dimensions
     */
    public Vec3i size() {
        return size;
    }

    /**
     * Checks the equality of this Bindable to another
     * @param other The other Bindable to compare to
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Bindable bindable) {
            return bindable.getLocation().equals(location) && bindable.size().equals(size());
        }
        return false;
    }

}