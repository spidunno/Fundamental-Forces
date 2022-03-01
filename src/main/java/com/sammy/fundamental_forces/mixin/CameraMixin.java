package com.sammy.fundamental_forces.mixin;

import com.sammy.fundamental_forces.config.ClientConfig;
import com.sammy.fundamental_forces.core.handlers.ScreenshakeHandler;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.sammy.fundamental_forces.FundamentalForcesMod.RANDOM;

@Mixin(Camera.class)
public class CameraMixin {

    @Inject(method = "setup", at = @At("RETURN"))
    private void fundamentalForcesScreenshake(BlockGetter area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (ClientConfig.SCREENSHAKE_INTENSITY.get() > 0) {
            ScreenshakeHandler.cameraTick((Camera) (Object) this, RANDOM);
        }
    }
}