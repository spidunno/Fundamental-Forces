package team.lodestar.fufo.mixin;

import team.lodestar.fufo.common.block.FlammableMeteoriteBlock;
import team.lodestar.fufo.config.CommonConfig;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelMixin {

    @Inject(method = "useOn", at = @At(ordinal = 0, value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
    private void fundamentalForcesFlintAndSteelDurabilityMixin(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof FlammableMeteoriteBlock) {
            int amount = Mth.nextInt(context.getLevel().random, CommonConfig.MINIMUM_METEOR_FLAME_COST.getConfigValue(), CommonConfig.MAXIMUM_METEOR_FLAME_COST.getConfigValue());
            context.getItemInHand().hurtAndBreak(amount, context.getPlayer(), (player) -> player.broadcastBreakEvent(context.getHand()));
        }
    }
}
