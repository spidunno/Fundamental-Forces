package com.project_esoterica.esoterica.mixin;

import com.project_esoterica.esoterica.config.ClientConfig;
import com.project_esoterica.esoterica.core.systems.screenshake.ScreenshakeHandler;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.project_esoterica.esoterica.EsotericaMod.RANDOM;

@Mixin(Camera.class)
public class CameraMixin {

    @Inject(method = "setup", at = @At("RETURN"))
    private void esotericaScreenshake(BlockGetter area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (ClientConfig.SCREENSHAKE_INTENSITY.get() > 0) {
            ScreenshakeHandler.cameraTick((Camera) (Object) this, RANDOM);
        }
    }
}