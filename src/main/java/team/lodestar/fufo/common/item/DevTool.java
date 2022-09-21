package team.lodestar.fufo.common.item;


import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.lodestar.fufo.common.starfall.impact.features.MeteoriteFeature;
import team.lodestar.fufo.unsorted.util.DevToolResponse;
import team.lodestar.lodestone.setup.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.screen.base.ScreenParticle;
import team.lodestar.lodestone.systems.rendering.particle.screen.emitter.ItemParticleEmitter;

import java.awt.*;

import static net.minecraft.util.Mth.nextFloat;

public class DevTool extends Item {

    public DevTool(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockEntity te = level.getBlockEntity(context.getClickedPos());
        if (te instanceof DevToolResponse dev) {
            dev.onDevTool(context);
            return InteractionResult.SUCCESS;
        } else if (level instanceof ServerLevel serverLevel) {
            BlockPos pos = context.getClickedPos();
            MeteoriteFeature.perlinNoiseTest(serverLevel, serverLevel.getChunkSource().getGenerator(), pos, level.random);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }
}
