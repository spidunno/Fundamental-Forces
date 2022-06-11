package com.sammy.fufo.core.systems.logistics;

import com.sammy.fufo.common.block.PipeNodeBlock;
import com.sammy.fufo.common.blockentity.PipeNodeBlockEntity;
import com.sammy.ortus.handlers.GhostBlockHandler;
import com.sammy.ortus.handlers.PlacementAssistantHandler;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.systems.placementassistance.IPlacementAssistant;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

import static com.sammy.ortus.handlers.PlacementAssistantHandler.ASSISTANTS;

public class PipeBuilderAssistant implements IPlacementAssistant {

    public ArrayList<BlockPos> cachedPath = new ArrayList<>();
    public final HashMap<BlockPos, BlockState> states = new HashMap<>();

    public BlockPos pastTarget;

    public PipeNodeBlockEntity recentAnchor;
    public BlockPos recentAnchorPos;

    public static void registerPlacementAssistant(FMLCommonSetupEvent event) {
        event.enqueueWork(() ->
                ASSISTANTS.add(new PipeBuilderAssistant())
        );
    }

    public void recalculatePath(Level level, BlockHitResult blockHitResult, BlockState blockState) {
        if (recentAnchorPos != null && blockHitResult != null) {
            if (blockHitResult.getType().equals(HitResult.Type.MISS)) {
                cachedPath.clear();
                return;
            }
            cachedPath = BlockHelper.getPath(PlacementAssistantHandler.target.relative(blockHitResult.getDirection()), recentAnchorPos, 4, true, level);
        }
    }

    @Override
    public void onPlace(Level level, BlockHitResult blockHitResult, BlockState blockState) {
        if (level.getBlockEntity(recentAnchorPos) instanceof AnchorBlockEntity anchorBlockEntity) {
            for (BlockPos pos : cachedPath) {
                System.out.println(pos);
                if (level.getBlockState(pos).getBlock() == Blocks.AIR || level.getBlockState(pos).getMaterial().isReplaceable()) {
                    level.destroyBlock(pos, true);
                    level.setBlock(pos, Blocks.GLASS.defaultBlockState(), 3);
                }
            }
        }
        recentAnchorPos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
        if (level.getBlockEntity(recentAnchorPos) instanceof AnchorBlockEntity anchorBlockEntity) {
            recentAnchor = anchorBlockEntity;
        }
    }

    @Override
    public void assist(Level level, BlockHitResult blockHitResult, BlockState blockState) {
        BlockPos cachedPastTarget = pastTarget;
        pastTarget = PlacementAssistantHandler.target;
        if (!pastTarget.equals(cachedPastTarget)) {
            recalculatePath(level, blockHitResult, blockState);
        }
    }

    @OnlyIn(value = Dist.CLIENT)
    @Override
    public void showAssistance(ClientLevel clientLevel, BlockHitResult blockHitResult, BlockState blockState) {
        for (BlockPos pos : cachedPath) {
            GhostBlockHandler.addGhost(pos, Blocks.GLASS.defaultBlockState()).at(pos);
        }
    }

    @Override
    public Predicate<ItemStack> canAssist() {
        return s -> s.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof PipeNodeBlock;
    }
}