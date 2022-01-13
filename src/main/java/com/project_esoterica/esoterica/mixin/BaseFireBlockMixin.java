package com.project_esoterica.esoterica.mixin;

import com.project_esoterica.esoterica.common.block.FlammableMeteoriteBlock;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseFireBlock.class)
public class BaseFireBlockMixin {

    @Inject(at = @At("RETURN"), method = "getState", cancellable = true)
    private static void esotericaMeteorFlameMixin(BlockGetter pReader, BlockPos pPos, CallbackInfoReturnable<BlockState> cir)
    {
        BlockState state = pReader.getBlockState(pPos.below());
        if (state.getBlock() instanceof FlammableMeteoriteBlock block)
        {
            cir.setReturnValue(block.getFlameState(state, pPos));
            return;
        }
        cir.setReturnValue(cir.getReturnValue());
    }
}