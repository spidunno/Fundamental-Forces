package com.sammy.fufo.common.block;

import com.sammy.fufo.common.blockentity.CrudePrimerBlockEntity;
import com.sammy.ortus.systems.block.OrtusEntityBlock;

public class CrudePrimerBlock<T extends CrudePrimerBlockEntity> extends OrtusEntityBlock<T> {
    public CrudePrimerBlock(Properties properties) {
        super(properties);
    }
}
