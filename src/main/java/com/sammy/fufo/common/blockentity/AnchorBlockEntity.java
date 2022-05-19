package com.sammy.fufo.common.blockentity;

import com.sammy.fufo.core.setup.content.block.BlockEntityRegistry;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnchorBlockEntity extends OrtusBlockEntity {

    public ArrayList<AnchorBlockEntity> nearbyAnchors = new ArrayList<>();
    public ArrayList<BlockPos> nearbyAnchorPositions = new ArrayList<>();

    public AnchorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public AnchorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.ANCHOR.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (!nearbyAnchorPositions.isEmpty()) {
            CompoundTag compound = new CompoundTag();
            compound.putInt("anchorAmount", nearbyAnchorPositions.size());
            for (int i = 0; i < nearbyAnchorPositions.size(); i++) {
                BlockHelper.saveBlockPos(compound, nearbyAnchorPositions.get(i), "" + i);
            }
            pTag.put("anchorData", compound);
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        nearbyAnchorPositions.clear();
        nearbyAnchors.clear();
        CompoundTag compound = pTag.getCompound("anchorData");
        int amount = compound.getInt("anchorAmount");
        for (int i = 0; i < amount; i++) {
            BlockPos pos = BlockHelper.loadBlockPos(compound, "" + i);
            if (level != null && level.getBlockEntity(pos) instanceof AnchorBlockEntity anchor) {
                nearbyAnchorPositions.add(pos);
                nearbyAnchors.add(anchor);
            }
        }
    }

    @Override
    public void onPlace(LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide) {
            nearbyAnchors = BlockHelper.getBlockEntities(AnchorBlockEntity.class, level, this.getBlockPos(), 4);
            nearbyAnchors.remove(this);
            nearbyAnchorPositions = nearbyAnchors.stream().map(BlockEntity::getBlockPos).collect(Collectors.toCollection(ArrayList::new));
            BlockHelper.updateState(level, getBlockPos());
            placer.sendMessage(Component.nullToEmpty("Found " + nearbyAnchors.size() + " anchors"), placer.getUUID());
        }
    }
}
