package team.lodestar.fufo.common.fluid;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import team.lodestar.fufo.core.fluid.PipeNode;

public class PipeNodeBlockItem extends BlockItem {
    public PipeNodeBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        if (pContext.getLevel().getBlockEntity(pContext.getClickedPos().relative(pContext.getClickedFace().getOpposite())) instanceof PipeNode pipeNode) {
            PipeBuilderAssistant instance = PipeBuilderAssistant.INSTANCE;
            if (!pipeNode.getPos().equals(instance.previousNodePosition)) {
                instance.updateSelectedNode(pipeNode);
                pContext.getPlayer().swing(pContext.getHand(), true);
                return InteractionResult.PASS;
            }
        }
        return super.place(pContext);
    }
}
