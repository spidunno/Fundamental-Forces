package com.sammy.fufo.common.block;

import com.sammy.fufo.common.blockentity.CrudeNeedleBlockEntity;
import com.sammy.ortus.systems.block.OrtusEntityBlock;

public class CrudeNeedleBlock<T extends CrudeNeedleBlockEntity> extends OrtusEntityBlock<T> {
    public CrudeNeedleBlock(Properties properties) {
        super(properties);
    }
}
