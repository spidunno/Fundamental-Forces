package com.sammy.fufo.common.block;

import com.sammy.fufo.common.blockentity.UITestBlockEntity;
import com.sammy.ortus.systems.block.OrtusEntityBlock;

public class UITestBlock<T extends UITestBlockEntity> extends OrtusEntityBlock<T> {
    public UITestBlock(Properties properties) {
        super(properties);
    }
}
