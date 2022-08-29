package team.lodestar.fufo.common.fluid;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.jetbrains.annotations.Nullable;
import team.lodestar.fufo.core.fluid.PipeNode;
import team.lodestar.fufo.registry.common.FufoBlocks;
import team.lodestar.lodestone.handlers.GhostBlockHandler;
import team.lodestar.lodestone.handlers.PlacementAssistantHandler;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.placementassistance.IPlacementAssistant;
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
import team.lodestar.lodestone.systems.rendering.ghost.GhostBlockOptions;
import team.lodestar.lodestone.systems.rendering.ghost.GhostBlockRenderer;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static team.lodestar.lodestone.handlers.PlacementAssistantHandler.ASSISTANTS;

public class PipeBuilderAssistant implements IPlacementAssistant {

    public static final PipeBuilderAssistant INSTANCE = new PipeBuilderAssistant();

    public Collection<BlockPos> cachedPath = new ArrayList<>();
    public final HashMap<BlockPos, BlockState> states = new HashMap<>();

    public BlockHitResult pastTarget;

    public PipeNode previousPipeNode;
    public BlockPos previousNodePosition;
    public BlockPos currentNodePosition;

    public static void registerPlacementAssistant(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> ASSISTANTS.add(INSTANCE));
    }

    public BlockPos getTargetPosition(BlockHitResult blockHitResult) { //TODO: this ain't always accurate
        BlockPos placedPos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
        return placedPos;
    }

    public Collection<BlockPos> recalculatePath(Level level, BlockHitResult blockHitResult, BlockState blockState) {
        if (currentNodePosition != null && blockHitResult != null) {
            if (blockHitResult.getType().equals(HitResult.Type.MISS)) {
                return Collections.emptyList();
            }
            BlockPos placedPos = getTargetPosition(blockHitResult);
            boolean matchesX = placedPos.getX() - currentNodePosition.getX() == 0;
            boolean matchesY = placedPos.getY() - currentNodePosition.getY() == 0;
            boolean matchesZ = placedPos.getZ() - currentNodePosition.getZ() == 0;
            List<Boolean> matches = List.of(matchesX, matchesY, matchesZ);
            if (matches.stream().filter(b -> b).count() != 2) {
                return Collections.emptyList();
            }
            List<BlockPos> inBetween = BlockPos.betweenClosedStream(currentNodePosition, placedPos).map(BlockPos::immutable).collect(Collectors.toList());
            inBetween.remove(0);
            inBetween.remove(inBetween.size() - 1);
            return inBetween;
        }
        return Collections.emptyList();
    }

    public InteractionResult updateSelectedNode(BlockPlaceContext pContext) {
        if (pContext.getLevel().getBlockEntity(pContext.getClickedPos().relative(pContext.getClickedFace().getOpposite())) instanceof PipeNode pipeNode) {
            PipeBuilderAssistant instance = PipeBuilderAssistant.INSTANCE;
            if (!pipeNode.getPos().equals(instance.previousNodePosition)) {
                instance.updateSelectedNode(pipeNode);
                return InteractionResult.SUCCESS;
            }
        }
        return null;
    }

    public void updateSelectedNode(PipeNode tile) {
        previousNodePosition = currentNodePosition;
        currentNodePosition = tile.getPos();
        previousPipeNode = tile;
        cachedPath.clear();
    }

    @Override
    public void onPlace(Level level, BlockHitResult hit, BlockState blockState) {

    }

    @Override
    public void assist(Level level, BlockHitResult blockHitResult, BlockState blockState) {
        BlockHitResult cachedPastTarget = pastTarget;
        pastTarget = PlacementAssistantHandler.target;
        if (!pastTarget.equals(cachedPastTarget)) {
            cachedPath = recalculatePath(level, blockHitResult, blockState);
        }
    }

    @OnlyIn(value = Dist.CLIENT)
    @Override
    public void showPlacementAssistance(ClientLevel clientLevel, BlockHitResult blockHitResult, BlockState blockState, ItemStack held) {
        for (BlockPos pos : cachedPath) {
            GhostBlockHandler.addGhost(pos, GhostBlockRenderer.STANDARD, GhostBlockOptions.create(Blocks.GLASS.defaultBlockState(), pos).withRenderType(LodestoneRenderTypeRegistry.ADDITIVE_BLOCK).withColor(0.6f, 0.95f, 1f), 0);
        }
        if (currentNodePosition != null && !cachedPath.isEmpty()) {
            BlockState anchor = ((BlockItem)held.getItem()).getBlock().defaultBlockState();
            BlockPos placedPos = getTargetPosition(blockHitResult);
            GhostBlockHandler.addGhost(placedPos, GhostBlockRenderer.STANDARD, GhostBlockOptions.create(anchor, placedPos).withRenderType(LodestoneRenderTypeRegistry.ADDITIVE_BLOCK).withColor(0.6f, 0.95f, 1f).withAlpha(()-> 0.75f).withScale(() -> 1.1f), 0);
        }
    }

    @Override
    public void showPassiveAssistance(ClientLevel level, @Nullable BlockHitResult hit) {
        if (currentNodePosition != null) {
            BlockState anchor = level.getBlockState(currentNodePosition);
            GhostBlockHandler.addGhost(currentNodePosition, GhostBlockRenderer.STANDARD, GhostBlockOptions.create(anchor, currentNodePosition).withRenderType(LodestoneRenderTypeRegistry.ADDITIVE_BLOCK).withColor(0.6f, 0.95f, 1f).withAlpha(()-> 0.75f).withScale(() -> 1.1f), 0);
        }
    }

    @Override
    public Predicate<ItemStack> canAssist() {
        return s -> s.getItem() instanceof PipeNodeBlockItem;
    }
}