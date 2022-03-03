package com.sammy.fundamental_forces.core.systems.rendering.screenparticle;

import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.base.ScreenParticle;
import com.sammy.fundamental_forces.core.systems.rendering.screenparticle.options.ScreenParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;

public class ScreenParticleType<T extends ScreenParticleOptions>  extends net.minecraftforge.registries.ForgeRegistryEntry<ScreenParticleType<?>> {

   public ParticleProvider<T> provider;
   public ScreenParticleType() {
   }

   public interface ParticleProvider<T extends ScreenParticleOptions> {
      ScreenParticle createParticle(ClientLevel pLevel, T options, double pX, double pY, double pXSpeed, double pYSpeed);
   }
}