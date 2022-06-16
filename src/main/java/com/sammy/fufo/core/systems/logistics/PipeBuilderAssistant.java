package com.sammy.fufo.core.systems.logistics;

import com.sammy.fufo.common.block.PipeNodeBlock;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Predicate;

import static com.sammy.ortus.handlers.PlacementAssistantHandler.ASSISTANTS;

public class PipeBuilderAssistant implements IPlacementAssistant {
	
	public static final PipeBuilderAssistant INSTANCE = new PipeBuilderAssistant();

    public Collection<BlockPos> cachedPath = new ArrayList<>();
    public final HashMap<BlockPos, BlockState> states = new HashMap<>();

    public BlockPos pastTarget;

    public PipeNode recentAnchor;
    public BlockPos prevAnchorPos;
    public BlockPos recentAnchorPos;

    // Temporary, for debugging only
//    private final LinkedList<BlockPos> positions = new LinkedList<BlockPos>();
    
    public static void registerPlacementAssistant(FMLCommonSetupEvent event) {
        event.enqueueWork(() ->
                ASSISTANTS.add(INSTANCE)
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
    	if (!level.isClientSide) {
	    	BlockPos newAnchorPos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
//	    	positions.addFirst(newAnchorPos);
//	    	if (positions.size() >= 8) positions.removeLast();
	    	prevAnchorPos = recentAnchorPos;
	        recentAnchorPos = newAnchorPos;
	        if (level.getBlockEntity(recentAnchorPos) instanceof PipeNode anchorBlockEntity) {
	            recentAnchor = anchorBlockEntity;
	        }
//	        FufoMod.LOGGER.info(positions);
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