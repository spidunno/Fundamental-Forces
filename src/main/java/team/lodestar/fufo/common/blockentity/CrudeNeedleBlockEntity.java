package team.lodestar.fufo.common.blockentity;

import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.common.entity.weave.WeaveEntity;
import team.lodestar.fufo.common.recipe.WeaveRecipe;
import team.lodestar.fufo.core.weaving.Bindable;
import team.lodestar.fufo.core.weaving.BindingType;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Arrays;

public class CrudeNeedleBlockEntity extends LodestoneBlockEntity {
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
