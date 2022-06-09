package com.sammy.fufo.common.blockentity;

import com.sammy.fufo.core.registratation.BlockEntityRegistrate;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class AnchorBlockEntity extends OrtusBlockEntity {


    public ArrayList<AnchorBlockEntity> nearbyAnchors = new ArrayList<>();
    public ArrayList<BlockPos> nearbyAnchorPositions = new ArrayList<>();

    public AnchorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public AnchorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistrate.ANCHOR.get(), pos, state);
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
}
