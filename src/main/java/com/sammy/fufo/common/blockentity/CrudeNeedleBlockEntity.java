package com.sammy.fufo.common.blockentity;

import com.sammy.fufo.FufoMod;
import com.sammy.fufo.common.entity.weave.HologramWeaveEntity;
import com.sammy.fufo.common.entity.weave.PrimedBindableEntity;
import com.sammy.fufo.common.recipe.WeaveRecipe;
import com.sammy.fufo.core.systems.magic.weaving.BindingType;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class CrudeNeedleBlockEntity extends OrtusBlockEntity {
    private ArrayList<PrimedBindableEntity> primedBindableEntities = new ArrayList<>();
    public CrudeNeedleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if(!level.isClientSide) {
            level.getEntities(new PrimedBindableEntity(level), AABB.ofSize(Vec3.atCenterOf(this.getBlockPos()), 2, 2, 2)).forEach(entity -> {
                if(entity instanceof PrimedBindableEntity) {
                    primedBindableEntities.add((PrimedBindableEntity) entity);
                    entity.kill();
                }
            });
            System.out.println(primedBindableEntities.toString());
            if (!primedBindableEntities.isEmpty()) {
                HologramWeaveEntity weave = new HologramWeaveEntity(level);
                weave.weave = new WeaveRecipe(primedBindableEntities.get(0).getBindable(), FufoMod.fufoPath("bogus"), "weak").setOutput(Items.HORSE_SPAWN_EGG.getDefaultInstance());
                weave.weave.add(primedBindableEntities.get(0).getBindable(), new BindingType(FufoMod.fufoPath("weak")), new Vec3i(0, 0, 1), primedBindableEntities.get(1).getBindable());
                weave.setPos(this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 1.5, this.getBlockPos().getZ() + 0.5);
                level.addFreshEntity(weave);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
