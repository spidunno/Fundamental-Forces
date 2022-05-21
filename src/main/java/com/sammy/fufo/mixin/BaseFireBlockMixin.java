package com.sammy.fufo.mixin;

import com.sammy.fufo.common.block.FlammableMeteoriteBlock;
import com.sammy.fufo.core.registratation.BlockRegistrate;
import com.sammy.fufo.core.setup.content.item.ItemTagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseFireBlock.class)
public class BaseFireBlockMixin {

    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    private void fundamentalForcesConvertMeteorFlame(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity, CallbackInfo ci) {
        if (pEntity instanceof ItemEntity itemEntity) {
            ItemStack stack = itemEntity.getItem();
            if (pState.equals(pState.getBlock().defaultBlockState())) {
                if (stack.is(ItemTagRegistry.METEOR_FLAME_CATALYST)) {
                    pLevel.setBlock(pPos, BlockRegistrate.METEOR_FIRE.get().defaultBlockState(), 3);
                    ci.cancel();
                }
            }
        }
    }
    @Inject(method = "getState", at = @At("RETURN"), cancellable = true)
    private static void fundamentalForcesCreateMeteorFlame(BlockGetter pReader, BlockPos pPos, CallbackInfoReturnable<BlockState> cir)
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