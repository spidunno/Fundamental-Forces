package com.sammy.fundamental_forces.mixin;

import com.sammy.fundamental_forces.core.handlers.CustomFireHandler;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;updateSwimming()V"))
    private void fundamentalForcesCustomFireTicking(CallbackInfo ci)
    {
        CustomFireHandler.entityUpdate(((Entity)(Object)this));
    }

    @Inject(method = "setSecondsOnFire", at = @At(value = "RETURN"))
    private void fundamentalForcesCustomFireOverride(int pSeconds, CallbackInfo ci)
    {
        CustomFireHandler.onVanillaFireTimeUpdate((Entity)(Object)this);
    }
}
