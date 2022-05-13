package com.sammy.fufo.common.blockentity;

import com.sammy.fufo.core.setup.content.block.BlockEntityRegistry;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

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
        }
    }

    @Override
    public void onBreak() {
        if (!level.isClientSide) {
            for (BlockPos pos : nearbyAnchorPositions) {
                BlockHelper.updateState(level, pos);
            }
        }
    }

    //A* algorithm for pathfinding between BlockPos A and BlockPos B
    public ArrayList<BlockPos> getShortestPath(BlockPos start, BlockPos end, Level level) {
        ArrayList<BlockPos> path = new ArrayList<>();
        ArrayList<BlockPos> open = new ArrayList<>();
        ArrayList<BlockPos> closed = new ArrayList<>();
        open.add(start);
        while (!open.isEmpty()) {
            BlockPos current = open.get(0);
            BlockPos next;
            for (int i = 1; i < open.size(); i++) {
                if (open.get(i).distManhattan(end) < current.distManhattan(end)) {
                    current = open.get(i);
                }
            }
            if (current.equals(end)) {
                while (!current.equals(start)) {
                    path.add(current);
                    // TODO: This needs to be normalized IDK HOW TO NORMALIZE A BLOCKPOS HELP MEEEEEEEEE
                    current = current.subtract(current.subtract(start));

                }
                path.add(start);
                return path;
            }
            open.remove(current);
            closed.add(current);
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        next = current.mutable().offset(x, y, z);
                        if (!closed.contains(next) && !next.equals(current) && level.getBlockState(next).isAir()) {
                            open.add(next);
                        }
                    }
                }
            }
        }
        return path;
    }

}
