package com.sammy.fufo.common.blockentity;

import com.sammy.fufo.core.setup.content.block.BlockEntityRegistry;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;

import java.util.ArrayList;
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
            nearbyAnchors = BlockHelper.getBlockEntities(AnchorBlockEntity.class, level, this.getBlockPos(), 10);
            nearbyAnchors.remove(this);
            nearbyAnchorPositions = nearbyAnchors.stream().map(BlockEntity::getBlockPos).collect(Collectors.toCollection(ArrayList::new));
            BlockHelper.updateState(level, getBlockPos());
            placer.sendMessage(Component.nullToEmpty("Found " + nearbyAnchors.size() + " anchors"), placer.getUUID());
            for(AnchorBlockEntity anchor : nearbyAnchors) {
                ArrayList<BlockPos> path = BlockHelper.getPath(this.getBlockPos(), anchor.getBlockPos(), 4, true, level);
                for (BlockPos pos : path) {
                    System.out.println(pos);
                    if (level.getBlockState(pos).getBlock() == Blocks.AIR || level.getBlockState(pos).getMaterial().isReplaceable()) {
                        level.destroyBlock(pos, true);
                        level.setBlock(pos, Blocks.GLASS.defaultBlockState(), 3);
                    }
                }
            }
        }
    }

}
