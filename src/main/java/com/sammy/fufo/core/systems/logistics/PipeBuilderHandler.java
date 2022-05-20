package com.sammy.fufo.core.systems.logistics;

import com.sammy.fufo.common.blockentity.AnchorBlockEntity;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;

public class PipeBuilderHandler {

    public final ArrayList<BlockPos> cachedPath = new ArrayList<>();
    public AnchorBlockEntity recentAnchor;
    public BlockPos recentAnchorPos;

    public void recalculatePath() {

    }
}