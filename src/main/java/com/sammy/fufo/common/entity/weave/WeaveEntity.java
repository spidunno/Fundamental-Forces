package com.sammy.fufo.common.entity.weave;

import com.sammy.fufo.common.blockentity.CrudePrimerBlockEntity;
import com.sammy.fufo.core.setup.content.entity.EntityRegistry;
import com.sammy.fufo.core.systems.magic.weaving.Weave;
import com.sammy.fufo.core.systems.magic.weaving.recipe.ItemStackBindable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class WeaveEntity extends AbstractWeaveEntity {
    public boolean filled = false;

    public WeaveEntity(EntityType<?> entity, Level level) {
        super(entity, level);
    }

    public WeaveEntity(Level level) {
        super(EntityRegistry.BASIC_WEAVE.get(), level);
        this.mainColors = new Color[]{new Color(250, 226, 0), new Color(223, 219, 120)};
        this.secondaryColors = new Color[]{new Color(252, 175, 50), new Color(247, 210, 151)};
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        if (!level.isClientSide) {
            if (!filled) {
                if (pPlayer.isShiftKeyDown()) {
                    for (BlockPos pos : BlockPos.spiralAround(blockPosition(), 1, Direction.NORTH, Direction.EAST)) {
                        if (level.getBlockEntity(pos) instanceof CrudePrimerBlockEntity && level.getEntities(pPlayer, new AABB(pos.offset(0, 1, 0)), entity -> entity instanceof WeaveEntity).isEmpty()) {
                            move(MoverType.PISTON, new Vec3(pos.getX(), pos.getY() + 1, pos.getZ()));
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return InteractionResult.FAIL;
    }

    public void setBaseItemBindable(ItemStackBindable item) {
        this.weave.add(Vec3i.ZERO, item);
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.weave = Weave.deserialize(pCompound.getCompound("Weave"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.put("Weave", weave.serialize());
    }

}
