package com.sammy.fufo.common.blockentity;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.entity.weave.HologramWeaveEntity;
import com.sammy.fufo.common.entity.weave.WeaveEntity;
import com.sammy.fufo.common.recipe.WeaveRecipe;
import com.sammy.fufo.core.systems.magic.weaving.Bindable;
import com.sammy.fufo.core.systems.magic.weaving.BindingType;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Arrays;

public class CrudeNeedleBlockEntity extends OrtusBlockEntity {
    private ArrayList<WeaveEntity> bindableEntities = new ArrayList<>();
    public CrudeNeedleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if(!level.isClientSide) {
            level.getEntitiesOfClass(WeaveEntity.class, AABB.ofSize(Vec3.atCenterOf(this.getBlockPos()), 2, 2, 2)).forEach(entity -> {
                if(entity != null) {
                    bindableEntities.add(entity);
                    entity.kill();
                }
            });
            System.out.println(bindableEntities.toString());
            if (!bindableEntities.isEmpty()) {
                WeaveEntity weave = new WeaveEntity(level);
                weave.weave = new WeaveRecipe((Bindable) Arrays.stream(bindableEntities.get(0).weave.getBindables().toArray()).findFirst().get(), FufoMod.fufoPath("bogus"), "weak").setOutput(Items.HORSE_SPAWN_EGG.getDefaultInstance());
                weave.weave.add((Bindable) Arrays.stream(bindableEntities.get(0).weave.getBindables().toArray()).findFirst().get(), new BindingType(FufoMod.fufoPath("weak")), new Vec3i(0, 0, 1), (Bindable) Arrays.stream(bindableEntities.get(1).weave.getBindables().toArray()).findFirst().get());
                weave.setPos(this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 1.5, this.getBlockPos().getZ() + 0.5);
                level.addFreshEntity(weave);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
