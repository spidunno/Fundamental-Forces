package team.lodestar.fufo.core.weaving;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import team.lodestar.fufo.common.recipe.WeaveRecipe;
import team.lodestar.fufo.core.weaving.recipe.EntityTypeBindable;
import team.lodestar.fufo.core.weaving.recipe.IngredientBindable;
import team.lodestar.fufo.core.weaving.recipe.ItemStackBindable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
        final Vec3i position = base.getLocation().offset(relativePosition);
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

    /**
     * Writes this Weave to NBT
     * @return a {@link CompoundTag} containing the Weave's data
     */
    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();

        ListTag bindablesTag = new ListTag();
        for (Bindable bindable : bindables.values()) {
            CompoundTag bindableTag = new CompoundTag();
            CompoundTag sizeTag = new CompoundTag();
            sizeTag.putInt("x", bindable.size().getX());
            sizeTag.putInt("y", bindable.size().getY());
            sizeTag.putInt("z", bindable.size().getZ());
            CompoundTag locationTag = new CompoundTag();
            locationTag.putInt("x", bindable.getLocation().getX());
            locationTag.putInt("y", bindable.getLocation().getY());
            locationTag.putInt("z", bindable.getLocation().getZ());
            bindableTag.put("size", sizeTag);
            bindableTag.put("location", locationTag);

            if(bindable instanceof IngredientBindable) {
                bindableTag.putString("type", "ingredient");
                bindableTag.put("value", Dynamic.convert(JsonOps.INSTANCE, NbtOps.INSTANCE, ((IngredientBindable) bindable).getIngredient().toJson()));
            } else if(bindable instanceof EntityTypeBindable) {
                bindableTag.putString("type", "entity_type");
                CompoundTag valueTag = new CompoundTag();
                valueTag.putString("entity", ForgeRegistries.ENTITY_TYPES.getKey(((EntityTypeBindable) bindable).get()).toString());
                bindableTag.put("value", valueTag);
            } else if (bindable instanceof ItemStackBindable) {
                bindableTag.putString("type", "item");
                bindableTag.put("value", Dynamic.convert(JsonOps.INSTANCE, NbtOps.INSTANCE, Ingredient.of(((ItemStackBindable) bindable).getItemStack()).toJson()));
            }

            bindablesTag.add(bindableTag);
        }

        tag.put("bindables", bindablesTag);

        ListTag bindsTag = new ListTag();
        for (Map.Entry<Pair<Vec3i, Vec3i>, BindingType> entry : getLinks().entrySet()) {
            CompoundTag bindTag = new CompoundTag();

            CompoundTag startTag = new CompoundTag();
            startTag.putInt("x", entry.getKey().getFirst().getX());
            startTag.putInt("y", entry.getKey().getFirst().getY());
            startTag.putInt("z", entry.getKey().getFirst().getZ());

            CompoundTag endTag = new CompoundTag();
            endTag.putInt("x", entry.getKey().getSecond().getX());
            endTag.putInt("y", entry.getKey().getSecond().getY());
            endTag.putInt("z", entry.getKey().getSecond().getZ());

            bindTag.put("start", startTag);
            bindTag.put("end", endTag);
            bindTag.putString("type", entry.getValue().id.toString());

            bindsTag.add(bindTag);
        }

        tag.put("binds", bindsTag);

        return tag;
    }

    /**
     * Deserializes a Weave from NBT
     */
    public static Weave deserialize(CompoundTag tag) {
        StandardWeave weave = null;

        for (Tag bindableGeneralTag : tag.getList("bindables", Tag.TAG_COMPOUND)) {

            Bindable bindable = null;

            CompoundTag bindableTag = (CompoundTag) bindableGeneralTag;
            CompoundTag sizeTag = bindableTag.getCompound("size");
            CompoundTag locationTag = bindableTag.getCompound("location");

            Vec3i size = new Vec3i(sizeTag.getInt("x"), sizeTag.getInt("y"), sizeTag.getInt("z"));
            Vec3i location = new Vec3i(locationTag.getInt("x"), locationTag.getInt("y"), locationTag.getInt("z"));

            if (bindableTag.getString("type").equals("ingredient")) {
                Ingredient ingredient = Ingredient.fromJson(Dynamic.convert(NbtOps.INSTANCE, JsonOps.INSTANCE, bindableTag.getCompound("value")));
                bindable = new IngredientBindable(size, ingredient);
            } else if (bindableTag.getString("type").equals("entity")) {
                EntityType<?> entityType = Registry.ENTITY_TYPE.get(new ResourceLocation(bindableTag.getCompound("value").getString("entity")));
                bindable = new EntityTypeBindable(size, entityType);
            } else if (bindableTag.getString("type").equals("item")) {
                ItemStack ingredient = Ingredient.fromJson(Dynamic.convert(NbtOps.INSTANCE, JsonOps.INSTANCE, bindableTag.getCompound("value"))).getItems()[0];
                bindable = new ItemStackBindable(size, ingredient);
            }

            if (weave == null) {
                weave = new StandardWeave(bindable);
            } else {
                weave.add(location, bindable);
            }
        }

        for (Tag bindTag : tag.getList("binds", Tag.TAG_COMPOUND)) {
            CompoundTag bindTagCompound = (CompoundTag) bindTag;

            CompoundTag startTag = bindTagCompound.getCompound("start");
            CompoundTag endTag = bindTagCompound.getCompound("end");

            Vec3i start = new Vec3i(startTag.getInt("x"), startTag.getInt("y"), startTag.getInt("z"));
            Vec3i end = new Vec3i(endTag.getInt("x"), endTag.getInt("y"), endTag.getInt("z"));

            ResourceLocation bindType = new ResourceLocation(bindTagCompound.getString("type"));

            weave.link(start, end, new BindingType(bindType));
        }

        return weave;
    }

}
