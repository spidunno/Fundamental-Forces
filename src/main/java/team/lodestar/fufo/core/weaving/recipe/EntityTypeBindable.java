package team.lodestar.fufo.core.weaving.recipe;

import team.lodestar.fufo.core.weaving.Bindable;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.EntityType;

/**
 * A bindable for an {@link EntityType}.
 */
public class EntityTypeBindable extends Bindable {
    private final EntityType type;

    public EntityTypeBindable(Vec3i size, EntityType type) {
        super(size);
        this.type = type;
    }

    public EntityTypeBindable(EntityType type) {
        this(new Vec3i(1, 1, 1), type);
    }

    public EntityType get() {
        return type;
    }

    @Override
    public String toString() {
        return "EntityTypeBindable{type=" + type + "}";
    }

    /**
     * Checks equality of this bindable with another.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityTypeBindable that = (EntityTypeBindable) o;

        return type.equals(that.type);
    }
}
