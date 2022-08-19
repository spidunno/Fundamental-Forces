package team.lodestar.fufo.common.block;

import team.lodestar.fufo.common.blockentity.CrudeNeedleBlockEntity;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

public class CrudeNeedleBlock<T extends CrudeNeedleBlockEntity> extends LodestoneEntityBlock<T> {
    public CrudeNeedleBlock(Properties properties) {
        super(properties);
    }
}
