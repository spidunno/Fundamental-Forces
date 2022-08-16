package team.lodestar.fufo.common.entity.weave;

import team.lodestar.fufo.common.blockentity.CrudePrimerBlockEntity;
import team.lodestar.fufo.registry.common.FufoEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class WeaveEntity extends AbstractWeaveEntity {
    public boolean filled = false;

    public WeaveEntity(EntityType<?> entity, Level level) {
        super(entity, level);
    }

    public WeaveEntity(Level level) {
        super(FufoEntities.BASIC_WEAVE.get(), level);
        this.mainColors = new Color[]{new Color(254, 120, 0), new Color(254, 190, 66)};
        this.secondaryColors = new Color[]{new Color(255, 190, 0), new Color(155, 210, 122)};
    }

    @Override
    public @NotNull InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        if (!level.isClientSide) {
            if (!filled) {
                if (pPlayer.isShiftKeyDown() && !(level.getBlockEntity(blockPosition().below()) instanceof CrudePrimerBlockEntity)) {
                    for (BlockPos pos : BlockPos.betweenClosed(this.blockPosition().offset(-5,-5,-5), this.blockPosition().offset(5,5,5))) {
                        if (level.getBlockEntity(pos) instanceof CrudePrimerBlockEntity && level.getEntities(pPlayer, new AABB(pos.offset(0, 1, 0)), entity -> entity instanceof WeaveEntity).isEmpty()) {
                            setPos(new Vec3(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5));
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return super.interact(pPlayer, pHand);
    }



    @Override
    public boolean isPickable() {
        return true;
    }

}
