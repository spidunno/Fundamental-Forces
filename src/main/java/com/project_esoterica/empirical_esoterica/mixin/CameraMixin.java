package com.project_esoterica.empirical_esoterica.mixin;

import com.project_esoterica.empirical_esoterica.core.config.ClientConfig;
import com.project_esoterica.empirical_esoterica.core.systems.screenshake.ScreenshakeHandler;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.project_esoterica.empirical_esoterica.EmpiricalEsoterica.RANDOM;

@Mixin(Camera.class)
public class CameraMixin {

    @Inject(at = @At("RETURN"), method = "setup")
    private void empiricalEsotericaSetupCamera(BlockGetter area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (ClientConfig.ENABLE_SCREENSHAKE.get()) {
            ScreenshakeHandler.cameraTick((Camera) (Object) this, RANDOM);
        }
    }
}