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
        InteractionResult result = PipeBuilderAssistant.INSTANCE.updateSelectedNode(pContext);
        if (result != null) {
            return result;
        }
        InteractionResult originalResult = super.place(pContext);
        if (!originalResult.equals(InteractionResult.FAIL) && pContext.getLevel().getBlockEntity(pContext.getClickedPos()) instanceof PipeNode pipeNode) {
            PipeBuilderAssistant.INSTANCE.updateSelectedNode(pipeNode);
        }
        return originalResult;
    }
}
