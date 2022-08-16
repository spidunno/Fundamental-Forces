package team.lodestar.fufo.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import team.lodestar.fufo.unsorted.handlers.PlayerSpellHotbarHandler;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

    //TODO: all this should be done with forges gui system. Wire knows this shit best.

    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    private void fundamentalForcesHotbarOffset(float partialTicks, PoseStack poseStack, CallbackInfo ci) {
        boolean cancel = PlayerSpellHotbarHandler.ClientOnly.moveVanillaUI(false, poseStack);
        if (cancel) {
            ci.cancel();
        }
    }

    @Inject(method = "renderHotbar", at = @At("RETURN"))
    private void fundamentalForcesHotbarOffsetPartTwo(float partialTicks, PoseStack poseStack, CallbackInfo ci) {
        PlayerSpellHotbarHandler.ClientOnly.moveVanillaUI(true, poseStack);
    }

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    private void fundamentalForcesExperienceOffset(PoseStack poseStack, int l, CallbackInfo ci) {
        boolean cancel = PlayerSpellHotbarHandler.ClientOnly.moveVanillaUI(false, poseStack);
        if (cancel) {
            ci.cancel();
        }
    }

    @Inject(method = "renderExperienceBar", at = @At("RETURN"))
    private void fundamentalForcesExperienceOffsetPartTwo(PoseStack poseStack, int l, CallbackInfo ci) {
        PlayerSpellHotbarHandler.ClientOnly.moveVanillaUI(true, poseStack);
    }

    @Inject(method = "renderJumpMeter", at = @At("HEAD"), cancellable = true)
    private void fundamentalForcesHorsePlinkoOffset(PoseStack poseStack, int l, CallbackInfo ci) {
        boolean cancel = PlayerSpellHotbarHandler.ClientOnly.moveVanillaUI(false, poseStack);
        if (cancel) {
            ci.cancel();
        }
    }

    @Inject(method = "renderJumpMeter", at = @At("RETURN"))
    private void fundamentalForcesHorsePlinkoPartTwo(PoseStack poseStack, int l, CallbackInfo ci) {
        PlayerSpellHotbarHandler.ClientOnly.moveVanillaUI(true, poseStack);
    }

    @ModifyArg(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V"), index = 1)
    private int fundamentalForcesHotbarItemOffset(int original) {
        return (int) (original + PlayerSpellHotbarHandler.ClientOnly.itemHotbarOffset());
    }
}