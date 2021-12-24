package com.project_esoterica.esoterica.common.item;

import com.project_esoterica.esoterica.common.worldgen.MeteoriteFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class DevTool extends Item {

    public DevTool(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level instanceof ServerLevel serverLevel) {
            BlockPos pos = context.getClickedPos();
            MeteoriteFeature.generateMeteorite(serverLevel, serverLevel.getChunkSource().getGenerator(), pos, level.random);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }
}
