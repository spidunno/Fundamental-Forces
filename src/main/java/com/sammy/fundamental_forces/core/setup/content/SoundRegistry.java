package com.sammy.fundamental_forces.core.setup.content;

import com.sammy.fundamental_forces.FundamentalForcesMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FundamentalForcesMod.MODID);

}
