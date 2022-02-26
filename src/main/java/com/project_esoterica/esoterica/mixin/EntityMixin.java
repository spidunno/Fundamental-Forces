package com.project_esoterica.esoterica.mixin;

import com.project_esoterica.esoterica.core.handlers.MeteorFireHandler;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;updateSwimming()V"))
    private void esotericaMeteorFireTicking(CallbackInfo ci)
    {
        MeteorFireHandler.entityUpdate(((Entity)(Object)this));
    }

    @Inject(method = "setSecondsOnFire", at = @At(value = "RETURN"))
    private void esotericaMeteorFireOverride(int pSeconds, CallbackInfo ci)
    {
        MeteorFireHandler.removeMeteorFire((Entity)(Object)this);
    }
}
