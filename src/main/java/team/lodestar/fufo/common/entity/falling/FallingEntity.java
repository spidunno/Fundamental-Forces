package team.lodestar.fufo.common.entity.falling;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public abstract class FallingEntity extends Entity {

    public final ArrayList<Vec3> pastPositions = new ArrayList<>();

    public FallingEntity(EntityType<?> p_19870, Level p_19871_) {
        super(p_19870, p_19871_);
        this.noPhysics = true;
    }

    @Override
    public void tick() {
        super.tick();
        if (Minecraft.getInstance().player == null)
        {
            return;
        }
        Vec3 position = Minecraft.getInstance().player.position().subtract(getX(), getY(), getZ());
        if (!pastPositions.isEmpty()) {
            Vec3 latest = pastPositions.get(pastPositions.size() - 1);
            float distance = (float) latest.distanceTo(position);
            if (!latest.equals(position) && distance > 0.02f) {
                pastPositions.add(position);
            }
            int excess = pastPositions.size() - 1;
            ArrayList<Vec3> toRemove = new ArrayList<>();
            int desiredLength = 0;
            float efficiency = (excess - desiredLength) * 0.2f;
            if (efficiency > 0f) {
                for (int i = 0; i < excess; i++) {
                    Vec3 excessPosition = pastPositions.get(i);
                    Vec3 nextExcessPosition = pastPositions.get(i + 1);
                    pastPositions.set(i, excessPosition.lerp(nextExcessPosition, Math.min(1, 0.05f * (excess - i) * efficiency)));
                    float excessDistance = (float) excessPosition.distanceTo(nextExcessPosition);
                    if (excessDistance < 0.2) {
                        toRemove.add(pastPositions.get(i));
                    }
                }
                pastPositions.removeAll(toRemove);
            }
        } else {
            pastPositions.add(position);
        }
    }
}