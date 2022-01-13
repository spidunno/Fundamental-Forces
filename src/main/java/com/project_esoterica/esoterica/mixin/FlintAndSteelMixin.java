package com.project_esoterica.esoterica.mixin;

import com.project_esoterica.esoterica.common.block.FlammableMeteoriteBlock;
import com.project_esoterica.esoterica.config.CommonConfig;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelMixin {

    @Inject(method = "useOn", at = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V"), cancellable = true)
    private void esotericaFlintAndSteelDurabilityMixin(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof FlammableMeteoriteBlock) {
            int amount = Mth.nextInt(context.getLevel().random, CommonConfig.MINIMUM_METEOR_FLAME_COST.get(), CommonConfig.MAXIMUM_METEOR_FLAME_COST.get());
            context.getItemInHand().hurtAndBreak(amount, context.getPlayer(), (player) -> player.broadcastBreakEvent(context.getHand()));
        }
    }
}
