package team.lodestar.fufo.common.blockentity;

import team.lodestar.fufo.common.entity.weave.WeaveEntity;
import team.lodestar.fufo.core.weaving.StandardWeave;
import team.lodestar.fufo.core.weaving.recipe.ItemStackBindable;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.blockentity.ItemHolderBlockEntity;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CrudePrimerBlockEntity extends ItemHolderBlockEntity {
    public CrudePrimerBlockEntity(BlockEntityType<? extends CrudePrimerBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        inventory = new LodestoneBlockEntityInventory(1, 1) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            if (this.inventory.isEmpty() && !player.getItemInHand(hand).isEmpty()) {
                if (level.getEntities(player, new AABB(getBlockPos().offset(0, 1, 0)), entity -> entity instanceof WeaveEntity).isEmpty()) {
                    inventory.interact(player.level, player, hand);
                    WeaveEntity item = new WeaveEntity(level);
                    item.weave = new StandardWeave(new ItemStackBindable(ItemStack.EMPTY));
                    item.setBaseItemBindable(new ItemStackBindable(this.inventory.extractItem(0, 1, false)));
                    item.setPos(worldPosition.getX() + 0.5, worldPosition.getY() + 1, worldPosition.getZ() + 0.5);
                    item.mainColors = new Color[]{new Color(250, 226, 0), new Color(223, 219, 120)};
                    item.secondaryColors = new Color[]{new Color(252, 175, 50), new Color(247, 210, 151)};
                    item.filled = true;
                    level.addFreshEntity(item);
                    return InteractionResult.SUCCESS;
                } else if (!level.getEntities(player, new AABB(getBlockPos().offset(0, 1, 0)), entity -> entity instanceof WeaveEntity && !((WeaveEntity) entity).filled).isEmpty()) {
                    inventory.interact(player.level, player, hand);
                    WeaveEntity item = (WeaveEntity) level.getEntities(player, new AABB(getBlockPos().offset(0, 1, 0)), entity -> entity instanceof WeaveEntity && !((WeaveEntity) entity).filled).get(0);
                    item.setBaseItemBindable(new ItemStackBindable(this.inventory.extractItem(0, 1, false)));
                    item.filled = true;
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.FAIL;
    }
}