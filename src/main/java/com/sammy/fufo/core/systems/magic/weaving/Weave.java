package com.sammy.fufo.core.systems.magic.weaving;

import com.mojang.datafixers.util.Pair;
import com.sammy.fufo.common.recipe.WeaveRecipe;
import com.sammy.fufo.core.systems.magic.weaving.recipe.EntityTypeBindable;
import net.minecraft.core.Vec3i;

import java.util.Collection;
import java.util.HashMap;

/**
 * Represents a weave of {@link Bindable}s, as part of a {@link WeaveRecipe}.
 */
public abstract class Weave<T extends Weave> {

    /**
     * Map of relative positions to bindings.
     */
    private HashMap<Vec3i, Bindable> bindables = new HashMap<>();

    /**
     * Map of weave positions to a binding type.
     */
    private HashMap<Pair<Vec3i, Vec3i>, BindingType> binds = new HashMap<>();

    /**
     * Constructs a new weave, given a Bindable as a base.
     */
    public Weave(Bindable base) {
        base.setLocation(Vec3i.ZERO);
        bindables.put(Vec3i.ZERO, base);
    }

    /**
     * Obtains the given {@link Bindable} at a position globally
     * @return the found {@link Bindable} or {@code null} if none was found.
     */
    public Bindable get(Vec3i position) {
        return this.bindables.get(position);
    }

    /**
     * Obtains the {@link BindingType} connecting two given positions.
     * @return the found {@link BindingType} or {@code null} if none was found.
     */
    public BindingType get(Vec3i position1, Vec3i position2) {
        return this.binds.get(new Pair(position1, position2));
    }

    /**
     * Adds a {@link Bindable} to the weave.
     *
     * @param base the {@link Bindable} to add this to.
     * @param binding the {@link BindingType} to link this with.
     * @param relativePosition the relative position to the base to attach the bindable.
     * @param bindable the {@link Bindable} to add.
     */
    public T add(Bindable base, BindingType binding, Vec3i relativePosition, Bindable bindable) {
        final Vec3i position = bindable.getLocation().offset(relativePosition);
        bindable.setLocation(position);
        this.bindables.put(position, bindable);
        this.link(base, binding, bindable);
        return (T) this;
    }

    /**
     * Adds a Bindable at a global position.
     * @param position the global position to add the bindable at.
     * @param bindable the bindable to add.
     */
    public T add(Vec3i position, Bindable bindable) {
        bindable.setLocation(position);
        this.bindables.put(position, bindable);

        return (T) this;
    }

    /**
     * Links two bindables together with a binding.
     *
     * @param base the {@link Bindable} to add this to.
     * @param binding the {@link BindingType} to link the two given bindables with.
     * @param bindable the {@link Bindable} to add.
     */
    private T link(Bindable base, BindingType binding, Bindable bindable) {
        final Pair<Vec3i, Vec3i> positions = new Pair<>(base.getLocation(), bindable.getLocation());
        binds.put(positions, binding);
        return (T) this;
    }

    /**
     * Links two bindables at given positions with a binding.
     *
     * @param position1 the position of the first bindable.
     * @param position2 the position of the second bindable.
     * @param binding the {@link BindingType} to link the two given bindables with.
     */
    public T link(Vec3i position1, Vec3i position2, BindingType binding) {
        binds.put(new Pair(position1, position2), binding);
        return (T) this;
    }

    /**
     * Links to a new bindable
     * @param start the position to link from.
     * @param end the position of the bindable to link to.
     * @param bindingType the binding type to link with.
     * @param bindable the bindable to link to.
     * @return this
     */
    public T link(Vec3i start, Vec3i end, BindingType bindingType, Bindable bindable) {
        binds.put(new Pair(start, end), bindingType);
        add(end, bindable);
        return (T) this;
    }

    /**
     * Severs two bindables at given positions.
     *
     * @param position1 the position of the first bindable.
     * @param position2 the position of the second bindable.
     * @return the {@link BindingType} that was severed.
     */
    public BindingType sever(Vec3i position1, Vec3i position2) {
        final Pair<Vec3i, Vec3i> positions = new Pair<>(position1, position2);
        final BindingType binding = binds.remove(positions);
        return binding;
    }

    /**
     * Obtains the {@link Bindable}s in this weave.
     *
     * @return the {@link Bindable}s in this weave.
     */
    public Collection<Bindable> getBindables() {
        return bindables.values();
    }

    /**
     * Obtains the links in this weave.
     * @return the links in this weave.
     */
    public HashMap<Pair<Vec3i, Vec3i>, BindingType> getLinks() {
        return binds;
    }

    /**
     * Computes the amount of {@link Bindable}s in this weave.
     * @return the amount of {@link Bindable}s in this weave, or {@code 0} if none were found.
     */
    public int size() {
        return this.bindables.size();
    }

    /**
     * Computes the total dimensions of this weave.
     * @return the dimensions of this weave.
     */
    public Vec3i getDimensions() {
        Vec3i min = new Vec3i(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        Vec3i max = new Vec3i(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

        for (final Bindable bindable : this.bindables.values()) {
            final Vec3i dimensions = bindable.size();
            final Vec3i location = bindable.getLocation();

            // Min the min and location
            min = new Vec3i(Math.min(min.getX(), location.getX()), Math.min(min.getY(), location.getY()), Math.min(min.getZ(), location.getZ()));

            // Max the max and location + dimensions
            max = new Vec3i(Math.max(max.getX(), location.getX() + dimensions.getX()), Math.max(max.getY(), location.getY() + dimensions.getY()), Math.max(max.getZ(), location.getZ() + dimensions.getZ()));
        }

        return new Vec3i(max.getX() - min.getX(), max.getY() - min.getY(), max.getZ() - min.getZ());
    }

    /**
     * Checks the equality of two {@link Weave}s.
     *
     * @param obj the {@link Object} to check equality with.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Weave)) return false;

        final Weave other = (Weave) obj;

        return this.bindables.equals(other.bindables) && this.binds.equals(other.binds);
    }

}
